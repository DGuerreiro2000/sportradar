package com.sportradar;

public record Match (
        String homeTeam,
        String awayTeam,
        int homeScore,
        int awayScore,
        long counter
) implements Comparable<Match> {

    public static Match create(String homeTeam, String awayTeam, long counter) {
        return new Match(homeTeam, awayTeam, 0, 0, counter);
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public int compareTo(Match o) {
        int compareScore = Integer.compare(o.getTotalScore(), this.getTotalScore());
        if (compareScore != 0) {
            return compareScore;
        }
        return Long.compare(o.counter(), this.counter);
    }
}
