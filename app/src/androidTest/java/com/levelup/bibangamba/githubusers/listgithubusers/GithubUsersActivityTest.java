package com.levelup.bibangamba.githubusers.listgithubusers;

import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.levelup.bibangamba.githubusers.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class GithubUsersActivityTest {
    public static final String SNACKBAR_RETRY_BUTTON_TEXT = "RETRY";
    private static final String KNOWN_JAVA_DEVELOPER_USERNAME = "nellyk";
    private static final int POSITION_BELOW_KNOWN_DEV = 26;
    private static final String NO_INTERNET_TEXT = "No internet detected.";
    @Rule
    public ActivityTestRule<GithubUsersActivity> mGithubUsersActivityTestRule = new ActivityTestRule<>(GithubUsersActivity.class);
    private UiDevice mUiDevice;

    @Test
    public void mainActivityLayoutIsRendered() {
        onView(ViewMatchers.withId(R.id.main_activity_constraint_layout)).check(matches(isDisplayed()));
    }

//    @Test
//    public void loadingProgressDialogIsShown() {
//        onView(ViewMatchers.withId(R.id.github_users_progress_bar)).check(matches(isDisplayed()));
//        onView(withId(R.id.recycler_view_layout)).check(matches(not(isDisplayed())));
//    }

//    @Test
//    public void recyclerViewIsShown() {
//        registerIdlingResource();
//        onView(ViewMatchers.withId(R.id.recycler_view_layout))
//                .check(matches(isDisplayed()));
//    }

    @Test
    public void swipeToRefreshWorks() {
        registerIdlingResource();
        onView(ViewMatchers.withId(R.id.github_users_swipe_refresh_layout)).perform(swipeDown());
    }

    @Test
    public void scrollThroughListOfGithubUsers() {
        registerIdlingResource();
        onView(withId(R.id.recycler_view_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.github_users_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view_layout))
                .perform(scrollTo(hasDescendant(withText(KNOWN_JAVA_DEVELOPER_USERNAME))));
    }

    @Test
    public void clickingOnAUserShouldGoToDetailsView() {
        registerIdlingResource();

        onView(withId(R.id.recycler_view_layout))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(KNOWN_JAVA_DEVELOPER_USERNAME)), click()));
        onView(withId(R.id.usernameTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void clickingOnBackButtonInDetailActivityTakesUsBackToMainActivity() {
        registerIdlingResource();

        onView(withId(R.id.recycler_view_layout))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(KNOWN_JAVA_DEVELOPER_USERNAME)), click()));
        onView(withId(R.id.usernameTextView)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(ViewMatchers.withId(R.id.main_activity_constraint_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewStateIsRestoredOnScreenOrientationChange() {
        registerIdlingResource();
        onView(withId(R.id.recycler_view_layout))
                .perform(scrollToPosition(POSITION_BELOW_KNOWN_DEV));
        mGithubUsersActivityTestRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withText(KNOWN_JAVA_DEVELOPER_USERNAME)).check(matches(isDisplayed()));

    }

    @Test
    public void testNoNetworkFunctionality() throws UiObjectNotFoundException {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        toggleAirplaneMode();
        onView(ViewMatchers.withId(R.id.github_users_swipe_refresh_layout)).perform(swipeDown());
        onView(withText(NO_INTERNET_TEXT)).check(matches(isDisplayed()));
        onView(withText(SNACKBAR_RETRY_BUTTON_TEXT)).perform(click());
        onView(withText(NO_INTERNET_TEXT)).check(matches(isDisplayed()));
        toggleAirplaneMode();
        onView(withText(SNACKBAR_RETRY_BUTTON_TEXT)).perform(click());
        onView(withText(NO_INTERNET_TEXT)).check(doesNotExist());

    }

    public void toggleAirplaneMode() throws UiObjectNotFoundException {
        mUiDevice.openNotification();
        swipeNotificationBar(false);
        swipeNotificationBar(false);

        final int firstInstance = 1;
        UiObject airplaneModeButton = mUiDevice.findObject(
                new UiSelector().textStartsWith("Airplane mode").instance(firstInstance));
        if (airplaneModeButton.exists()) {
            airplaneModeButton.click();
        } else {
            mUiDevice.findObject(
                    new UiSelector().text("Airplane mode")).click();
        }

        swipeNotificationBar(true);
        swipeNotificationBar(true);

    }

    public void toggleAirplaneMode2() throws UiObjectNotFoundException {
        mUiDevice.openQuickSettings();
        UiObject airplaneModeButton = mUiDevice.findObject(
                new UiSelector().textStartsWith("Airplane mode"));
        if (airplaneModeButton.exists()) {
            airplaneModeButton.click();
        } else {
            mUiDevice.findObject(
                    new UiSelector().text("Airplane mode")).click();
        }
        mUiDevice.pressBack();
        mUiDevice.pressBack();
    }

    public void swipeNotificationBar(boolean isSwipeUp) {
        final int bottomOfScreen = mUiDevice.getDisplayHeight();
        int deviceWidth = mUiDevice.getDisplayWidth();
        int xAxisSwipePosition = (int) (deviceWidth * 0.5);
        final int topOfScreen = 0;
        final int steps = 100;

        if (isSwipeUp) {
            mUiDevice.swipe(xAxisSwipePosition, bottomOfScreen, xAxisSwipePosition, topOfScreen, steps);
        } else {
            mUiDevice.swipe(xAxisSwipePosition, topOfScreen, xAxisSwipePosition, bottomOfScreen, steps);
        }
    }


    /**
     * Unregister Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mGithubUsersActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    private void registerIdlingResource() {
        IdlingRegistry.getInstance().register(mGithubUsersActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
