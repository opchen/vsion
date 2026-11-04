package com.opchen.vsion.core.shell

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AccessibilityListTest {
    private val vsion = PermissionCommands.accessibilityService("com.opchen.vsion")
    private val vsionLong = "com.opchen.vsion/com.opchen.vsion.a11y.VsionAccessibilityService"
    private val other = "com.example.reader/.ReaderService"
    private val other2 = "org.sample.keys/org.sample.keys.KeyService"

    @Test
    fun emptyOrUnsetValues() {
        listOf(null, "", "   ", "null", "null\n", ":", " : ").forEach { raw ->
            assertEquals("raw=$raw", emptyList<String>(), AccessibilityList.entries(raw))
            assertFalse(AccessibilityList.contains(raw, vsion))
            assertEquals(vsion, AccessibilityList.with(raw, vsion))
            assertEquals("", AccessibilityList.without(raw, vsion))
        }
    }

    @Test
    fun appendKeepsOtherServicesInOrder() {
        assertEquals("$other:$vsion", AccessibilityList.with(other, vsion))
        assertEquals("$other:$other2:$vsion", AccessibilityList.with("$other:$other2\n", vsion))
    }

    @Test
    fun alreadyPresentIsNotDuplicated() {
        assertEquals("$other:$vsion", AccessibilityList.with("$other:$vsion", vsion))
        // 系统里存的是完整类名写法,也算已经有了
        assertEquals("$vsionLong:$other", AccessibilityList.with("$vsionLong:$other", vsion))
        assertTrue(AccessibilityList.contains(vsionLong, vsion))
    }

    @Test
    fun removeKeepsOthersAndHandlesBothSpellings() {
        assertEquals("$other:$other2", AccessibilityList.without("$other:$vsion:$other2", vsion))
        assertEquals(other, AccessibilityList.without("$vsionLong:$other", vsion))
        assertEquals(other, AccessibilityList.without(other, vsion))
        assertEquals("", AccessibilityList.without(vsion, vsion))
    }

    @Test
    fun strayColonsAndSpacesAreCleanedUp() {
        assertEquals("$other:$other2:$vsion", AccessibilityList.with(":$other:: $other2 :", vsion))
        assertEquals("$other:$other2", AccessibilityList.without("$other::$vsion:$other2:", vsion))
    }

    @Test
    fun differentServiceOfSamePackageIsNotConfused() {
        val sibling = "com.opchen.vsion/.a11y.OtherService"
        assertFalse(AccessibilityList.contains(sibling, vsion))
        assertEquals(sibling, AccessibilityList.without("$sibling:$vsion", vsion))
    }
}
