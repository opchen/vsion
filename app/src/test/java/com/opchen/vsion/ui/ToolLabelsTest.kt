package com.opchen.vsion.ui

import com.opchen.vsion.core.assembly.knownTools
import com.opchen.vsion.i18n.ChineseTexts
import com.opchen.vsion.i18n.ResFileTexts
import com.opchen.vsion.i18n.UiText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ToolLabelsTest {
    @get:Rule val texts = ChineseTexts()

    /** 每个声明了 label 的工具都有界面文字,中文和声明一字不差;英文另有一份。 */
    @Test
    fun everyDeclaredLabelIsLocalized() {
        val tools = knownTools()
        val labels = ToolLabels(tools)
        val declared = tools.names.mapNotNull { n -> tools[n]?.label?.let { n to it } }
        assertTrue(declared.size > 40)
        for ((name, label) in declared) {
            assertTrue("$name: $label", label in ToolLabels.TOOL_LABELS)
            assertEquals(name, label, labels.label(name))
        }
        UiText.source = ResFileTexts.english
        assertEquals("Tap", labels.label("tap"))
        assertEquals("Open web page", labels.label("browser_navigate"))
    }
}
