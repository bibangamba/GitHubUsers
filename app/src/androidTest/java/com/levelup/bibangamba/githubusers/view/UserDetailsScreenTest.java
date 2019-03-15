package com.levelup.bibangamba.githubusers.view;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.levelup.bibangamba.githubusers.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import model.GithubUsers;
import view.DetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class UserDetailsScreenTest {
    private static final String knownJavaDeveloperUsername = "nellyk";


    @Rule
    public IntentsTestRule<DetailActivity> detailActivityActivityTestRule = new IntentsTestRule<>(DetailActivity.class, true, false);

    @Before
    public void intentWithStubbedUserNameAndInfo() {
        GithubUsers githubUser = new GithubUsers();
        githubUser.setUsername("nellyk");
        githubUser.setProfilePicture("https://avatars3.githubusercontent.com/u/3062772?v=4");
        githubUser.setProfileUrl("https://github.com/nellyk");
        Intent startDetailActivityIntent = new Intent();
        startDetailActivityIntent.putExtra(getResourceString(R.string.github_user_details), githubUser);
        detailActivityActivityTestRule.launchActivity(startDetailActivityIntent);
    }

    @Test
    public void githubUserDetailsScreenIsShown() throws Exception {
        onView(withId(R.id.usernameTextView)).check(matches(withText(knownJavaDeveloperUsername)));
        onView(withId(R.id.profilePictureImageView)).check(matches(isDisplayed()));
    }

    @Test
    public void furtherUserDetailsTextViewsShowLoadingStateWhileDataIsFetched() throws Exception {
        onView(withId(R.id.followersValueTextView)).check(matches(withText(R.string.default_followers_value)));
        onView(withId(R.id.reposValueTextView)).check(matches(withText(R.string.default_repos_value)));
        onView(withId(R.id.followsValueTextView)).check(matches(withText(R.string.default_follows_value)));
    }

    @Test
    public void furtherUserDetailsAreFetchedAndShownOnDetailsView() throws Exception {
        registerIdlingResource();

        onView(withId(R.id.followersValueTextView)).check(matches(withText("51")));
        onView(withId(R.id.reposValueTextView)).check(matches(withText("44")));
        onView(withId(R.id.followsValueTextView)).check(matches(withText("107")));
    }

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }

    /**
     * Unregister Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(detailActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    private void registerIdlingResource() {
        IdlingRegistry.getInstance().register(detailActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
