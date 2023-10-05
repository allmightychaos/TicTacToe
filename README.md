# TicTacToe in Java

## Known Issues:

### 1. Game Progress Indicator:
- Current Behavior: During gameplay, the Start-Button's text dynamically changes to "In progress..." based on various function calls.
- Desired Behavior: A consistent background check should ensure that as long as the game is active, the button displays "In progress...". Upon game termination (win, tie, etc.), it should revert to "Start" until the next game session is initiated by the user.

### 2. PC Player Difficulty Selection:
- Issue A: The difficulty selection button is hidden unless the "PC" player option is selected.
    - Solution: Always display the button. If the "PC" option isn't selected, grey it out. If the user attempts to interact with it, display an alert: "Difficulty settings are available only for the 'PC' player option."
- Issue B: The current difficulty selection window is not user-friendly.
    - Solution: Revise the difficulty selector interface to resemble the player-combobox instead of opening in a separate window.

### 3. Game Algorithms Enhancement:
- Current Behavior: The 'easy' difficulty often performs better than the 'hard' difficulty due to its random move selection, whereas the 'hard' difficulty is predictable, starting at the upper left corner without accounting for opponent's potential winning moves.
- Desired Behavior: Improve the 'medium' and 'hard' algorithms. The 'hard' difficulty should be notably challenging, anticipating the player's potential winning moves and strategizing based on predicted future moves.

### 4. PC vs PC Gameplay:
- Current Behavior: When both Player-1 and Player-2 are set to 'PC', only one move occurs before the game stalls.
- Desired Behavior: In 'PC vs PC' mode, the game should alternate between Player-1 and Player-2 every 0.5 seconds, executing moves based on their respective difficulty settings (easy, medium, hard).