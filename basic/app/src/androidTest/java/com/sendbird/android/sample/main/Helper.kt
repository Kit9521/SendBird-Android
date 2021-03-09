package com.sendbird.android.sample.main

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

fun waitForView(viewMatcher: Matcher<View>, timeout: Int=5000): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>{
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view during $timeout millis."
        }

        override fun perform(uiController: UiController, rootView: View) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + timeout

            do {
                // Iterate through all views on the screen and see if the view we are looking for is there already
                for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                    // found view with required matcher
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(100)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withCause(TimeoutException())
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(rootView))
                .build()
        }
    }
}