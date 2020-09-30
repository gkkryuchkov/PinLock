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
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.manusunny.pinlock.R

/**
 * Component that constitutes a single dot on StatusDots
 * @since 1.0.0
 */
class Dot(
    context: Context?,
    filled: Boolean
) : View(context) {
    /**
     * Setting up layout dimensional parameters for the view
     */
    private fun setLayoutParameters() {
        val dotDiameter = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 20f, resources
                .displayMetrics
        ).toInt()
        val margin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 30f, resources
                .displayMetrics
        ).toInt()
        val params = LinearLayout.LayoutParams(dotDiameter, dotDiameter)
        params.setMargins(margin, 0, margin, 0)
        layoutParams = params
    }

    /**
     * Setting up background for the view. Should pass shapes for both filled and empty dots.
     * Otherwise will use the default backgrounds
     */
    private fun setBackground(filled: Boolean) {
        val background = if (filled) {
            R.drawable.dot_filled
        } else {
            R.drawable.dot_empty
        }
        setBackgroundResource(background)
    }

    /**
     * @param context Calling activity as context
     * @param styledAttributes TypedArray of styled attributes passed to the element
     * @param filled Indicates whether to fill the dot or not
     */
    init {
        setBackground(filled)
        setLayoutParameters()
    }
}