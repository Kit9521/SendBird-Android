package com.sendbird.android.sample.utils

import org.junit.Assert
import org.junit.Test
import org.junit.Before

class DateUtilsTest {

    // Mar 09 2021 00:00:00 (UTC)
    private val ts: Long = 1615248000000

    private val oneDay: Long = 24 * 60 * 60 * 1000
    private val oneHour: Long = 60 * 60 * 1000


    @Before
    fun setup(){
        System.setProperty("user.timezone", "UTC")
    }

    @Test
    fun testIsToday(){
        Assert.assertTrue(DateUtils.isToday(System. currentTimeMillis()))
        Assert.assertFalse(DateUtils.isToday(System. currentTimeMillis() - oneDay))
        Assert.assertFalse(DateUtils.isToday(System. currentTimeMillis() + oneDay))
    }

    @Test
    fun testHasSameDate(){

        Assert.assertTrue(DateUtils.hasSameDate(ts, ts))
        Assert.assertTrue(DateUtils.hasSameDate(ts, ts + oneHour))
        Assert.assertFalse(DateUtils.hasSameDate(ts, ts - oneHour))
    }

    @Test
    fun testFormatDate(){
        Assert.assertEquals(DateUtils.formatDate(ts), "March 09")
        Assert.assertEquals(DateUtils.formatDate(ts - 1), "March 08")
        Assert.assertEquals(DateUtils.formatDate(ts + oneHour), "March 09")
        Assert.assertEquals(DateUtils.formatDate(ts + oneDay), "March 10")
    }
}