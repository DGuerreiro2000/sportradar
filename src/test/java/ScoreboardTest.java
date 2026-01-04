import com.sportradar.Match;
import com.sportradar.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setup() {
        scoreboard = new Scoreboard();
    }

    @Nested
    @DisplayName("Tests for starting new games")
    class StartGameTests {

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

        @Test
        void shouldStartDifferentGames() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");
            scoreboard.startGame("Spain", "Brazil");

            //Act
            List<Match> summary = scoreboard.getSummary();

            //Assert
            assertEquals(2, summary.size());
        }

        @Test
        void shouldFailToStartAGameAlreadyInProgress() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            assertThrows(IllegalArgumentException.class, () -> {
                scoreboard.startGame("Mexico", "Canada");
            });

            //Assert
            assertEquals(1, scoreboard.getSummary().size());
        }
    }

    @Nested
    @DisplayName("Tests for finishing games")
    class FinishGameTests {

        @Test
        void shouldFinishExistingGame() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            scoreboard.finishGame("Mexico", "Canada");
            List<Match> summary = scoreboard.getSummary();

            //Assert
            assertEquals(0, summary.size());
        }

        @Test
        void shouldFailToFinishAGameNotInProgress() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            assertThrows(IllegalArgumentException.class, () -> {
                scoreboard.finishGame("Portugal", "Norway");
            });

            //Assert
            assertEquals(1, scoreboard.getSummary().size());
        }
    }

    @Nested
    @DisplayName("Tests for updating the score of a game")
    class UpdateGameTests {

        @Test
        void shouldUpdateAGameInProgressInitialScore() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            scoreboard.updateGame("Mexico", "Canada", 0, 5);
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            Match mexicoCanada = liveMatches.getFirst();
            assertEquals(0, mexicoCanada.homeScore());
            assertEquals(5, mexicoCanada.awayScore());
        }

        @Test
        void shouldUpdateAGameInProgressPreviousUpdate() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            scoreboard.updateGame("Mexico", "Canada", 0, 5);
            scoreboard.updateGame("Mexico", "Canada", 1, 1);
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            Match mexicoCanada = liveMatches.getFirst();
            assertEquals(1, mexicoCanada.homeScore());
            assertEquals(1, mexicoCanada.awayScore());
        }

        @Test
        void shouldFailToSetATeamsScoreAsNegative() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateGame("Mexico", "Canada", 0, -5)
            );
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            Match mexicoCanada = liveMatches.getFirst();
            assertEquals(0, mexicoCanada.homeScore());
            assertEquals(0, mexicoCanada.awayScore());
        }

        @Test
        void shouldFailToUpdateAGameNotInProgress() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");

            //Act
            assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateGame("Portugal", "Norway", 1, 1)
            );
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            assertEquals(1, liveMatches.size());
            Match mexicoCanada = liveMatches.getFirst();
            assertEquals(0, mexicoCanada.homeScore());
            assertEquals(0, mexicoCanada.awayScore());
        }
    }
}
