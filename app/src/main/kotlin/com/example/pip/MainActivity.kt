package com.example.pip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.pip.notebook.NotebookScreen
import com.example.pip.notebook.NotebookTheme
import com.example.pip.ui.theme.PipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PipTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Surface(
        modifier = androidx.compose.foundation.layout.Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        NotebookScreen(theme = NotebookTheme.Light)
    }
}