package com.example.compitition

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TournamentService(
    @Value("\${points.win}") private val pointsForWin: Int,
    @Value("\${points.draw}") private val pointsForDraw: Int,
) {

    fun calculateStandings(tournament: Tournament): List<String> {

        val standings = mutableMapOf(*tournament.teams.map { Pair(it.teamName, 0) }.toTypedArray())

        tournament.matches.forEach { match ->

            when (getMatchResult(match)) {
                MatchResult.TEAM_1 -> {
                    match.Team1.let { score ->
                        standings[score.teamName] = standings.getValue(score.teamName).plus(pointsForWin)
                    }
                }
                MatchResult.TEAM_2 -> {
                    match.Team2.let { score ->
                        standings[score.teamName] = standings.getValue(score.teamName).plus(pointsForWin)
                    }
                }
                MatchResult.DRAW -> {
                    match.Team1.let { score ->
                        standings[score.teamName] = standings.getValue(score.teamName).plus(pointsForDraw)
                    }
                    match.Team2.let { score ->
                        standings[score.teamName] = standings.getValue(score.teamName).plus(pointsForDraw)
                    }
                }
            }
        }

        val formattedStandingsResult = formatStandingsResult(standings)

        formattedStandingsResult.forEach { println(it) }

        return formattedStandingsResult
    }

    private fun formatStandingsResult(standings: Map<String,Int>): List<String> {
        return standings.entries.sortedByDescending { it.value }.mapIndexed { index, (team, points) ->
            "${index + 1}. $team, $points pts"
        }
    }

    private fun getMatchResult(match: Match): MatchResult {
        return if (match.Team1.points > match.Team2.points) MatchResult.TEAM_1
        else if (match.Team1.points < match.Team2.points) MatchResult.TEAM_2
        else MatchResult.DRAW
    }

}

enum class MatchResult {
    TEAM_1,
    TEAM_2,
    DRAW
}

private val logger = KotlinLogging.logger {}