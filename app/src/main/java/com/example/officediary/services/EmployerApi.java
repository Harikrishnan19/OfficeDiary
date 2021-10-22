package com.example.officediary.services;

import com.example.officediary.model.Employer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployerApi {

    @GET("/users")
    Call<List<Employer>> fetchEmployers();
}
