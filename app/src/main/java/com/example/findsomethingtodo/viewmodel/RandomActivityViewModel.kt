package com.example.findsomethingtodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findsomethingtodo.model.RandomActivity
import com.example.findsomethingtodo.network.RandomActivityAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomActivityViewModel:ViewModel() {

    private val apiService = RandomActivityAPIService()
     private val compositeDisposable=CompositeDisposable()
    val loadRandomActivity =MutableLiveData<Boolean>()
    val randomActivityResponse =MutableLiveData<RandomActivity>()
    val randomActivityloadERROR= MutableLiveData<Boolean>()

    fun getRandomActivityFromAPI(){
        loadRandomActivity.value =true
        compositeDisposable.add(
            apiService.getRandomActivity()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object:DisposableSingleObserver<RandomActivity>(){
                        override fun onSuccess(t: RandomActivity) {
                            loadRandomActivity.value =true
                            randomActivityloadERROR.value= false
                            randomActivityResponse.value =t

                        }

                        override fun onError(e: Throwable) {
                            loadRandomActivity.value =false
                            randomActivityloadERROR.value=false
                 e.printStackTrace()
                        }

                    }
                )
        )
    }

}