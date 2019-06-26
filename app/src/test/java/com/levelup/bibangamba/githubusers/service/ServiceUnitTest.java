package com.levelup.bibangamba.githubusers.service;

import com.levelup.bibangamba.githubusers.model.GithubUser;
import com.levelup.bibangamba.githubusers.model.GithubUsersResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import retrofit2.Call;

import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@SuppressStaticInitializationFor("com.levelup.bibangamba.githubusers.service.GithubService")

@RunWith(PowerMockRunner.class)
@PrepareForTest(GithubService.class)
public class ServiceUnitTest {
    @Test
    public void retrofitInstance_isSingleton() {
//        PowerMockito.mockStatic(GithubService.class);
//        GithubUsersAPI apiInstance = new GithubUsersAPI() {
//            @Override
//            public Call<GithubUsersResponse> getAllUsers() {
//                return null;
//            }
//
//            @Override
//            public Call<GithubUser> getUserInformation(String username) {
//                return null;
//            }
//        };
//
//        when(GithubService.getServiceInstance()).thenReturn(apiInstance);
//
//        GithubUsersAPI instance1 = GithubService.getServiceInstance();
//        GithubUsersAPI instance2 = GithubService.getServiceInstance();
//
//        assertSame(instance1, instance2);
    }
}
