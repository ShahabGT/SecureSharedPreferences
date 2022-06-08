package ir.shahabazimi.mydraft.security

import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object FingerprintPrompt {

    fun show(fragment: Fragment): LiveData<Boolean> =
        MutableLiveData<Boolean>().also {
            BiometricPrompt(
                fragment,
                ContextCompat.getMainExecutor(fragment.requireContext()),
                AuthenticationCallback(it)
            ).authenticate(
                PromptInfo.Builder()
                    .setTitle("Authenticate")
                    .setNegativeButtonText("Close")
                    .setAllowedAuthenticators(General.AUTHENTICATORS)
                    .build()
            )
        }

    private class AuthenticationCallback(
        private val out: MutableLiveData<Boolean>
    ) : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            out.postValue(true)
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            out.postValue(false)
        }
    }
}