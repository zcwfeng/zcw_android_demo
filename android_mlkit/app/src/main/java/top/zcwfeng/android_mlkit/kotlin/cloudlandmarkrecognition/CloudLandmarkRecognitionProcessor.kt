package top.zcwfeng.android_mlkit.kotlin.cloudlandmarkrecognition

import android.graphics.Bitmap
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import top.zcwfeng.android_mlkit.common.FrameMetadata
import top.zcwfeng.android_mlkit.common.GraphicOverlay
import top.zcwfeng.android_mlkit.kotlin.VisionProcessorBase

/** Cloud Landmark Detector Demo.  */
class CloudLandmarkRecognitionProcessor : VisionProcessorBase<List<FirebaseVisionCloudLandmark>>() {

    private val detector: FirebaseVisionCloudLandmarkDetector

    init {
        val options = FirebaseVisionCloudDetectorOptions.Builder()
            .setMaxResults(10)
            .setModelType(FirebaseVisionCloudDetectorOptions.STABLE_MODEL)
            .build()

        detector = FirebaseVision.getInstance().getVisionCloudLandmarkDetector(options)
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionCloudLandmark>> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(
            originalCameraImage: Bitmap?,
            results: List<FirebaseVisionCloudLandmark>,
            frameMetadata: FrameMetadata,
            graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()
        Log.d(TAG, "cloud landmark size: ${results.size}")

        results.forEach {
            Log.d(TAG, "cloud landmark: $it")
            val cloudLandmarkGraphic = CloudLandmarkGraphic(graphicOverlay, it)
            graphicOverlay.add(cloudLandmarkGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Cloud Landmark detection failed $e")
    }

    companion object {
        private const val TAG = "CloudLmkRecProc"
    }
}
