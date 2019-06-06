package com.levelup.bibangamba.githubusers.view;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.levelup.bibangamba.githubusers.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class GithubUserListTest {
    private static final String knownJavaDeveloperUsername = "nellyk";
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityLayoutIsRendered() throws Exception {
        onView(ViewMatchers.withId(R.id.main_activity_constraint_layout)).check(matches(isDisplayed()));
    }

//    @Test
//    public void loadingProgressDialogIsShown() throws Exception {
//        onView(ViewMatchers.withId(R.id.github_users_progress_bar)).check(matches(isDisplayed()));
//        onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())));
//    }

    @Test
    public void recyclerViewIsShown() throws Exception {
        registerIdlingResource();
        onView(ViewMatchers.withId(R.id.recycler_view_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeToRefreshWorks() throws Exception {
        onView(ViewMatchers.withId(R.id.github_users_swipe_refresh_layout)).perform(swipeDown());
    }

    @Test
    public void scrollThroughListOfGithubUsers() throws Exception {
        registerIdlingResource();
        onView(withId(R.id.recycler_view_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.github_users_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view_layout)).perform(scrollTo(hasDescendant(withText(knownJavaDeveloperUsername))));
    }

    @Test
    public void clickingOnAUserShouldGoToDetailsView() throws Exception {
        registerIdlingResource();

        onView(withId(R.id.recycler_view_layout))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(knownJavaDeveloperUsername)), click()));
        onView(withId(R.id.usernameTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void clickingOnBackButtonInDetailActivityTakesUsBackToMainActivity() throws Exception {
        registerIdlingResource();

        onView(withId(R.id.recycler_view_layout))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(knownJavaDeveloperUsername)), click()));
        onView(withId(R.id.usernameTextView)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(ViewMatchers.withId(R.id.main_activity_constraint_layout)).check(matches(isDisplayed()));
    }


    /**
     * Unregister Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mainActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    private void registerIdlingResource() {
        IdlingRegistry.getInstance().register(mainActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
