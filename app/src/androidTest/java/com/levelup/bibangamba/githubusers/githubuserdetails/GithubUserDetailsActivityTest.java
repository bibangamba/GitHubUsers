package com.levelup.bibangamba.githubusers.githubuserdetails;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.util.Constants;
import com.levelup.bibangamba.githubusers.util.DataBindingIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class GithubUserDetailsActivityTest {
    private static final String knownJavaDeveloperUsername = "nellyk";

    @Rule
    public IntentsTestRule<GithubUserDetailsActivity> githubUserDetailsActivityIntentsTestRule =
            new IntentsTestRule<>(GithubUserDetailsActivity.class, true, false);

    private DataBindingIdlingResource mDataBindingIdlingResource;

    @Before
    public void intentWithStubbedUserNameAndInfo() {
        GithubUser githubUser = new GithubUser();
        githubUser.setUsername(knownJavaDeveloperUsername);
        githubUser.setProfilePicture("https://avatars3.githubusercontent.com/u/3062772?v=4");
        githubUser.setProfileUrl("https://github.com/nellyk");
        Intent startDetailActivityIntent = new Intent();
        startDetailActivityIntent.putExtra(Constants.GITHUB_USER_DETAILS_KEY, githubUser);
        githubUserDetailsActivityIntentsTestRule.launchActivity(startDetailActivityIntent);

        mDataBindingIdlingResource =
                new DataBindingIdlingResource(githubUserDetailsActivityIntentsTestRule);

        IdlingRegistry.getInstance().register(mDataBindingIdlingResource);
    }

    @Test
    public void detailActivityLayoutIsRendered() {
        onView(ViewMatchers.withId(R.id.detail_activity_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void githubUserDetailsScreenIsShown() {
        registerIdlingResource();
        onView(withId(R.id.usernameTextView)).check(matches(withText(knownJavaDeveloperUsername)));
        onView(withId(R.id.usernameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.shareButton)).check(matches(isDisplayed()));
        onView(withId(R.id.profileUrlTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.followsValueTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.followersValueTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.reposValueTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.followsLabelTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.followersLabelTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.reposLabelTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.profilePictureImageView)).check(matches(isDisplayed()));
    }

    @Test
    public void homeAsUpButtonIsShown() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).check(matches(isDisplayed()));
    }


    @Test
    public void furtherUserDetailsAreFetchedAndShownOnDetailsView() {
        registerIdlingResource();

        onView(withId(R.id.followersValueTextView)).check(matches(withText("51")));
        onView(withId(R.id.reposValueTextView)).check(matches(withText("44")));
        onView(withId(R.id.followsValueTextView)).check(matches(withText("107")));
    }

    /**
     * test to check that chooser intent is sent but do not start activity
     */
    @Test
    public void testOnClickShareButton() {
        Intent intent = new Intent(Intent.ACTION_CHOOSER);
        Instrumentation.ActivityResult intentResult =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        intending(anyIntent()).respondWith(intentResult);
        onView(withId(R.id.shareButton)).perform(click());
        intended(hasExtra(Intent.EXTRA_TITLE, "Share @nellyk's profile using:"));
    }

    /**
     * Unregister Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(
                githubUserDetailsActivityIntentsTestRule.getActivity().getCountingIdlingResource());
        IdlingRegistry.getInstance().unregister(mDataBindingIdlingResource);

    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    private void registerIdlingResource() {
        IdlingRegistry.getInstance().register(
                githubUserDetailsActivityIntentsTestRule.getActivity().getCountingIdlingResource());
    }
}
