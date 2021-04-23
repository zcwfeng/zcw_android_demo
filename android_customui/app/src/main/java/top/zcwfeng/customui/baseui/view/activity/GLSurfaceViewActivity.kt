package top.zcwfeng.customui.baseui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_glsurface_view.*
import top.zcwfeng.customui.R

class GLSurfaceViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glsurface_view)

        gl_surfaceview.setZOrderOnTop(true)
        gl_surfaceview.setZOrderMediaOverlay(true)
        myview.bringChildToFront(gl_surfaceview)

        //android 8.0  需要重新画下mGLSurfaceView以便显示在上层
        if(android.os.Build.VERSION.SDK_INT>=26){
            gl_surfaceview.visibility = View.INVISIBLE;
            gl_surfaceview.visibility =  View.VISIBLE;
        }
    }
}
