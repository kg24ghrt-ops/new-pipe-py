package com.example.pip.notebook

import org.junit.Assert.*
import org.junit.Test

class NotebookModelTest {

    private val density = 1.0f
    private val metrics = NotebookPageDefaults.calculateMetrics(density)

    @Test
    fun pageMetrics_calculatesCorrectDimensions() {
        assertEquals(720f, metrics.pageWidth, 0.001f)
        assertEquals(1024f, metrics.pageHeight, 0.001f)
        assertEquals(32f, metrics.lineSpacing, 0.001f)
        assertEquals(56f, metrics.marginLeft, 0.001f)
        assertEquals(48f, metrics.marginTop, 0.001f)
        assertEquals(48f, metrics.marginBottom, 0.001f)
        assertEquals(24f, metrics.marginRight, 0.001f)
        assertEquals(32f, metrics.firstLineBaseline, 0.001f)
    }

    @Test
    fun pageMetrics_calculatesContentWidthAndHeight() {
        assertEquals(720f - 56f - 24f, metrics.contentWidth, 0.001f)
        assertEquals(1024f - 48f - 48f, metrics.contentHeight, 0.001f)
    }

    @Test
    fun pageMetrics_calculatesLineCount() {
        val expectedLines = ((metrics.contentHeight - (metrics.firstLineBaseline - metrics.marginTop)) / metrics.lineSpacing).toInt() + 1
        assertEquals(expectedLines, metrics.lineCount)
        assertTrue(metrics.lineCount > 0)
    }

    @Test
    fun pageMetrics_calculatesLineYPositions() {
        val firstLineY = metrics.lineY(0)
        val secondLineY = metrics.lineY(1)
        val thirdLineY = metrics.lineY(2)

        assertEquals(metrics.marginTop + metrics.firstLineBaseline, firstLineY, 0.001f)
        assertEquals(firstLineY + metrics.lineSpacing, secondLineY, 0.001f)
        assertEquals(secondLineY + metrics.lineSpacing, thirdLineY, 0.001f)
    }

    @Test
    fun pageMetrics_visibleLineRange() {
        val visibleTop = metrics.marginTop
        val visibleBottom = metrics.marginTop + metrics.lineSpacing * 5
        val range = metrics.visibleLineRange(visibleTop, visibleBottom)

        assertEquals(0, range.first)
        assertTrue(range.last >= 4)
    }

    @Test
    fun pageMetrics_isLineVisible() {
        val firstLineY = metrics.lineY(0)
        val lastLineY = metrics.lineY(metrics.lineCount - 1)

        assertTrue(metrics.isLineVisible(0, metrics.marginTop, metrics.pageHeight - metrics.marginBottom))
        assertTrue(metrics.isLineVisible(metrics.lineCount - 1, metrics.marginTop, metrics.pageHeight - metrics.marginBottom))
    }

    @Test
    fun pageMetrics_scalesWithDensity() {
        val highDensityMetrics = NotebookPageDefaults.calculateMetrics(2.0f)

        assertEquals(metrics.pageWidth * 2, highDensityMetrics.pageWidth, 0.001f)
        assertEquals(metrics.pageHeight * 2, highDensityMetrics.pageHeight, 0.001f)
        assertEquals(metrics.lineSpacing * 2, highDensityMetrics.lineSpacing, 0.001f)
        assertEquals(metrics.marginLeft * 2, highDensityMetrics.marginLeft, 0.001f)
    }

    @Test
    fun pageMetrics_calculatesForSize() {
        val availableWidth = 500f
        val availableHeight = 800f
        val scaledMetrics = NotebookPageDefaults.calculateMetricsForSize(availableWidth, availableHeight, 1.0f)

        assertTrue(scaledMetrics.pageWidth <= availableWidth)
        assertTrue(scaledMetrics.pageHeight <= availableHeight)
    }

    @Test
    fun notebookPage_addLine() {
        val page = NotebookPage()
        val line = NotebookLine(index = 0, text = "Test line")
        val newPage = page.addLine(line)

        assertEquals(1, newPage.lines.size)
        assertEquals("Test line", newPage.lines[0].text)
        assertEquals(0, newPage.lines[0].index)
    }

    @Test
    fun notebookPage_addMarginNote() {
        val page = NotebookPage()
        val note = MarginNote(lineIndex = 2, text = "Important!")
        val newPage = page.addMarginNote(note)

        assertEquals(1, newPage.marginNotes.size)
        assertEquals("Important!", newPage.marginNotes[0].text)
        assertEquals(2, newPage.marginNotes[0].lineIndex)
    }

    @Test
    fun notebookPage_emptyPage() {
        val page = NotebookPage()
        assertTrue(page.lines.isEmpty())
        assertTrue(page.marginNotes.isEmpty())
    }

    @Test
    fun notebookTheme_lightTheme() {
        val theme = NotebookTheme.Light
        assertEquals(0xFFFDFBF7.toLong(), theme.paperColor)
        assertEquals(0xFF8FA3BF.toLong(), theme.lineColor)
        assertEquals(0xFFD06B6B.toLong(), theme.marginColor)
    }

    @Test
    fun notebookTheme_darkTheme() {
        val theme = NotebookTheme.Dark
        assertEquals(0xFF1E1E24.toLong(), theme.paperColor)
        assertEquals(0xFF4A5568.toLong(), theme.lineColor)
        assertEquals(0xFFB05050.toLong(), theme.marginColor)
    }
}