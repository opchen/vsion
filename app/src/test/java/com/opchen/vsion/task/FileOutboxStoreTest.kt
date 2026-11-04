package com.opchen.vsion.task

import com.opchen.vsion.core.run.ChannelPeer
import com.opchen.vsion.core.run.Delivery
import com.opchen.vsion.core.run.DeliveryRecord
import com.opchen.vsion.core.run.DeliveryState
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Files

class FileOutboxStoreTest {
    @Test fun attemptingDeliverySurvivesRestartWithoutLosingItsIdentity() {
        val dir = Files.createTempDirectory("vsion-outbox").toFile()
        try {
            val file = dir.resolve("outbox.json")
            val peer = ChannelPeer("local", "owner", "thread")
            val delivery = Delivery.Result("result:1", "thread", "回答", title = "任务完成")
            FileOutboxStore(file).save(DeliveryRecord(peer, delivery, DeliveryState.Attempting))
            val restored = FileOutboxStore(file).get("result:1")
            assertEquals(DeliveryRecord(peer, delivery, DeliveryState.Attempting), restored)
        } finally { dir.deleteRecursively() }
    }
}
