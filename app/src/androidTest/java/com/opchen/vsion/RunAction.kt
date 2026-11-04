package com.opchen.vsion

import com.opchen.vsion.core.action.ActionExecutor
import com.opchen.vsion.core.action.ActionRecorder
import com.opchen.vsion.core.action.Origin
import com.opchen.vsion.core.action.execution
import com.opchen.vsion.core.guard.OperationGuards
import com.opchen.vsion.core.model.Part
import com.opchen.vsion.core.record.RunSink
import com.opchen.vsion.core.tool.ToolDispatcher
import com.opchen.vsion.core.tool.ToolExecution

/** 设备测试里直接做一个工具调用:和产品一样经执行入口(不配确认关卡,不等锁屏)。 */
suspend fun ToolDispatcher.runAction(call: Part.ToolCall): ToolExecution =
    ActionExecutor(this, OperationGuards(), null, gate = {}, recorder = ActionRecorder(RunSink.NONE)).run(call, Origin.Model(0)).execution()
