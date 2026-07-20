package at.hannibal2.skyhanni.test

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
import org.spongepowered.asm.mixin.MixinEnvironment

// Fabric's client game-test API is experimental, but it is the supported API for this test environment.
@Suppress("UnstableApiUsage")
object MixinAuditGameTest : FabricClientGameTest {

    override fun runTest(context: ClientGameTestContext) {
        context.worldBuilder().create().use {
            MixinEnvironment.getCurrentEnvironment().audit()
        }
    }
}
