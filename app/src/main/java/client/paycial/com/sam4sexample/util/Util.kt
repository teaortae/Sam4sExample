package client.paycial.com.sam4sexample.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> createViewModel(lifecycleOwner: FragmentActivity): T =
    ViewModelProviders.of(lifecycleOwner)[T::class.java]

inline fun <reified T : ViewModel> createViewModelWithFactory(
    lifecycleOwner: FragmentActivity,
    factory: ViewModelProvider.Factory
): T = ViewModelProviders.of(lifecycleOwner, factory)[T::class.java]


fun View.getBitmapFromView(): Bitmap {
    val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    layout(0, 0, measuredWidth, measuredHeight)
    draw(canvas)
    return bitmap
}