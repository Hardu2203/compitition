package com.example.compitition

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest

internal class TournamentServiceTest{

    lateinit var tournament: Tournament
    lateinit var tournamentService: TournamentService

    @BeforeEach
    fun setUp() {
        tournamentService = TournamentService(3,1)
        tournament = Tournament(
            teams = mutableSetOf(
                Team("Lions"),
                Team("Snakes"),
                Team("Tarantulas"),
                Team("FC Awesome"),
                Team("Grouches")
            ),
            matches = mutableListOf(
                Match(Score("Lions", 3), Score("Snakes", 3)),
                Match(Score("Tarantulas", 1), Score("FC Awesome", 0)),
                Match(Score("Lions", 1), Score("FC Awesome", 1)),
                Match(Score("Tarantulas", 3), Score("Snakes", 1)),
                Match(Score("Lions", 4), Score("Grouches", 0)),
            )
        )
    }

    @Test
    fun calculateStandings() {
        assertEquals(
            listOf("1. Tarantulas, 6 pts", "2. Lions, 5 pts", "3. Snakes, 1 pts", "4. FC Awesome, 1 pts", "5. Grouches, 0 pts"),
            tournamentService.calculateStandings(tournament)
        )
    }
}