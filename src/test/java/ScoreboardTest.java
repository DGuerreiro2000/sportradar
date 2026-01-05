import com.sportradar.Match;
import com.sportradar.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Nested
    @DisplayName("Tests for getting the full summary of games")
    class SummaryTests {

        @Test
        void shouldReturnGamesOrderedByTotalGoals() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");
            scoreboard.updateGame("Mexico", "Canada", 0, 5);

            scoreboard.startGame("Spain", "Brazil");
            scoreboard.updateGame("Spain", "Brazil", 10, 2);

            //Act
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            Match firstGame = liveMatches.getFirst();
            assertEquals("Spain", firstGame.homeTeam());
            assertEquals("Brazil", firstGame.awayTeam());
            assertEquals(10, firstGame.homeScore());
            assertEquals(2, firstGame.awayScore());

            Match secondGame = liveMatches.get(1);
            assertEquals("Mexico", secondGame.homeTeam());
            assertEquals("Canada", secondGame.awayTeam());
            assertEquals(0, secondGame.homeScore());
            assertEquals(5, secondGame.awayScore());
        }

        @Test
        void shouldReturnMostRecentGameIfTiedForGoals() {
            //Arrange
            scoreboard.startGame("Spain", "Brazil");
            scoreboard.updateGame("Spain", "Brazil", 10, 2);

            scoreboard.startGame("Uruguay", "Italy");
            scoreboard.updateGame("Uruguay", "Italy", 6, 6);

            //Act
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            Match firstGame = liveMatches.getFirst();
            assertEquals("Uruguay", firstGame.homeTeam());
            assertEquals("Italy", firstGame.awayTeam());
            assertEquals(0, firstGame.homeScore());
            assertEquals(6, firstGame.awayScore());

            Match secondGame = liveMatches.get(1);
            assertEquals("Spain", secondGame.homeTeam());
            assertEquals("Brazil", secondGame.awayTeam());
            assertEquals(10, secondGame.homeScore());
            assertEquals(2, secondGame.awayScore());
        }

        @Test
        void shouldReturnEmptyListIfNoLiveGames() {
            //Act
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            assertTrue(liveMatches.isEmpty());
        }

        @Test
        void shouldReturnFullSummary() {
            //Arrange
            scoreboard.startGame("Mexico", "Canada");
            scoreboard.updateGame("Mexico", "Canada", 0, 5);

            scoreboard.startGame("Spain", "Brazil");
            scoreboard.updateGame("Spain", "Brazil", 10, 2);

            scoreboard.startGame("Germany", "France");
            scoreboard.updateGame("Germany", "France", 2, 2);

            scoreboard.startGame("Uruguay", "Italy");
            scoreboard.updateGame("Uruguay", "Italy", 6, 6);

            scoreboard.startGame("Argentina", "Australia");
            scoreboard.updateGame("Argentina", "Australia", 3, 1);

            //Act
            List<Match> liveMatches = scoreboard.getSummary();

            //Assert
            Match firstGame = liveMatches.getFirst();
            assertEquals("Uruguay", firstGame.homeTeam());
            assertEquals("Italy", firstGame.awayTeam());
            assertEquals(0, firstGame.homeScore());
            assertEquals(6, firstGame.awayScore());

            Match secondGame = liveMatches.get(1);
            assertEquals("Spain", secondGame.homeTeam());
            assertEquals("Brazil", secondGame.awayTeam());
            assertEquals(10, secondGame.homeScore());
            assertEquals(2, secondGame.awayScore());

            Match thirdGame = liveMatches.get(2);
            assertEquals("Mexico", thirdGame.homeTeam());
            assertEquals("Canada", thirdGame.awayTeam());
            assertEquals(0, thirdGame.homeScore());
            assertEquals(5, thirdGame.awayScore());

            Match fourthGame = liveMatches.get(3);
            assertEquals("Argentina", fourthGame.homeTeam());
            assertEquals("Australia", fourthGame.awayTeam());
            assertEquals(3, fourthGame.homeScore());
            assertEquals(1, fourthGame.awayScore());

            Match fifthGame = liveMatches.get(4);
            assertEquals("Germany", fifthGame.homeTeam());
            assertEquals("France", fifthGame.awayTeam());
            assertEquals(2, fifthGame.homeScore());
            assertEquals(2, fifthGame.awayScore());
        }
    }
}
