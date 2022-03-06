package com.tandem.communityapp.utilities

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.tandem.communityapp.R

object BindingAdapters {
    @BindingAdapter("willShow")
    @JvmStatic
    fun willShow(view: View, willShow: Boolean) {
        view.visibility = if (willShow) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(R.color.functionGreyPlaceholder)
            .into(imageView)
    }

    @BindingAdapter("imageRadius")
    @JvmStatic
    fun setImageRadius(imageView: ShapeableImageView, imageRadius: Int) {
        imageView.shapeAppearanceModel =
            generateShapeAppearanceModel(imageRadius.dpToPx(imageView.resources.displayMetrics))
    }

    @BindingAdapter("backgroundColor", "backgroundRadius", requireAll = false)
    @JvmStatic
    fun setBackground(view: View, @ColorInt backgroundColor: Int = 0, backgroundRadius: Int = 0) {
        if (backgroundColor == 0) return

        val shapeAppearanceModel =
            generateShapeAppearanceModel(backgroundRadius.dpToPx(view.resources.displayMetrics))
        val background =
            MaterialShapeDrawable(shapeAppearanceModel).apply {
                fillColor = ColorStateList.valueOf(backgroundColor)
            }
        view.background = background
    }

    private fun generateShapeAppearanceModel(radius: Float): ShapeAppearanceModel =
        ShapeAppearanceModel.Builder()
            .setAllCornerSizes(radius)
            .build()
}