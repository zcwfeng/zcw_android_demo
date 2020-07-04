package top.zcwfeng.mylibrary;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ShellApplication extends Application {

    final private static String TAG = ShellApplication.class.getName();

    public static String getPassword(){
        return "abcdefgh12345678";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        AES.init(getPassword());
        File apkFile = new File(getApplicationInfo().sourceDir);
        //data/data/包名/files/fake_apk/
        File unZipFile = getDir("fake_apk", MODE_PRIVATE);
        File app = new File(unZipFile, "app");
        if (!app.exists()) {
            Zip.unZip(apkFile, app);
            File[] files = app.listFiles();
            for (File file : files) {
                String name = file.getName();
                if (name.equals("classes.dex")) {

                } else if (name.endsWith(".dex")) {
                    try {
                        byte[] bytes = getBytes(file);
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] decrypt = AES.decrypt(bytes);
//                        fos.write(bytes);
                        fos.write(decrypt);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        List list = new ArrayList<>();
        Log.d("FAKE", Arrays.toString(app.listFiles()));
        for (File file : app.listFiles()) {
            if (file.getName().endsWith(".dex")) {
                list.add(file);
            }
        }

        Log.d("FAKE", list.toString());
        try {
            V19.install(getClassLoader(), list, unZipFile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class clazz = instance.getClass();
        while(clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                if(!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }

        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }


    public static Method findMethod(Object instance,String name,Class... parameterTypes) throws NoSuchMethodException {
        Class clazz = instance.getClass();
        while(clazz != null) {
            try {
               Method method = clazz.getDeclaredMethod(name,parameterTypes);
                if(!method.isAccessible()){
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }

        }

        throw new NoSuchMethodException("Method " + name +
                " with parameters " + Arrays.asList(parameterTypes)
                + " not found in " + instance.getClass());

    }

    private static void expandFieldArray(Object instance,String fildName,Object[] extraElements) throws NoSuchFieldException, IllegalAccessException {
        Field jlrField = findField(instance,fildName);
        Object origin[] = (Object[]) jlrField.get(instance);
        Object combined[] = (Object[]) Array.newInstance(origin.getClass().getComponentType(),
                origin.length + extraElements.length);
        System.arraycopy(origin,0,combined,0,origin.length);
        System.arraycopy(extraElements,0,combined,origin.length,extraElements.length);
        jlrField.set(instance,combined);

    }


    public static final class V19 {
        public V19() {
        }


        private static Object[] makeDexElements(Object dexPathList,
                                                ArrayList<File> files, File
                                                        optimizedDirectory,
                                                ArrayList<IOException> suppressedExceptions) throws
                IllegalAccessException, InvocationTargetException, NoSuchMethodException {

            Method makeDexElements = findMethod(dexPathList, "makeDexElements", new
                    Class[]{ArrayList.class, File.class, ArrayList.class});
            return ((Object[]) makeDexElements.invoke(dexPathList, new Object[]{files,
                    optimizedDirectory, suppressedExceptions}));
        }

        private static void install(ClassLoader loader,List<File> additionalClassPathEntries,
                                    File optimizedDirectory)throws IllegalArgumentException,
                IllegalAccessException, NoSuchFieldException, InvocationTargetException,
        NoSuchMethodException {
            Field pathListField = findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            ArrayList suppressedExceptions = new ArrayList();
            Log.d(TAG, "Build.VERSION.SDK_INT " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT >= 23) {
                expandFieldArray(dexPathList, "dexElements", makePathElements(dexPathList, new
                                ArrayList(additionalClassPathEntries), optimizedDirectory,
                        suppressedExceptions));
            } else {
                expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new
                                ArrayList(additionalClassPathEntries), optimizedDirectory,
                        suppressedExceptions));
            }

            if (suppressedExceptions.size() > 0) {
                Iterator suppressedExceptionsField = suppressedExceptions.iterator();

                while (suppressedExceptionsField.hasNext()) {
                    IOException dexElementsSuppressedExceptions = (IOException)
                            suppressedExceptionsField.next();
                    Log.w("MultiDex", "Exception in makeDexElement",
                            dexElementsSuppressedExceptions);
                }

                Field suppressedExceptionsField1 = findField(loader,
                        "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions1 = (IOException[]) ((IOException[])
                        suppressedExceptionsField1.get(loader));
                if (dexElementsSuppressedExceptions1 == null) {
                    dexElementsSuppressedExceptions1 = (IOException[]) suppressedExceptions
                            .toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[suppressedExceptions.size() +
                            dexElementsSuppressedExceptions1.length];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions1, 0, combined,
                            suppressedExceptions.size(), dexElementsSuppressedExceptions1.length);
                    dexElementsSuppressedExceptions1 = combined;
                }

                suppressedExceptionsField1.set(loader, dexElementsSuppressedExceptions1);
            }

        }
    }

    /**
     * A wrapper around
     * {@code private static final dalvik.system.DexPathList#makePathElements}.
     */
    private static Object[] makePathElements(
            Object dexPathList, ArrayList<File> files, File optimizedDirectory,
            ArrayList<IOException> suppressedExceptions)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Method makePathElements;
        try {
            makePathElements = findMethod(dexPathList, "makePathElements", List.class, File.class,
                    List.class);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException: makePathElements(List,File,List) failure");
            try {
                makePathElements = findMethod(dexPathList, "makePathElements", ArrayList.class, File.class, ArrayList.class);
            } catch (NoSuchMethodException e1) {
                Log.e(TAG, "NoSuchMethodException: makeDexElements(ArrayList,File,ArrayList) failure");
                try {
                    Log.e(TAG, "NoSuchMethodException: try use v19 instead");
                    return V19.makeDexElements(dexPathList, files, optimizedDirectory, suppressedExceptions);
                } catch (NoSuchMethodException e2) {
                    Log.e(TAG, "NoSuchMethodException: makeDexElements(List,File,List) failure");
                    throw e2;
                }
            }
        }
        return (Object[]) makePathElements.invoke(dexPathList, files, optimizedDirectory, suppressedExceptions);
    }

    private byte[] getBytes(File file) throws IOException {
        RandomAccessFile r = new RandomAccessFile(file,"r");
        byte[] buffer = new byte[(int) r.length()];
        r.readFully(buffer);
        r.close();
        return buffer;

    }
}
