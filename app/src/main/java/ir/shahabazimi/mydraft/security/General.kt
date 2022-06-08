package ir.shahabazimi.mydraft.security

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager

object General {
    const val AUTHENTICATORS = BiometricManager.Authenticators.BIOMETRIC_STRONG

    fun canAuthenticate(ctx: Context) =
        BiometricManager.from(ctx.applicationContext)
            .canAuthenticate(AUTHENTICATORS) == BiometricManager.BIOMETRIC_SUCCESS &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}