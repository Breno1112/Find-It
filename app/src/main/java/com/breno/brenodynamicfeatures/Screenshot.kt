package com.breno.brenodynamicfeatures

import android.app.Activity
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.*


object ScreenShot {
    private const val TAG = "SCREENSHOT_TAG"
    fun take(activity: Activity, name: String) {
        val dir =
            Environment.getExternalStorageDirectory().absolutePath + "/test-screenshots/"
        val path = dir + name
        val filePath = File(dir) // Create directory if not present
        if (!filePath.isDirectory) {
            Log.i(TAG, "Creating directory $filePath")
            filePath.mkdirs()
        }
        Log.i(TAG, "Saving to path: $path")
        val phoneView = activity.window.decorView.rootView
        phoneView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(phoneView.drawingCache)
        phoneView.isDrawingCacheEnabled = false
        var out: OutputStream? = null
        val imageFile = File(path)
        try {
            out = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
        } catch (e: FileNotFoundException) {
            Log.e(TAG, e.toString())
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
            }
        }
    }
}