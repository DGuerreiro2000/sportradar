package com.sportradar;

public record Match (
        String homeTeam,
        String awayTeam,
        int homeScore,
        int awayScore
) {}
