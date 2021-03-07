package com.sendbird.android.sample.utils

import org.junit.Assert
import org.junit.Test


class TextUtilsTest {

    @Test
    fun isEmpty_ReturnTrue(){
        Assert.assertTrue(TextUtils.isEmpty(""))
        Assert.assertTrue(TextUtils.isEmpty(null))
    }

    @Test
    fun isEmpty_ReturnFalse(){
        Assert.assertFalse(TextUtils.isEmpty(""))
        Assert.assertFalse(TextUtils.isEmpty("null"))
        Assert.assertFalse(TextUtils.isEmpty("123123"))
    }

}