package com.example.compitition

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream

@SpringBootTest
internal class ArgsParserServiceTest {

    private lateinit var argsParserService: ArgsParserService

    @BeforeEach
    fun setUp() {
        argsParserService = ArgsParserService()
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    fun toMatches(args: Array<String>) {
        assertEquals(
            mutableListOf(
                Match(Score("Lions", 3), Score("Snakes", 3)),
                Match(Score("Tarantulas", 1), Score("FC Awesome", 0)),
                Match(Score("Lions", 1), Score("FC Awesome", 1)),
                Match(Score("Tarantulas", 3), Score("Snakes", 1)),
                Match(Score("Lions", 4), Score("Grouches", 0)),
            ),
            argsParserService.toMatches(args)
        )
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    fun toUniqueTeams(args: Array<String>) {
        assertEquals(
            setOf(
                Team("Lions"),
                Team("Snakes"),
                Team("Tarantulas"),
                Team("FC Awesome"),
                Team("Grouches")
            ),
            argsParserService.toUniqueTeams(args)
        )
    }

    companion object {
        @JvmStatic
        fun getArgs(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayOf(
                        "Lions", "3", "Snakes", "3",
                        "Tarantulas", "1", "FC", "Awesome", "0",
                        "Lions", "1", "FC", "Awesome", "1",
                        "Tarantulas", "3", "Snakes", "1",
                        "Lions", "4", "Grouches", "0"
                    ),
                ),
                Arguments.of(
                    arrayOf(
                        "Lions 3, Snakes 3",
                        "Tarantulas 1, FC Awesome 0",
                        "Lions 1, FC Awesome 1",
                        "Tarantulas 3, Snakes 1",
                        "Lions 4, Grouches 0",
                    ),
                ),
            )
        }
    }
}