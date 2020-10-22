package com.teamig.imgraphy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ImgraphyService {

    @Headers({"Content-Type: application/json"})
    @GET("api/list.php")
    Call<List<ImgraphyType.Graphy>> graphyList(@Query("max") int max, @Query("page") int page, @Query("keyword") String keyword);

}
