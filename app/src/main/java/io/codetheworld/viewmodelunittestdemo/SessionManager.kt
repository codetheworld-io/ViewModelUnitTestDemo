package io.codetheworld.viewmodelunittestdemo

import android.util.Log

class SessionManager {
    fun saveAccessToken(accessToken: String) {
        Log.d(TAG, "saveAccessToken: $accessToken")
    }

    companion object {
        private const val TAG = "SessionManager"
    }
}