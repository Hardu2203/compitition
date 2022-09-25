package com.example.compitition

data class Tournament(
    val teams: MutableSet<Team>,
    val matches: MutableList<Match>,
)