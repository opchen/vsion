package com.opchen.vsion.cap.sandbox

import com.opchen.vsion.core.workspace.NioFdSys
import com.opchen.vsion.core.workspace.WorkspaceFileGate

/** JVM 测试里的关口(按路径的近似;手机上是 AndroidFdSys.gate)。 */
internal val JVM_GATE = WorkspaceFileGate(NioFdSys)
