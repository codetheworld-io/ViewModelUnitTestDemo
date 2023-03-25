package io.codetheworld.viewmodelunittestdemo

import io.codetheworld.viewmodelunittestdemo.helpers.MainDispatcherRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val userRepository: UserRepository = mockk()
    private val sessionManager: SessionManager = mockk()
    private lateinit var viewModel: LoginViewModel
    private val username = "username"
    private val password = "password"
    private lateinit var viewStates: MutableList<LoginViewModel.ViewState>

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(userRepository, sessionManager)
        viewStates = mutableListOf()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }
    }

    @Test
    fun `should emit error object when api response error`() {
        val message = "Error from api"
        coEvery {
            userRepository.login(any(), any())
        } throws IllegalAccessException(message)

        viewModel.login(username, password)

        coVerify {
            userRepository.login(username, password)
        }
        Assert.assertEquals(LoginViewModel.ViewState.Loading, viewStates[0])
        Assert.assertEquals(LoginViewModel.ViewState.Error(message), viewStates[1])
        verify(exactly = 0) {
            sessionManager.saveAccessToken(any())
        }
    }

    @Test
    fun `should emit content object when api response success`() {
        val loginResponse = UserRepository.LoginResponse("accessToken")
        coEvery {
            userRepository.login(any(), any())
        } returns loginResponse

        viewModel.login(username, password)

        coVerify {
            userRepository.login(username, password)
        }
        Assert.assertEquals(LoginViewModel.ViewState.Loading, viewStates[0])
        Assert.assertEquals(LoginViewModel.ViewState.Content(loginResponse), viewStates[1])
        verify {
            sessionManager.saveAccessToken(loginResponse.accessToken)
        }
    }
}