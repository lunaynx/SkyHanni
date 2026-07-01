package at.hannibal2.skyhanni.config.commands.brigadier.arguments

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType

object NonWhitespaceStringArgumentType : ArgumentType<String> {

    private val emptyValueException = SimpleCommandExceptionType(LiteralMessage("Expected string"))

    override fun parse(reader: StringReader): String {
        val start = reader.cursor
        while (reader.canRead() && !reader.peek().isWhitespace()) {
            reader.skip()
        }

        val input = reader.string.substring(start, reader.cursor)
        if (input.isEmpty()) throw emptyValueException.createWithContext(reader)
        return input
    }

    override fun getExamples(): Collection<String> = listOf("minecraft:block.note_block.pling")
}
