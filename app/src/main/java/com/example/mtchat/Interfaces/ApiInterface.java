package com.example.mtchat.Interfaces;

import com.example.mtchat.Model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("messages")
    Call<List<Message>> getMessages();

    @POST("messages/save")
    Call<List<Message>> saveMessages(@Body Message message);

}
