package com.teamig.imgraphy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Imgraphy {

    Retrofit retrofit;
    ImgraphyService service;

    private List<ImgraphyType.Graphy> graphyList;
    private ImgraphyType.Options options;

    public Imgraphy(ImgraphyType.Options options) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.novang.tk/imgraphy/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ImgraphyService.class);
        setOptions(options);
    }

    public void setOptions(ImgraphyType.Options options) {
        this.options = options;
    }

    public ImgraphyType.Options getOptions() {
        return options;
    }

    public List<ImgraphyType.Graphy> getList() {
        return graphyList;
    }

    public void refreshList() {
        requestGraphyList(this.options, null);
    }

    public void refreshList(Object object) {
        requestGraphyList(this.options, object);
    }

    private void requestGraphyList(ImgraphyType.Options options, Object object) {
        Call<List<ImgraphyType.Graphy>> graphyCall = service.graphyList(options.count_per_page, options.page, options.keyword);

        graphyCall.enqueue(new Callback<List<ImgraphyType.Graphy>>() {
            @Override
            public void onResponse(Call<List<ImgraphyType.Graphy>> call, Response<List<ImgraphyType.Graphy>> response) {
                graphyList = response.body();
                ((GraphyListAdapter) object).updateList(graphyList);
            }

            @Override
            public void onFailure(Call<List<ImgraphyType.Graphy>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
