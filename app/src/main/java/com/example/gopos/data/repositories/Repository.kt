package com.example.gopos.data.repositories


import com.example.gopos.common.Constants
import com.example.gopos.common.Resource
import com.example.gopos.data.Retrofit.api.ApiService
import com.example.gopos.data.Retrofit.entities.ApiResponse.ApiResponse
import com.example.gopos.data.Retrofit.entities.TokenResponse
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService)  : RepositoryDefault {

    override suspend fun getTokenFromServer(login : String, password : String) : Resource<TokenResponse>  {
        return try {
            val tokenResponse : TokenResponse? = CoroutineScope(Dispatchers.IO).async {
                apiService.getToken(
                    password,
                    Constants.clientSecret,
                    Constants.clientId,
                    login
                )
            }.await().body()
            Resource.Success(tokenResponse!!)
        } catch(e: HttpException) {
            Resource.Error(e.message() ?: "Wystąpił błąd")
        } catch(e: IOException) {
            Resource.Error("Error. Sprawdź połączenie z internetem")
        } catch (e : Exception){
            Resource.Error(e.message ?: "Wystąpił błąd. Czy dane są poprawne?")
        }
    }


    override suspend fun getItemsFromAPI(token:String): Resource<ApiResponse>  {
        return try {
            val data : ApiResponse = CoroutineScope(Dispatchers.IO).async {
                apiService.getItems("category,tax")
            }.await()
            (Resource.Success(data))
        } catch(e: HttpException) {
            ( Resource.Error(e.message() ?: "Wystąpił nieoczekiwany błąd."))
        } catch(e: IOException) {
            ( Resource.Error("Error. Sprawdź połączenie z internetem."))
        }catch (e : Exception){
            Resource.Error(e.message ?: "Wystąpił błąd. Czy dane są poprawne?")
        }
    }

}
