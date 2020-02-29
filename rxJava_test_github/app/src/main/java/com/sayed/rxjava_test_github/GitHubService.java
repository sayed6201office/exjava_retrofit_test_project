package com.sayed.rxjava_test_github;

import com.sayed.rxjava_test_github.model.GitHubRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubService {

    //GET api link : //https://api.github.com/users/:username/starred
    @GET("users/{user}/starred")
    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);
}