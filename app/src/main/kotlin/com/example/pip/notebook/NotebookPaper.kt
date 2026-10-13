package com.example.pip.notebook

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas as GraphicsCanvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawLine
import androidx.compose.ui.graphics.drawscope.drawRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.px
import androidx.compose.ui.unit.sp

@Composable
fun NotebookPaperSurface(
    modifier: Modifier = Modifier,
    theme: NotebookTheme = NotebookTheme.Light,
    metrics: NotebookPageMetrics,
    visibleBounds: Rect = Rect.Zero,
    onPageClick: ((Offset) -> Unit)? = null,
    showPageShadow: Boolean = true,
) {
    val density = LocalDensity.current
    val paint = remember {
        Paint().apply { isAntiAlias = true }
    }
    val marginPaint = remember {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = theme.marginStrokeWidth * density
            color = Color(theme.marginColor)
        }
    }
    val linePaint = remember {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = theme.lineStrokeWidth * density
            color = Color(theme.lineColor)
        }
    }
    val paperPaint = remember {
        Paint().apply {
            isAntiAlias = true
            color = Color(theme.paperColor)
        }
    }
    val shadowPaint = remember {
        Paint().apply {
            isAntiAlias = true
            color = Color(0x44000000.toLong())
        }
    }

    val pageWidthPx = metrics.pageWidth
    val pageHeightPx = metrics.pageHeight

    Canvas(
        modifier = modifier
            .size(pageWidthPx.px, pageHeightPx.px)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onPageClick?.invoke(offset)
                }
            },
    ) {
        val clipRect = Rect(0f, 0f, pageWidthPx, pageHeightPx)
        clipRect(clipRect) {
            if (showPageShadow) {
                drawRect(
                    paint = shadowPaint,
                    topLeft = Offset(2f * density, 2f * density),
                    size = Size(pageWidthPx - 4f * density, pageHeightPx - 4f * density),
                )
            }

            drawRect(
                paint = paperPaint,
                topLeft = Offset.Zero,
                size = Size(pageWidthPx, pageHeightPx),
            )

            val marginX = metrics.marginLeft
            drawLine(
                paint = marginPaint,
                start = Offset(marginX, metrics.marginTop),
                end = Offset(marginX, pageHeightPx - metrics.marginBottom),
            )

            val visibleTop = if (visibleBounds != Rect.Zero) visibleBounds.top else metrics.marginTop
            val visibleBottom = if (visibleBounds != Rect.Zero) visibleBounds.bottom else pageHeightPx - metrics.marginBottom
            val lineRange = metrics.visibleLineRange(visibleTop, visibleBottom)

            for (lineIndex in lineRange) {
                val y = metrics.lineY(lineIndex)
                val startX = marginX + 8f * density
                val endX = pageWidthPx - metrics.marginRight - 8f * density
                drawLine(
                    paint = linePaint,
                    start = Offset(startX, y),
                    end = Offset(endX, y),
                )
            }
        }
    }
}

@Composable
fun NotebookPageView(
    modifier: Modifier = Modifier,
    page: NotebookPage,
    theme: NotebookTheme = NotebookTheme.Light,
    showShadow: Boolean = true,
) {
    val density = LocalDensity.current
    val metrics = remember { NotebookPageDefaults.calculateMetrics(density) }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        color = Color.Transparent,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            NotebookPaperSurface(
                theme = theme,
                metrics = metrics,
                showPageShadow = showShadow,
            )
        }
    }
}

@Composable
fun NotebookPreview(
    modifier: Modifier = Modifier.fillMaxSize(),
    theme: NotebookTheme = NotebookTheme.Light,
    pageCount: Int = 1,
) {
    val density = LocalDensity.current
    val metrics = remember { NotebookPageDefaults.calculateMetrics(density) }

    Box(
        modifier = modifier
            .background(Color(theme.paperColor).copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            repeat(pageCount) {
                NotebookPaperSurface(
                    modifier = Modifier.width(metrics.pageWidth.px),
                    theme = theme,
                    metrics = metrics,
                    showPageShadow = true,
                )
            }
        }
    }
}

@Composable
fun NotebookPaperCanvas(
    modifier: Modifier = Modifier.fillMaxSize(),
    theme: NotebookTheme = NotebookTheme.Light,
    metrics: NotebookPageMetrics,
    onDrawContent: (DrawScope, NotebookPageMetrics) -> Unit = { _, _ -> },
) {
    val density = LocalDensity.current
    val paint = remember { Paint().apply { isAntiAlias = true } }
    val marginPaint = remember {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = theme.marginStrokeWidth * density
            color = Color(theme.marginColor)
        }
    }
    val linePaint = remember {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = theme.lineStrokeWidth * density
            color = Color(theme.lineColor)
        }
    }
    val paperPaint = remember {
        Paint().apply {
            isAntiAlias = true
            color = Color(theme.paperColor)
        }
    }
    val shadowPaint = remember {
        Paint().apply {
            isAntiAlias = true
            color = Color(0x44000000.toLong())
        }
    }

    Canvas(
        modifier = modifier
            .size(metrics.pageWidth.px, metrics.pageHeight.px)
            .background(Color.Transparent),
    ) {
        val pageWidthPx = metrics.pageWidth
        val pageHeightPx = metrics.pageHeight
        val clipRect = Rect(0f, 0f, pageWidthPx, pageHeightPx)

        clipRect(clipRect) {
            drawRect(
                paint = shadowPaint,
                topLeft = Offset(2f * density, 2f * density),
                size = Size(pageWidthPx - 4f * density, pageHeightPx - 4f * density),
            )

            drawRect(
                paint = paperPaint,
                topLeft = Offset.Zero,
                size = Size(pageWidthPx, pageHeightPx),
            )

            val marginX = metrics.marginLeft
            drawLine(
                paint = marginPaint,
                start = Offset(marginX, metrics.marginTop),
                end = Offset(marginX, pageHeightPx - metrics.marginBottom),
            )

            val lineCount = metrics.lineCount
            for (lineIndex in 0 until lineCount) {
                val y = metrics.lineY(lineIndex)
                val startX = marginX + 8f * density
                val endX = pageWidthPx - metrics.marginRight - 8f * density
                drawLine(
                    paint = linePaint,
                    start = Offset(startX, y),
                    end = Offset(endX, y),
                )
            }

            onDrawContent(this, metrics)
        }
    }
}