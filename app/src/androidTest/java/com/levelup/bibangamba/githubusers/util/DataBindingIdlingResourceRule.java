package com.levelup.bibangamba.githubusers.util;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


public class DataBindingIdlingResourceRule extends TestWatcher {
    private DataBindingIdlingResource mIdlingResource;

    public DataBindingIdlingResourceRule(ActivityTestRule activityTestRule) {
        mIdlingResource = new DataBindingIdlingResource(activityTestRule);
    }

    @Override
    protected void starting(Description description) {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
        super.starting(description);
    }

    @Override
    protected void finished(Description description) {
        IdlingRegistry.getInstance().register(mIdlingResource);
        super.finished(description);
    }
}
