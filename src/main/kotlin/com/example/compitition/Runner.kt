package com.example.compitition

import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Runner(
    private val argsParserService: ArgsParserService,
    private val tournamentService: TournamentService,
): CommandLineRunner {
    override fun run(args: Array<String>) {

        val matches = argsParserService.toMatches(args)

        val teams = argsParserService.toUniqueTeams(args)

        val tournament = Tournament(
            teams,
            matches
        )

        tournamentService.calculateStandings(tournament)
    }
}

fun Array<String>.argsToString(): String {
    val argType = when (this[0].split(" ").size) {
        1 -> ArgType.ONE_WORD
        4 -> ArgType.FOUR_WORDS
        else -> error("Please format args as (\"Lions 3, Snakes 3\")")
    }

    return when (argType) {
        ArgType.ONE_WORD -> this.joinToString(separator = " ")
        ArgType.FOUR_WORDS -> this.joinToString(separator = " ")
    }
}

enum class ArgType {
    ONE_WORD,
    FOUR_WORDS
}

private val logger = KotlinLogging.logger {  }