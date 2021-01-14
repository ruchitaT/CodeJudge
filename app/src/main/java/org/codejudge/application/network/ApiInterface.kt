package org.codejudge.application.network

import org.codejudge.application.model.BySearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap




public interface ApiInterface {
    @GET("maps/api/place/nearbysearch/json")
    fun getSearchData(
            @Query("location") location: String?,
            @Query("radius") radius: String?,
            @Query("type") type: String?,
            @Query("key") key: String?
    ): Call<BySearchResponse>?

    @GET("maps/api/place/nearbysearch/json")
    fun getSearchDataByKeyword(
        @Query("location") location: String?,
        @Query("radius") radius: String?,
        @Query("type") type: String?,
        @Query("keyword") keyword: String?,
        @Query("key") key: String?
    ): Call<BySearchResponse>?
}