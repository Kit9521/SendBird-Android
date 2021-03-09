package com.sendbird.android.sample.main

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import com.sendbird.android.sample.R
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import org.hamcrest.Matchers.allOf
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.BeforeClass


@LargeTest
class OpenChannelActivityTest {

    private val userIdA = "99901"
    private val userNicknameA = "Peter"
    private val userIdB = "99902"
    private val userNicknameB = "Jackson"

    // pre-created open channel
    private val openChannelName = "T01"

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    companion object {

        var textMessage = ""

        @JvmStatic
        @BeforeClass
        fun setup() {
            // generate unique message each time
            textMessage = getRandomText(15)
        }

        private fun getRandomText(length: Int): String {
            /*
             *  return random string with specified length
             */
            val charSet = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            return List(length) { charSet.random() }.joinToString("")
        }
    }

    @Test
    fun sendMessage() {
        login(userIdA, userNicknameA)

        onView(withId(R.id.linear_layout_open_channels)).perform(click())

        val openChannelMatcher = allOf(withId(R.id.text_open_channel_list_name), withText(openChannelName))
        onView(isRoot()).perform(waitForView(openChannelMatcher))
        onView(openChannelMatcher)
            .perform(click())

        onView(isRoot()).perform(waitForView(withId(R.id.edittext_chat_message)))
        onView(withId(R.id.edittext_chat_message))
            .perform(typeText(textMessage), closeSoftKeyboard())
        onView(withId(R.id.button_open_channel_chat_send)).perform(click())


        val chatBox = allOf(
            withChild(allOf(withId(R.id.text_open_chat_nickname), withText(userNicknameA))),
            withChild(allOf(withId(R.id.text_open_chat_message), withText(textMessage)))
        )

        onView(isRoot()).perform(waitForView(chatBox))
        onView(chatBox).check(matches(isDisplayed()))

        val resultData = Intent()
        resultData.setData(Uri.parse("content://com.android.providers.downloads.documents/document/4"))
        val result = ActivityResult(Activity.RESULT_OK, resultData)

        val expectedIntent = allOf(hasAction(Intent.ACTION_CHOOSER))
//        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        Intents.init()
        intending(expectedIntent).respondWith(result)

        ContentResolver.SCHEME_ANDROID_RESOURCE

        onView(withId(R.id.button_open_channel_chat_upload)).perform(click())
        Thread.sleep(3000)
        intended(expectedIntent)
        Intents.release()
        Thread.sleep(3000)
        onView(withText("UPLOAD")).perform(click())
        Thread.sleep(3000)
        // Back to home page and logout
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withId(R.id.button_disconnect)).perform(click())

    }


    @Test
    fun showMessage() {
        login(userIdB, userNicknameB)
        onView(withId(R.id.linear_layout_open_channels)).perform(click())
        Thread.sleep(3000)

        onView(withText(openChannelName)).perform(click())

        Thread.sleep(3000)

        // Check text displayed
        onView(
            allOf(
                withChild(allOf(withId(R.id.text_open_chat_nickname), withText(userNicknameA))),
                withChild(allOf(withId(R.id.text_open_chat_message), withText(textMessage)))
            )
        ).check(matches(isDisplayed()))

        // TODO: Check image displayed
        // ...

    }

    private fun login(userId: String, userNickname: String) {

        onView(withId(R.id.text_login_versions))
            .check(matches(isDisplayed()))

        onView(withId(R.id.edittext_login_user_id))
            .check(matches(isDisplayed()))
            .perform(replaceText(userId), closeSoftKeyboard())

        onView(withId(R.id.edittext_login_user_nickname))
            .check(matches(isDisplayed()))
            .perform(replaceText(userNickname), closeSoftKeyboard())

        onView(withId(R.id.button_login_connect))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(5000)
    }

}