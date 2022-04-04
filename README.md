# Snap-bot

- ask for number of decks ✓
- should cards be matched by suit, value or both ✓
- shuffle decks before play ✓
- play game ✓
- end when one player has all cards ✓

## To run
`$ sbt run`

## To run tests
`$ sbt test`

### Notes
- The brief for the task mentioned that each player should have their own stack but I found it simpler to implement 
if the game controls one shared stack that both player play on to.
- There are a few error cases I have not handled: eg if the user enters invalid answers to the questions at the beginning
- I have not implemented a flexible number of players, although I was mindful of that extension when designing the bot.
- I have not added integration-level tests to test the full game running but in a real world situation this would certainly be necessary
- There are a lot of println statements in the code to track the game's progress when you run it. In a real world application
a better approach would be needed for the UI which would separate the display from the logic.


