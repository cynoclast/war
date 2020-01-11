# War (card game) Implementation

Simulates the card game War: http://en.wikipedia.org/wiki/War_(card_game)

Players are numbered 0-n

Accepts arbitrary card decks from 2 to java.lang.Integer.MAX_VALUE

## Build:

`./gradlew clean fatJar`

## Example to run:

`java -classpath ./build/libs/war-1.0.jar com.cynoclast.war.War 4 13 2`

## Notes

* If we have an odd number of players the first player will have 1 more card than the other players
* If two players both tie for the highest card played, all players enter 'war', even if third or other players would have lost
* If a player enters into 'war' but has insufficient cards to put down, they'll be removed from the game (lose)