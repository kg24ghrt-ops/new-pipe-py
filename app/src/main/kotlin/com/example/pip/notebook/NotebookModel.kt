package com.example.pip.notebook

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotebookPageMetrics(
    val pageWidth: Float,
    val pageHeight: Float,
    val lineSpacing: Float,
    val marginLeft: Float,
    val marginTop: Float,
    val marginBottom: Float,
    val marginRight: Float,
    val firstLineBaseline: Float,
) {
    val contentWidth: Float
        get() = pageWidth - marginLeft - marginRight

    val contentHeight: Float
        get() = pageHeight - marginTop - marginBottom

    val lineCount: Int
        get() = maxOf(1, ((contentHeight - (firstLineBaseline - marginTop)) / lineSpacing).toInt() + 1)

    fun lineY(lineIndex: Int): Float {
        return marginTop + firstLineBaseline + lineIndex * lineSpacing
    }

    fun isLineVisible(lineIndex: Int, visibleTop: Float, visibleBottom: Float): Boolean {
        val y = lineY(lineIndex)
        return y >= visibleTop && y <= visibleBottom
    }

    fun visibleLineRange(visibleTop: Float, visibleBottom: Float): IntRange {
        val first = maxOf(0, ((visibleTop - marginTop - firstLineBaseline) / lineSpacing).floor().toInt())
        val last = minOf(lineCount - 1, ((visibleBottom - marginTop - firstLineBaseline) / lineSpacing).ceil().toInt())
        return first..last
    }
}

object NotebookPageDefaults {
    private const val BASE_LINE_SPACING_DP = 32f
    private const val BASE_MARGIN_LEFT_DP = 56f
    private const val BASE_MARGIN_TOP_DP = 48f
    private const val BASE_MARGIN_BOTTOM_DP = 48f
    private const val BASE_MARGIN_RIGHT_DP = 24f
    private const val BASE_FIRST_LINE_BASELINE_DP = 32f
    private const val BASE_PAGE_WIDTH_DP = 720f
    private const val BASE_PAGE_HEIGHT_DP = 1024f

    fun calculateMetrics(density: Float): NotebookPageMetrics {
        return NotebookPageMetrics(
            pageWidth = BASE_PAGE_WIDTH_DP * density,
            pageHeight = BASE_PAGE_HEIGHT_DP * density,
            lineSpacing = BASE_LINE_SPACING_DP * density,
            marginLeft = BASE_MARGIN_LEFT_DP * density,
            marginTop = BASE_MARGIN_TOP_DP * density,
            marginBottom = BASE_MARGIN_BOTTOM_DP * density,
            marginRight = BASE_MARGIN_RIGHT_DP * density,
            firstLineBaseline = BASE_FIRST_LINE_BASELINE_DP * density,
        )
    }

    fun calculateMetricsForSize(
        availableWidth: Float,
        availableHeight: Float,
        density: Float
    ): NotebookPageMetrics {
        val scale = minOf(availableWidth / BASE_PAGE_WIDTH_DP, availableHeight / BASE_PAGE_HEIGHT_DP) * density
        return NotebookPageMetrics(
            pageWidth = BASE_PAGE_WIDTH_DP * scale,
            pageHeight = BASE_PAGE_HEIGHT_DP * scale,
            lineSpacing = BASE_LINE_SPACING_DP * scale,
            marginLeft = BASE_MARGIN_LEFT_DP * scale,
            marginTop = BASE_MARGIN_TOP_DP * scale,
            marginBottom = BASE_MARGIN_BOTTOM_DP * scale,
            marginRight = BASE_MARGIN_RIGHT_DP * scale,
            firstLineBaseline = BASE_FIRST_LINE_BASELINE_DP * scale,
        )
    }
}

data class NotebookTheme(
    val paperColor: Long,
    val lineColor: Long,
    val marginColor: Long,
    val lineStrokeWidth: Float = 1f,
    val marginStrokeWidth: Float = 1.5f,
)

object NotebookTheme {
    val Light = NotebookTheme(
        paperColor = 0xFFFDFBF7.toLong(),
        lineColor = 0xFF8FA3BF.toLong(),
        marginColor = 0xFFD06B6B.toLong(),
    )

    val Dark = NotebookTheme(
        paperColor = 0xFF1E1E24.toLong(),
        lineColor = 0xFF4A5568.toLong(),
        marginColor = 0xFFB05050.toLong(),
    )
}

data class NotebookPage(
    val lines: List<NotebookLine> = emptyList(),
    val marginNotes: List<MarginNote> = emptyList(),
) {
    fun addLine(line: NotebookLine): NotebookPage {
        return copy(lines = lines + line)
    }

    fun addMarginNote(note: MarginNote): NotebookPage {
        return copy(marginNotes = marginNotes + note)
    }
}

data class NotebookLine(
    val index: Int,
    val text: String = "",
    val strokeColor: Long = 0xFF000000.toLong(),
    val strokeWidth: Float = 2f,
)

data class MarginNote(
    val lineIndex: Int,
    val text: String,
    val color: Long = 0xFFD06B6B.toLong(),
)

enum class PaperOrientation {
    PORTRAIT,
    LANDSCAPE,
}