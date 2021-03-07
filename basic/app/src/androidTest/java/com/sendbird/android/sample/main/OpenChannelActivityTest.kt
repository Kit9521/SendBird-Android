package com.sendbird.android.sample.main

import com.sendbird.android.sample.R
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.Espresso.onView
import org.hamcrest.Matchers.allOf
import androidx.test.filters.LargeTest
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

    companion object {

        var textMessage = ""

        @JvmStatic
        @BeforeClass
        fun setup() {
            textMessage = getRandomText(15)
        }

        private fun getRandomText(length: Int = 5): String {

            val charSet = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return List(length) { charSet.random() }.joinToString("")
        }
    }

    @Test
    fun sendMessage() {
        login(userIdA, userNicknameA)

        // TODO: check for elements
        // ...

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


        // TODO: upload image
//        onView(withId(R.id.button_open_channel_chat_upload)).perform(click())

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

        onView(isRoot()).perform(waitForView(withId(R.id.toolbar_main)))


    }


}