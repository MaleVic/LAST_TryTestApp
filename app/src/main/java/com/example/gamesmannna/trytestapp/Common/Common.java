package com.example.gamesmannna.trytestapp.Common;

import com.example.gamesmannna.trytestapp.API.IApiService;
import com.example.gamesmannna.trytestapp.Model.User;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gamesmannna on 03.06.2018.
 */

public class Common {
    public static User CURRENT_USER;
    public static String SERVER_NAME="http://85.143.223.183:5000";
    public static IApiService ApiService;
    public Common()
    {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_NAME)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService = retrofit.create(IApiService.class);
    }
}
