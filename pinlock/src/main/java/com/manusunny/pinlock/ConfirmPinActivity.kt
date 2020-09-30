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

import android.os.Bundle

/**
 * Abstract class for PIN confirm activity.
 * Subclass this activity to show ConfirmPin screen.
 * All subclasses should implement isPinCorrect() method
 * @since 1.0.0
 */
abstract class ConfirmPinActivity : BasePinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLabel(getString(R.string.message_enter_pin))
    }

    /**
     * Implementation of BasePinActivity method
     * @param pin PIN value entered by user
     */
    override fun onCompleted(pin: String) {
        resetStatus()
        if (isPinCorrect(pin)) {
            setResult(PinListener.SUCCESS)
            finish()
        } else {
            setLabel(getString(R.string.message_invalid_pin))
        }
    }

    /**
     * Abstract method which decides the PIN entered by user is correct or not
     * @param pin PIN value entered by user
     * @return Boolean value indicates the status of PIN entered
     */
    abstract fun isPinCorrect(pin: String?): Boolean

    /**
     * Abstract method which handles PIN forgot scenario
     */
    abstract override fun onForgotPin()
    override fun onBackPressed() {
        setResult(PinListener.CANCELLED)
        finish()
    }
}