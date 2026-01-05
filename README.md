Assumptions & Design Choices

* Update Score

In a real-world scenario, scores may need to be corrected due to input error or VAR overturning a goal.

I considered going with a more traditional scoreboard approach that would allow the user to increment/decrement the away or home teams score paired with a score log that would validate that goals besides the last one scored could not be reverted by VAR as play would have gone on but decided against it to adhere to the guidelines and avoid over-engineering.

* Error Handling

I chose to throw exceptions when attempting to update or finish a match that doesn't exist.

Considerations & Future Work

This library only invalidates a user from starting a game already in progress.
It makes no effort to check if the user tries to start a game with a team that is already in a live match against another one.

("TeamA vs TeamB" and "TeamB vs TeamA" at the same time is therefore allowed as it generates a different key)