package com.sportradar;

public record Match (
        String homeTeam,
        String awayTeam,
        int homeScore,
        int awayScore
) {
    public static Match create(String homeTeam, String awayTeam) {
        return new Match(homeTeam, awayTeam, 0, 0);
    }
}
