package com.example.compitition

import org.springframework.stereotype.Service

@Service
class ArgsParserService {

    fun toMatches(args: Array<String>): MutableList<Match> {
        return args.toScores().chunked(2) { (score1, score2) -> Match(score1, score2) }.toMutableList()
    }

    fun toUniqueTeams(args: Array<String>): MutableSet<Team> {
        return args.toScores().map { score -> Team(score.teamName) }.toMutableSet()
    }

    private fun Array<String>.toScores(): MutableList<Score> {
        val scores = mutableListOf<Score>()
        var teamName = ""
        this.toStandardizedString().forEach { arg ->
            if (arg.all { it.isLetter() }) {
                teamName += " $arg"
            } else {
                scores.add(Score(teamName.trim(), arg.toInt()))
                teamName = ""
            }
        }
        return scores
    }

    private fun Array<String>.toStandardizedString(): List<String> {
        return this.joinToString(" ").replace(",", "").split(" ")
    }

}