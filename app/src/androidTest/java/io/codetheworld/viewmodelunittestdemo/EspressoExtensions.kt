package io.codetheworld.viewmodelunittestdemo

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher

class EspressoExtensions {
    companion object {
        fun waitForViewVisible(
            viewMatcher: Matcher<View>,
            timeout: Long = 5000L
        ): ViewInteraction {
            val start = System.currentTimeMillis()
            do {
                try {
                    onView(isRoot()).perform(searchView(viewMatcher))
                    return onView(viewMatcher)
                } catch (e: NoMatchingViewException) {
                    Thread.sleep(200L)
                }
            } while (System.currentTimeMillis() < start + timeout)
            throw RuntimeException("Timeout, no view found: $viewMatcher")
        }

        private fun searchView(matcher: Matcher<View>) = object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "Wait for view: $matcher"
            }

            override fun perform(uiController: UiController, view: View) {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    if (matcher.matches(child) && child.visibility == View.VISIBLE) {
                        return
                    }
                }

                throw NoMatchingViewException.Builder()
                    .withRootView(view)
                    .withViewMatcher(matcher)
                    .build()
            }
        }
    }
}