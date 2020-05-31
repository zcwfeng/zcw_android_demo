package top.zcwfeng.android_mlkit.kotlin.objectdetection

import android.graphics.Bitmap
import android.util.Log

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.objects.FirebaseVisionObject
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetector
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions
import top.zcwfeng.android_mlkit.common.CameraImageGraphic
import top.zcwfeng.android_mlkit.common.FrameMetadata
import top.zcwfeng.android_mlkit.common.GraphicOverlay
import top.zcwfeng.android_mlkit.java.VisionProcessorBase

import java.io.IOException

/** A processor to run object detector.  */
class ObjectDetectorProcessor(options: FirebaseVisionObjectDetectorOptions) :
    VisionProcessorBase<List<FirebaseVisionObject>>() {

    private val detector: FirebaseVisionObjectDetector

    init {
        detector = FirebaseVision.getInstance().getOnDeviceObjectDetector(options)
    }

    override fun stop() {
        super.stop()
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close object detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionObject>> {
        return detector.processImage(image)
    }

    override fun onSuccess(
            originalCameraImage: Bitmap?,
            results: List<FirebaseVisionObject>,
            frameMetadata: FrameMetadata,
            graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()
        if (originalCameraImage != null) {
            val imageGraphic = CameraImageGraphic(graphicOverlay, originalCameraImage)
            graphicOverlay.add(imageGraphic)
        }
        for (visionObject in results) {
            val objectGraphic = ObjectGraphic(graphicOverlay, visionObject)
            graphicOverlay.add(objectGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Object detection failed $e")
    }

    companion object {
        private const val TAG = "ObjectDetectorProcessor"
    }
}
