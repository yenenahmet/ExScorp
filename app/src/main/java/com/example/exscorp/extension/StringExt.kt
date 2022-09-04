package com.example.exscorp.extension

import java.lang.NumberFormatException

fun String?.checkNext(): Boolean {
    try {
        if (this.isNullOrEmpty()) {
            return false
        }
        return true
    } catch (ex: NumberFormatException) {
        return false
    }
}