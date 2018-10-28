package com.tysonsapps.locationexample

import android.content.Context
import android.widget.Toast

/**
 * Created by jared on 8/15/17.
 */

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()