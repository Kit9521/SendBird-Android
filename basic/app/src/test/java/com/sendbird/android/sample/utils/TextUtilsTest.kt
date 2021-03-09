package com.sendbird.android.sample.utils

import org.junit.Assert
import org.junit.Test


class TextUtilsTest {
    
    @Test
    fun testIsEmpty(){

        Assert.assertTrue(TextUtils.isEmpty(""))
        Assert.assertTrue(TextUtils.isEmpty(null))

        Assert.assertFalse(TextUtils.isEmpty(" "))
        Assert.assertFalse(TextUtils.isEmpty("null"))
        Assert.assertFalse(TextUtils.isEmpty("123123"))
    }

}