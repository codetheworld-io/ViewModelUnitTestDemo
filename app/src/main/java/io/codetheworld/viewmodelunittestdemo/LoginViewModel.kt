package io.codetheworld.viewmodelunittestdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _viewState.postValue(ViewState.Loading)

            try {
                val result = userRepository.login(username, password)
                _viewState.postValue(ViewState.Content(result))
                sessionManager.saveAccessToken(result.accessToken)
            } catch (exception: Exception) {
                _viewState.postValue(ViewState.Error(exception.message!!))
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val message: String) : ViewState()
        data class Content(val loginResponse: UserRepository.LoginResponse) : ViewState()
    }
}