/*
 * Copyright (C) 2015. Manu Sunny <manupsunny@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.manusunny.pinlock.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.os.Vibrator
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.Button
import com.manusunny.pinlock.PinListener
import com.manusunny.pinlock.R

/**
 * ViewAdaptor for Keypad which will add buttons to Keypad
 * @see Keypad
 *
 * @since 1.0.0
 */
class KeypadAdapter(
    /**
     * Stores the context of current activity
     */
    private val context: Context,
    pinListener: PinListener
) : BaseAdapter() {
    /**
     * LayoutInflator which is used to inflate views to UI
     */
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     * Implementation of PinListener interface. Used to handle PIN change events
     */
    private val pinListener: PinListener = pinListener
    override fun getCount(): Int {
        return 11
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return if (position == 10) 0 else ((position + 1) % 10).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: Button = if (convertView == null) {
            inflater.inflate(R.layout.pin_input_button, null) as Button
        } else {
            convertView as Button
        }
        setStyle(view)
        setValues(position, view)
        view.setOnClickListener { v ->
            val duration = 100
            val transition = v.background as TransitionDrawable
            transition.startTransition(duration)
            val pinLength = 4
            val key = v as Button
            val keyText = key.text.toString()
            val currentPin: String = Keypad.onKeyPress(keyText)
            val currentPinLength = currentPin.length
            vibrateIfEnabled()
            pinListener.onPinValueChange(currentPinLength)
            if (currentPinLength == pinLength) {
                pinListener.onCompleted(currentPin)
                Keypad.resetPin()
            }
            transition.reverseTransition(duration)
        }
        return view
    }

    /**
     * Vibrate device on each key press if the feature is enabled
     */
    private fun vibrateIfEnabled() {
        val enabled = false
        if (enabled) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val duration = 20
            v.vibrate(duration.toLong())
        }
    }

    /**
     * Setting Button background styles such as background, size and shape
     * @param view Button itself
     */
    private fun setStyle(view: Button) {
        val textSize = 15
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        val color = Color.WHITE
        view.setTextColor(color)
        val height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 75f, view.resources
                .displayMetrics).toInt()
        val width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 75f, view.resources
                .displayMetrics).toInt()
        view.layoutParams = AbsListView.LayoutParams(width, height)
        val background = R.drawable.rectangle
        view.setBackgroundResource(background)
    }

    /**
     * Setting Button text
     * @param position Position of Button in GridView
     * @param view Button itself
     */
    private fun setValues(position: Int, view: Button) {
        if (position == 10) {
            view.text = "0"
        } else if (position == 9) {
            view.visibility = View.INVISIBLE
        } else {
            view.text = ((position + 1) % 10).toString()
        }
    }

}