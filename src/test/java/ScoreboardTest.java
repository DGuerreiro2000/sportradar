import com.sportradar.Match;
import com.sportradar.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setup() {
        scoreboard = new Scoreboard();
    }

    @Test
    void shouldStartAGameWithInitialScore() {
        //Arrange
        scoreboard.startGame("Mexico", "Canada");

        //Act
        List<Match> liveMatches = scoreboard.getSummary();

        //Assert
        assertEquals(1, liveMatches.size());
        Match mexicoCanada = liveMatches.getFirst();
        assertEquals("Mexico", mexicoCanada.homeTeam());
        assertEquals("Canada", mexicoCanada.awayTeam());
        assertEquals(0, mexicoCanada.homeScore());
        assertEquals(0, mexicoCanada.awayScore());
    }
}
