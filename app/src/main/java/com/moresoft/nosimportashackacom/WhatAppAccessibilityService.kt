package com.moresoft.nosimportashackacom

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

class WhatAppAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (getRootInActiveWindow()==null){
            return
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}