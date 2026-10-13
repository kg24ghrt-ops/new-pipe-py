package com.example.pip

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val appContext: Context = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("com.example.pip", appContext.packageName)
    }

    @Test
    fun appContext_isNotNull() {
        val appContext: Context = ApplicationProvider.getApplicationContext<Context>()
        assertNotNull(appContext)
    }

    @Test
    fun appContext_resources_notNull() {
        val appContext: Context = ApplicationProvider.getApplicationContext<Context>()
        assertNotNull(appContext.resources)
    }

    @Test
    fun appContext_packageManager_notNull() {
        val appContext: Context = ApplicationProvider.getApplicationContext<Context>()
        assertNotNull(appContext.packageManager)
    }
}