package ar.com.notificacionesandroid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by Fernando Moreno on 22/11/2021.
 */

fun Int.createBitmap(context: Context): Bitmap {
    return BitmapFactory.decodeResource(context.resources, this)
}