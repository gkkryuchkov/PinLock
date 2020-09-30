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
package com.manusunny.pinlock

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.manusunny.pinlock.components.Keypad
import com.manusunny.pinlock.components.StatusDots

/**
 * Abstract class for basic PIN activity.
 * All subclasses should implement onCompleted(String) method.
 * @since 1.0.0
 */
abstract class BasePinActivity : Activity(), PinListener {
    /**
     * Holds reference to label added to the UI
     */
    private var label: TextView? = null

    /**
     * Holds reference to StatusDots added to the UI
     */
    private var statusDots: StatusDots? = null

    /**
     * Holds reference to forgot button added to the UI
     */
    private var forgetButton: TextView? = null
    private var cancelButton: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        val keypad = findViewById<Keypad>(R.id.keypad)
        keypad.setPinListener(this)
        label = findViewById(R.id.label)
        statusDots = findViewById(R.id.statusDots)
        setupButtons()
        setupStyles()
    }

    /**
     * Setting up cancel and forgot buttons and adding onClickListeners to them
     */
    private fun setupButtons() {
        cancelButton = findViewById(R.id.cancelButton)
        cancelButton!!.setOnClickListener {
            setResult(PinListener.CANCELLED)
            finish()
        }
        forgetButton = findViewById(R.id.forgotPin)
        forgetButton!!.setOnClickListener {
            onForgotPin()
            setResult(PinListener.FORGOT)
            finish()
        }
    }

    /**
     * Setting up color and textSize for cancel/forgot buttons and info text
     */
    private fun setupStyles() {
        val layoutBackground = Color.WHITE
        val layout = findViewById<View>(R.id.pinLockLayout)
        layout.setBackgroundColor(layoutBackground)
        val cancelForgotTextSize = 20
        cancelButton!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelForgotTextSize.toFloat())
        forgetButton!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelForgotTextSize.toFloat())
        val cancelForgotTextColor = Color.BLACK
        cancelButton!!.setTextColor(cancelForgotTextColor)
        if (forgetButton!!.isEnabled) {
            forgetButton!!.setTextColor(cancelForgotTextColor)
        } else {
            forgetButton!!.setTextColor(Color.parseColor("#a9abac"))
        }
        val infoTextSize = 20
        val infoTextColor = Color.BLACK
        label!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, infoTextSize.toFloat())
        label!!.setTextColor(infoTextColor)
    }

    /**
     * Disabling forgot button on request
     */
    fun disableForgotButton() {
        forgetButton!!.isEnabled = false
        forgetButton!!.setTextColor(Color.parseColor("#a9abac"))
    }

    /**
     * Setting label text as String value passed
     * @param text Text to be set as label text
     */
    fun setLabel(text: String?) {
        label!!.text = text
    }

    /**
     * Reset StatusDots to initial state where no dots are filled
     */
    fun resetStatus() {
        statusDots!!.initialize()
    }

    /**
     * Abstract method. Should be implemented by all subclasses.
     * Called when user completes entering PIN
     * @param pin PIN value entered by the user
     */
    abstract override fun onCompleted(pin: String)

    /**
     * Called when user clicks on Keypad
     * @param length Current length of PIN
     */
    override fun onPinValueChange(length: Int) {
        statusDots!!.updateStatusDots(length)
    }

    /**
     * Has to be implemented in confirm PIN activity
     */
    override fun onForgotPin() {
        // handle forgot PIN scenario
    }
}