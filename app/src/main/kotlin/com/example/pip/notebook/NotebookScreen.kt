package com.example.pip.notebook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.px
import androidx.compose.ui.unit.sp

@Composable
fun NotebookScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    theme: NotebookTheme = NotebookTheme.Light,
) {
    val density = LocalDensity.current
    val metrics = remember { NotebookPageDefaults.calculateMetrics(density) }
    val scrollState = rememberScrollState()
    val visibleBounds = remember {
        mutableStateOf(Rect(0f, 0f, metrics.pageWidth, metrics.pageHeight))
    }

    Box(
        modifier = modifier
            .background(Color(theme.paperColor).copy(alpha = 0.05f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            NotebookPaperSurface(
                modifier = Modifier.size(metrics.pageWidth.px, metrics.pageHeight.px),
                theme = theme,
                metrics = metrics,
                visibleBounds = visibleBounds.value,
                showPageShadow = true,
            )
        }
    }
}

@Composable
fun NotebookScreenWithMultiplePages(
    modifier: Modifier = Modifier.fillMaxSize(),
    theme: NotebookTheme = NotebookTheme.Light,
    pageCount: Int = 3,
) {
    val density = LocalDensity.current
    val metrics = remember { NotebookPageDefaults.calculateMetrics(density) }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .background(Color(theme.paperColor).copy(alpha = 0.05f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            repeat(pageCount) {
                NotebookPaperSurface(
                    modifier = Modifier.size(metrics.pageWidth.px, metrics.pageHeight.px),
                    theme = theme,
                    metrics = metrics,
                    showPageShadow = true,
                )
            }
        }
    }
}