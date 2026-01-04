Assumptions & Design Choices

* Update Score

In a real-world scenario, scores may need to be corrected due to input error or VAR overturning a goal.

I considered going with a more traditional scoreboard approach that would allow the user to increment/decrement the away or home teams score paired with a score log that would validate that goals besides the last one scored could not be reverted by VAR as play would have gone on but decided against it to adhere to the guidelines and avoid over-engineering.

* Error Handling

I chose to throw exceptions when attempting to update or finish a match that doesn't exist.