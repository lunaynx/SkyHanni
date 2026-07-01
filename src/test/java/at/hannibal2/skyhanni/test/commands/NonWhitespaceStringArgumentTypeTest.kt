package at.hannibal2.skyhanni.test.commands

import at.hannibal2.skyhanni.config.commands.brigadier.arguments.NonWhitespaceStringArgumentType
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class NonWhitespaceStringArgumentTypeTest {

    @Test
    fun `parses namespaced token`() {
        val reader = StringReader("skyhanni:centurytimer.active 1 50")

        assertEquals("skyhanni:centurytimer.active", NonWhitespaceStringArgumentType.parse(reader))
        assertEquals(" 1 50", reader.remaining)
    }

    @Test
    fun `rejects empty input`() {
        assertThrows(CommandSyntaxException::class.java) {
            NonWhitespaceStringArgumentType.parse(StringReader(""))
        }
    }
}
