/*
 * Copyright (C) 2020 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2020/1/1
 */

package com.jairrab.myapp.utils

import androidx.navigation.NavOptions
import com.jairrab.myapp.R

object NavUtils {

    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.enter_from_right)
        .setExitAnim(R.anim.exit_to_left)
        .setPopEnterAnim(R.anim.enter_from_left)
        .setPopExitAnim(R.anim.exit_to_right)
        .build()
}