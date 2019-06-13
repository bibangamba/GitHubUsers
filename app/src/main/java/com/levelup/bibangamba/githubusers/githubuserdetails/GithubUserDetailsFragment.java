package com.levelup.bibangamba.githubusers.githubuserdetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levelup.bibangamba.githubusers.R;
import com.levelup.bibangamba.githubusers.databinding.FragmentGithubUserDetailsBinding;
import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUserViewModel;
import com.levelup.bibangamba.githubusers.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GithubUserDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GithubUserDetailsFragment extends Fragment implements GithubUserDetailsContract.View {
    GithubUserViewModel mGithubUserViewModel;
    private GithubUserDetailsContract.Presenter mPresenter;
    private FragmentGithubUserDetailsBinding mFragmentGithubUserDetailsBinding;
    private GithubUser mGithubUser;

    public GithubUserDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GithubUserDetailsFragment.
     */
    public static GithubUserDetailsFragment newInstance() {
        return new GithubUserDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentGithubUserDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_github_user_details, container, false);

        return mFragmentGithubUserDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mGithubUser = getActivity().getIntent()
                    .getParcelableExtra(Constants.GITHUB_USER_DETAILS_KEY);
            mFragmentGithubUserDetailsBinding.setUserViewModel(mGithubUserViewModel);
            mFragmentGithubUserDetailsBinding.setUserDetailsPresenter(mPresenter);
            showGithubUserDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showGithubUserDetails() {
        mGithubUserViewModel = new GithubUserViewModel();
        mGithubUserViewModel.setGithubUser(mGithubUser);
        mFragmentGithubUserDetailsBinding.setUserViewModel(mGithubUserViewModel);
    }

    @Override
    public void loadMoreGithubUserDetails(GithubUser user) {
        mGithubUserViewModel.setGithubUser(user);
    }

    @Override
    public void requestGithubUserDetailsLoading() {
        mPresenter.loadGithubUserDetails(mGithubUser.getUsername());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

//    @Override
//    public void showNoInternetSnackbar() {
//        //not needed at the moment
//    }

//    @Override
//    public void showErrorOnFailingToLoadGithubUserDetails() {
//        //not needed at the moment
//    }

    @Override
    public void setPresenter(GithubUserDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
