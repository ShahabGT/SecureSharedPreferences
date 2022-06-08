package ir.shahabazimi.mydraft

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.shahabazimi.mydraft.security.MySp

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun checkEncryptDecrypt() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val target = "Test Sentence"
        val sp = MySp(appContext)
        sp.save(target)
        assertEquals(sp.load(), target)
    }
}