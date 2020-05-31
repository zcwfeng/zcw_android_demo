package top.zcwfeng.android_mlkit

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.camera.camera2.Camera2AppConfig
import androidx.camera.core.CameraX

/**
 * 作者：zcw on 2019-10-25
 */
class Camera2initializer:ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = "Camera2initializer"
    @SuppressLint("RestrictedApi")
    override fun onCreate(): Boolean {
        Log.d(TAG,"CameraX initializing with Camera2 ...")
        CameraX.init(context,Camera2AppConfig.create(context))
        return false
    }
}