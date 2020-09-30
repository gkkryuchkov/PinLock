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
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.GridView
import com.manusunny.pinlock.PinListener
import com.manusunny.pinlock.R

/**
 * GridView of buttons for PIN input. It uses KeypadAdaptor for filling in the buttons
 * @see KeypadAdapter
 *
 * @since 1.0.0
 */
class Keypad(
        /**
         * Stores the context of current activity
         */
        context: Context, attrs: AttributeSet?) : GridView(context, attrs) {

    /**
     * Setting up vertical and horizontal spacing for the view
     */
    private fun setSpacing() {
        val verticalSpacing = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15f, resources
                .displayMetrics).toInt()
        val horizontalSpacing = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15f, resources
                .displayMetrics).toInt()
        setVerticalSpacing(verticalSpacing)
        setHorizontalSpacing(horizontalSpacing)
    }

    /**
     * Setting up layout dimensional parameters for the view
     */
    fun setLayoutParameters() {
        val params = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = 17
        layoutParams = params
    }

    /**
     * Setting listener for PIN lock which handles pinChange, forgotPIN etc
     * @param pinListener Implementation of Interface PinListener.
     * @see PinListener
     */
    fun setPinListener(pinListener: PinListener) {
        adapter = KeypadAdapter(context, pinListener)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setLayoutParameters()
    }

    /**
     * Executed just before destroying Keypad object. Used to recycle StyledAttributes properly
     * @throws Throwable
     */

    companion object {
        /**
         * Stores the PIN value entered by user
         */
        private var pin = ""

        /**
         * Getting the value of key pressed and appending it to current PIN
         */
        fun onKeyPress(key: String): String {
            pin += key
            return pin
        }

        /**
         * Reset current PIN to initial state
         */
        fun resetPin() {
            pin = ""
        }
    }

    init {
        numColumns = 3
        setSpacing()
    }
}