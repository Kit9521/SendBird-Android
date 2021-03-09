package com.sendbird.android.sample.main

import com.sendbird.android.sample.R
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.onView
import org.hamcrest.Matchers.allOf
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.BeforeClass


@LargeTest
class OpenChannelActivityTest {

    // login info
    private val userIdA = "99901"
    private val userNicknameA = "Tester 1"
    private val userIdB = "99902"
    private val userNicknameB = "Tester 2"

    // name of the pre-created open channel
    private val openChannelName = "Room 001"

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

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

        // Enter open channel list
        onView(withId(R.id.linear_layout_open_channels)).perform(click())

        // select specified open channel
        val openChannelMatcher =
            allOf(withId(R.id.text_open_channel_list_name), withText(openChannelName))
        onView(isRoot()).perform(waitForView(openChannelMatcher))
        onView(openChannelMatcher)
            .perform(click())

        // send text message to the chatroom
        onView(isRoot()).perform(waitForView(withId(R.id.edittext_chat_message)))
        onView(withId(R.id.edittext_chat_message))
            .perform(typeText(textMessage), closeSoftKeyboard())
        onView(withId(R.id.button_open_channel_chat_send)).perform(click())

        // Verify the message just sent able to display on the chatroom
        val chatBox = allOf(
            withChild(allOf(withId(R.id.text_open_chat_nickname), withText(userNicknameA))),
            withChild(allOf(withId(R.id.text_open_chat_message), withText(textMessage)))
        )

        onView(isRoot()).perform(waitForView(chatBox))
        onView(chatBox).check(matches(isDisplayed()))

        // TODO: send image message
        // ...

        // Back to main activity and logout
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withId(R.id.button_disconnect)).perform(click())

    }

    @Test
    fun showMessage() {
        login(userIdB, userNicknameB)

        // Enter open channel list
        onView(withId(R.id.linear_layout_open_channels)).perform(click())

        // select specified open channel
        val openChannelMatcher =
            allOf(withId(R.id.text_open_channel_list_name), withText(openChannelName))
        onView(isRoot()).perform(waitForView(openChannelMatcher))
        onView(openChannelMatcher)
            .perform(click())

        // Check is text message displayed
        val chatBox = allOf(
            withChild(allOf(withId(R.id.text_open_chat_nickname), withText(userNicknameA))),
            withChild(allOf(withId(R.id.text_open_chat_message), withText(textMessage)))
        )
        onView(isRoot()).perform(waitForView(chatBox))
        onView(
            chatBox
        ).check(matches(isDisplayed()))

        // TODO: Check image message is displayed
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

        // FIXME: waitForView doesn't work here, use Thread.sleep instead
        Thread.sleep(5000)
    }

}