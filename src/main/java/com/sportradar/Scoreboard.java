package com.sportradar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scoreboard {

    private final Map<String, Match> liveMatches = new HashMap<>();

    public void startGame (String home, String away) {
        String gameId = home+away;
        if (liveMatches.containsKey(gameId)) {
            throw new IllegalArgumentException("Game in progress");
        }
        liveMatches.put(gameId, Match.create(home, away));
    }

    public List<Match> getSummary () {
        return liveMatches.values().stream().toList();
    }

    public void finishGame (String home, String away) {
        String gameId = home+away;
        if (!liveMatches.containsKey(gameId)) {
            throw new IllegalArgumentException("Game not in progress");
        }
        liveMatches.remove(gameId);
    }

    public void updateGame (String home, String away, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must be positive");
        }
        String gameId = home+away;
        if (!liveMatches.containsKey(gameId)) {
            throw new IllegalArgumentException("Game not in progress");
        }
        liveMatches.put(gameId, new Match(home, away, homeScore, awayScore));
    }
}
