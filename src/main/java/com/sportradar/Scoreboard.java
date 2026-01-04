package com.sportradar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scoreboard {

    private final Map<String, Match> liveMatches = new HashMap<>();

    public void startGame (String home, String away) {
        Match match = new Match(home, away, 0, 0);
        liveMatches.put(home+away, match);
    }

    public List<Match> getSummary () {
        return liveMatches.values().stream().toList();
    }
}
