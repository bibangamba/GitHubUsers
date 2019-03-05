package service;

import model.GithubUsers;
import model.GithubUsersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubUsersAPI {
    @GET("search/users?q=type:User+location:Nairobi+language:JAVA")
    Call<GithubUsersResponse> getAllUsers();

    @GET("users/{username}")
    Call<GithubUsers> getUserInformation(@Path("username") String username);
}
