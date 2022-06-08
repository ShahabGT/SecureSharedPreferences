package ir.shahabazimi.mydraft.security

import android.content.Context
import androidx.biometric.BiometricManager

object General {

    const val AUTHENTICATORS = BiometricManager.Authenticators.BIOMETRIC_STRONG

    fun canAuthenticate(c: Context) =
        BiometricManager.from(c)
            .canAuthenticate(AUTHENTICATORS) == BiometricManager.BIOMETRIC_SUCCESS
}