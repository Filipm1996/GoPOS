package com.example.gopos.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gopos.common.Resource
import com.example.gopos.data.Retrofit.entities.ApiResponse.Data
import com.example.gopos.data.db.Item
import com.example.gopos.data.repositories.RepositoryDefault
import com.example.gopos.utils.SharedPreferencesManagerDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GoPOSViewModel @Inject constructor(
    private val itemBox: Box<Item>,
    private val repository: RepositoryDefault,
    private val sharedPreferencesManager: SharedPreferencesManagerDefault
) : ViewModel() {
    private val mutableItemLiveData = MutableLiveData<List<Item>>()
    private val errorCollector = MutableLiveData<String?>()

    init {
        clearDb()
    }

    private fun clearDb() {
        itemBox.removeAll()
    }

    fun getTokenFromServer(login: String, password : String) {
        sharedPreferencesManager.removeAll()
        runBlocking {
            errorCollector.postValue(null)
            val response = repository.getTokenFromServer(login, password)
            when (response) {
                is Resource.Success -> {
                    sharedPreferencesManager.saveRefreshToken(response.data!!.refresh_token!!)
                    sharedPreferencesManager.saveAuthToken(response.data.access_token!!)
                }

                is Resource.Error -> errorCollector.postValue(
                    response.message ?: "An unexpected error occured"
                )
            }
        }
    }


    fun getItemsFromAPIandSave(){
        CoroutineScope(Dispatchers.IO).launch {
            val token = sharedPreferencesManager.getAuthToken() ?: ""
            repository.getItemsFromAPI(token).let {
                when(it){
                    is Resource.Success ->{
                        val list = it.data?.data
                        list?.forEach{ element ->
                            val itemToAdd = getInfo(element)
                            itemBox.put(itemToAdd)
                        }
                        updateMutableItemLiveData(itemBox.all)
                    }
                    is Resource.Error -> {
                        errorCollector.postValue(
                            it.message ?: "An unexpected error occured"
                        )
                    }
                }
            }
        }
    }

    private fun updateMutableItemLiveData(list : List<Item>) {
        mutableItemLiveData.postValue(list)
    }

    private fun getInfo(it: Data) : Item{
        val imageLink : String
        val name : String = it.name
        val rate : String = it.tax.name
        val category : String = it.category.name
        val price= "${it.price.amount.toInt()} ${it.price.currency}"
        if(it.image_link == null){
            imageLink = ""
        }else{
            imageLink = it.image_link.small
        }
        return Item(0L,name,price,rate,category, imageLink)
    }

    fun getItemsFromDb() : LiveData<List<Item>>{
        return mutableItemLiveData
    }
    fun getError() : LiveData<String?> {
        return errorCollector
    }
}