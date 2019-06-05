package com.example.gamesmannna.trytestapp.API;

import com.example.gamesmannna.trytestapp.Model.Movie;
import com.example.gamesmannna.trytestapp.Model.*;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiService
{
    // получить все фильмы
    @GET("api/Movies")
    Call<List<Movie>> GetMoviesColl();
    //получить фильм по его ID
    @GET("api/Movies/{id}")
    Call<Movie> GetMovieById(@Path("id") int movieId);

    @POST("api/Movies")
    Call<Movie> CreateMovie(@Body Movie movie);


    @GET("api/Categories")
    Call<List<Category>> GetCategoryColl();

    @GET("api/Categories/{id}")
    Call<Category> GetCategoryById(@Path("id") int categoryId);

    @POST("api/Categories")
    Call<Category> CreateCategory(@Body Category category);


    @GET("api/Orders")
    Call<List<Order>> GetOrderColl();

    @GET("api/Orders/{id}")
    Call<Order> GetOrderById(@Path("id") int orderId);

    @POST("api/Orders")
    Call<Order> CreateOrder(@Body Order order);


    @GET("api/Requests")
    Call<List<Request>> GetRequestColl();

    @GET("api/Requests/{id}")
    Call<Request> GetRequestById(@Path("id") int requestId);

    @POST("api/Requests")
    Call<Request> CreateRequest(@Body Request request);


    @GET("api/Users")
    Call<List<User>> GetUserColl();

    @GET("api/Users/{id}")
    Call<User> GetUserByPhoneId(@Path("id") String userPhoneId);

    @POST("api/Users")
    Call<User> CreateUser(@Body User user);
}