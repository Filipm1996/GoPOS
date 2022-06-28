package com.example.gopos.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gopos.FakeSharedPreferencesManager
import com.example.gopos.common.Resource
import com.example.gopos.data.Retrofit.entities.ApiResponse.ApiResponse
import com.example.gopos.data.Retrofit.entities.TokenResponse
import com.example.gopos.data.db.Item
import com.example.gopos.data.repositories.RepositoryDefault
import com.example.gopos.getOrAwaitValue
import com.example.gopos.utils.SharedPreferencesManagerDefault
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.objectbox.Box
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelUnitTests {
    private lateinit var sharedPreferencesManager : SharedPreferencesManagerDefault
    private lateinit var viewModel : GoPOSViewModel
    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: RepositoryDefault
    @MockK
    lateinit var itemBox: Box<Item>


    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        sharedPreferencesManager = FakeSharedPreferencesManager()
        viewModel = GoPOSViewModel(itemBox,repository,sharedPreferencesManager)
        every { itemBox.removeAll() } returns mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `get error from server save error in errorCollector`()= runBlocking{
        val errorResponse = Resource.Error("error",TokenResponse(null,null,null,null,null))
        coEvery { repository.getTokenFromServer(any(),any()) } returns errorResponse
        viewModel.getTokenFromServer("login", "password")
        val error = viewModel.getError().getOrAwaitValue()
        assertThat(error == "error").isTrue()
    }

    @Test
    fun `get success from server save tokens`() = runBlocking {
        val tokenResponse = TokenResponse("access_token",1,"refresh_token","scope","token_type")
        val successResponse = Resource.Success(tokenResponse)
        coEvery { repository.getTokenFromServer(any(),any()) } returns successResponse
        viewModel.getTokenFromServer("login", "password")
        val error = viewModel.getError().getOrAwaitValue()
        assertThat(error == null).isTrue()
        assertThat(sharedPreferencesManager.getAuthToken() == "access_token" ).isTrue()
        assertThat(sharedPreferencesManager.getRefreshToken() == "refresh_token" ).isTrue()
    }

    @Test
    fun `getItemsFromAPI return error response and errorCollector contains error` () = runBlocking {
        val errorResponse = Resource.Error("error",ApiResponse(null))
        coEvery { repository.getItemsFromAPI(any()) } returns  errorResponse
        viewModel.getItemsFromAPIandSave()
        val error = viewModel.getError().getOrAwaitValue()
        assertThat(error == "error").isTrue()
    }


}