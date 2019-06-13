package com.levelup.bibangamba.githubusers.util;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataBindingIdlingResource implements IdlingResource {
    private static final int MILLIS_TO_NEXT_FRAME = 16;
    // give it a unique id to workaround an espresso bug where you cannot register/unregister
    // an idling resource w/ the same name.
    private final String id = UUID.randomUUID().toString();
    private List<IdlingResource.ResourceCallback> idlingCallbacks = new ArrayList<>();
    // holds whether isIdle is called and the result was false. We track this to avoid calling
    // onTransitionToIdle callbacks if Espresso never thought we were idle in the first place.
    private boolean wasNotIdle = false;

    private ActivityTestRule mActivityTestRule;

    public DataBindingIdlingResource(ActivityTestRule activityTestRule) {
        this.mActivityTestRule = activityTestRule;
    }

    @Override
    public String getName() {
        return String.format("DataBinding %s", id);
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = (getBinding() == null) || !getBinding().hasPendingBindings();

        if (idle) {
            if (wasNotIdle) {
                for (ResourceCallback idlingCallback : idlingCallbacks) {
                    idlingCallback.onTransitionToIdle();
                }
            }
            wasNotIdle = false;
        } else {
            wasNotIdle = true;
            //check the next frame
            mActivityTestRule.getActivity().findViewById(android.R.id.content)
                    .postDelayed(this::isIdleNow, MILLIS_TO_NEXT_FRAME);
        }

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        idlingCallbacks.add(callback);
    }

    private ViewDataBinding getBinding() {
        if (mActivityTestRule.getActivity() != null) {
            FragmentActivity activity = (FragmentActivity) mActivityTestRule.getActivity();
            List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
            List<ViewDataBinding> bindings = new ArrayList<>();
            for (Fragment fragment : fragments) {
                if (fragment.getView() != null) {
                    bindings.add(DataBindingUtil.getBinding(fragment.getView()));
                }
            }

            /*
              Our application only has a single fragment per activity so we are sure we only get
              one binding in the list. Additionally, our application doesn't have nested fragments
              so there is no need to go deeper i.e. childFragmentManager and etc
             */
            return bindings.get(0);
        } else {
            return null;
        }
    }
}
