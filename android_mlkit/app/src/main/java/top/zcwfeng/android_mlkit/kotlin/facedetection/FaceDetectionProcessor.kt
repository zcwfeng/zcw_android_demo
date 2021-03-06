package top.zcwfeng.android_mlkit.kotlin.facedetection

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import top.zcwfeng.android_mlkit.R
import top.zcwfeng.android_mlkit.common.CameraImageGraphic
import top.zcwfeng.android_mlkit.common.FrameMetadata
import top.zcwfeng.android_mlkit.common.GraphicOverlay
import top.zcwfeng.android_mlkit.kotlin.VisionProcessorBase
import java.io.IOException

/** Face Detector Demo.  */
class FaceDetectionProcessor(res: Resources) : VisionProcessorBase<List<FirebaseVisionFace>>() {

    private val detector: FirebaseVisionFaceDetector

    private val overlayBitmap: Bitmap

    init {
        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .build()

        detector = FirebaseVision.getInstance().getVisionFaceDetector(options)

        overlayBitmap = BitmapFactory.decodeResource(res, R.drawable.clown_nose)
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Face Detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionFace>> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(
            originalCameraImage: Bitmap?,
            results: List<FirebaseVisionFace>,
            frameMetadata: FrameMetadata,
            graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()
        val imageGraphic = CameraImageGraphic(graphicOverlay, originalCameraImage)
        graphicOverlay.add(imageGraphic)
        for (i in results.indices) {
            val face = results[i]

            val cameraFacing = frameMetadata.cameraFacing
            val faceGraphic = FaceGraphic(graphicOverlay, face, cameraFacing, overlayBitmap)
            graphicOverlay.add(faceGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Face detection failed $e")
    }

    companion object {

        private const val TAG = "FaceDetectionProcessor"
    }
}