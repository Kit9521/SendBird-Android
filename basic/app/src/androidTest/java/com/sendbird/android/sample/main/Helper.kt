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
            // FIXME: update it
            return "wait for a specific view with during $timeout millis."
        }

        override fun perform(uiController: UiController, rootView: View) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + timeout

            do {
                // Iterate through all views on the screen and see if the view we are looking for is there already
                for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                    // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                // Loops the main thread for a specified period of time.
                // Control may not return immediately, instead it'll return after the provided delay has passed and the queue is in an idle state again.
                uiController.loopMainThreadForAtLeast(100)
            } while (System.currentTimeMillis() < endTime) // in case of a timeout we throw an exception -&gt; test fails
            throw PerformException.Builder()
                .withCause(TimeoutException())
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(rootView))
                .build()
        }
    }
}