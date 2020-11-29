package top.zcwfeng.mycamera;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity implements  ImageAnalysis.Analyzer {
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private final LinkedBlockingQueue queue = new LinkedBlockingQueue();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));


    }

    @SuppressLint("RestrictedApi")
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)

                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());


        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, getAnalysis());

    }


    private ImageAnalysis getAnalysis() {
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)

                        .build();
//        imageAnalysis.setAnalyzer(
//                new ThreadPoolExecutor(1, 1,
//                        10L, TimeUnit.SECONDS,queue),
//                this);
        imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(this),
                this);


        return imageAnalysis;
    }


    @Override
    public void analyze(@NonNull ImageProxy image) {
        Log.e("CameraTest", image.toString());
        Log.e("CameraTest0000000+", image.getWidth()+"");

    }
}