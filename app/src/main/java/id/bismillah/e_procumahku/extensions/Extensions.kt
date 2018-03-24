package id.bismillah.e_procumahku.extensions

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.Toast
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur
import eightbitlab.com.blurview.BlurView

/**
 * Created by Subkhan Sarif on 21/03/2018.
 */

// context extension
fun Context.colorResource(id: Int): Int {
    return resources.getColor(id)
}

fun Context.stringResource(id: Int): String {
    return resources.getString(id)
}

fun Context.arrayResource(id: Int): Array<String> {
    return resources.getStringArray(id)
}

fun Context.blur(view: BlurView, parent: ViewGroup, background: Drawable, radius: Float = 10f) {
    val renderScriptBlur = SupportRenderScriptBlur(this)
    view.setupWith(parent)
            .blurAlgorithm(renderScriptBlur)
            .windowBackground(background)
            .blurRadius(radius)
}

fun Context.ToastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun Context.ToastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Activity extension
fun Activity.blur(view: BlurView, radius: Float = 10f) {
    val renderScriptBlur = SupportRenderScriptBlur(this)
    val background = window.decorView.background
    val parentView = window.decorView as ViewGroup
    view.setupWith(parentView)
            .blurAlgorithm(renderScriptBlur)
            .windowBackground(background)
            .blurRadius(radius)
}