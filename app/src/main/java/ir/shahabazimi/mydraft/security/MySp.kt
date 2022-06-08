package ir.shahabazimi.mydraft.security

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

@RequiresApi(Build.VERSION_CODES.M)
class MySp private constructor(ctx: Context) {

    private val sharedPrefsFile: String = "SHAHABGT"

    private val sp =
        EncryptedSharedPreferences.create(
            ctx.applicationContext,
            sharedPrefsFile,
            MasterKey.Builder(ctx.applicationContext)
                .setUserAuthenticationRequired(true, 15)
                .setKeyGenParameterSpec(
                    KeyGenParameterSpec.Builder(
                        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(256)
                        .build()
                ).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )


    companion object {
        @Volatile
        private var instance: MySp? = null
        operator fun invoke(ctx: Context): MySp =
            instance ?: synchronized(this) {
                instance ?: MySp(ctx).also { instance = it }
            }
    }

    fun clear() = sp.edit { clear() }

    fun save(value: String) =
        with(sp.edit()) {
            putString("fingerprint", value)
            apply()
        }

    fun load() = sp.getString("fingerprint", "")

}