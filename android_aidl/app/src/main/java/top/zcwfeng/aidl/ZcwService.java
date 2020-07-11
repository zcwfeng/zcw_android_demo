package top.zcwfeng.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ZcwService extends Service {
    private ArrayList<Person> persons;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        persons = new ArrayList<>();
        Log.e("zcw:::ZcwService","onBind Success!");
        return iBinder;
    }

    private IBinder iBinder = new IZcwAidl.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            persons.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return persons;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("zcw:::ZcwService","onCreate Success!");

    }
}
