package com.gsb.colorpickerexample

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gsb.colorpickerexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var colorPicker: ImageView
    private lateinit var displayColorBox:View
    private lateinit var displayColorText:TextView

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews(binding)
        initializeColorPicker()
    }

    private fun initViews(binding: ActivityMainBinding) {
        colorPicker = binding.colorPicker
        displayColorBox = binding.displayColorBox
        displayColorText = binding.displayColorText
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeColorPicker() {
        colorPicker.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_UP) {
                val colorPickerBitmap = getBitmapFromView(colorPicker)
                val pixels = colorPickerBitmap?.getPixel(event.x.toInt(),event.y.toInt())
                val r = pixels?.let { Color.red(it) }
                val g = pixels?.let { Color.green(it) }
                val b = pixels?.let { Color.blue(it) }
                val hex = "#" + pixels?.let { Integer.toHexString(it) }

                displayColorBox.setBackgroundColor(Color.rgb(r!!,g!!,b!!))
                displayColorText.text = hex
            }
            v?.onTouchEvent(event) ?: true
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}