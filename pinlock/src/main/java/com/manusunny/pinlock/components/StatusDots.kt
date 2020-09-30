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
import android.util.AttributeSet
import android.widget.LinearLayout
import com.manusunny.pinlock.R

/**
 * Layout which contains set of Dots which shows PIN input status
 * @see Dot
 *
 * @since 1.0.0
 */
class StatusDots(
        /**
         * Current application context
         */
        context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    /**
     * TypedArray of styled attributes passed to the element
     */

    /**
     * Adding Dot objects to layout
     * @param length Length of PIN entered so far
     */
    private fun addDots(length: Int) {
        removeAllViews()
        val pinLength = 4
        for (i in 0 until pinLength) {
            val dot = Dot(context, i < length)
            addView(dot)
        }
    }

    /**
     * Executed just before destroying StatusDots object. Used to recycle StyledAttributes properly
     * @throws Throwable Throws exception
     */


    /**
     * Initialize StatusDots. Set backgrounds of all dots to empty background
     */
    fun initialize() {
        addDots(0)
    }

    /**
     * Updates StatusDots. Set backgrounds of {pinLength} dots to filled background
     * @param pinLength Current length of PIN entered
     */
    fun updateStatusDots(pinLength: Int) {
        addDots(pinLength)
    }

    init {
        initialize()
    }
}