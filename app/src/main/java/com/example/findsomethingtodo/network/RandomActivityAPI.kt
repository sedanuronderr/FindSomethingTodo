package com.example.findsomethingtodo.network

import com.example.findsomethingtodo.model.RandomActivity
import com.example.findsomethingtodo.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RandomActivityAPI {
    @GET(Constants.API_END_POINT)
    fun getRandomActivity():Single<RandomActivity>
}