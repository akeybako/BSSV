package io.github.akeybako.bssv.network

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    /**
     * [https://docs.github.com/en/rest/reference/search#search-users]
     */
    @GET("search/users")
    suspend fun fetchUsers(
        @Query("q") q: String
    ): ApiResponse<GithubSearchUsersResponse>

    /**
     * [https://docs.github.com/en/rest/reference/users#get-a-user]
     */
    @GET("users/{username}")
    suspend fun fetchUser(
        @Path("username") name: String
    ): ApiResponse<GithubUserResponse>

    /**
     * [https://docs.github.com/en/rest/reference/repos#list-repositories-for-a-user]
     */
    @GET("users/{username}/repos")
    suspend fun fetchRepos(
        @Path("username") name: String,
        @Query("per_page") perPage: Int = 100
    ): ApiResponse<List<GithubReposResponse>>
}