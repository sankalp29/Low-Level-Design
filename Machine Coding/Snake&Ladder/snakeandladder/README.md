# Snake and Ladder Game 🎲🐍🪜

## 📌 Problem Statement
Design and implement a **Snake and Ladder application**.  
The game should accept input (from the command line or a file) and simulate the turns of players until one of them wins.

---

## 📝 Input Format
1. **Number of snakes** (`s`), followed by `s` lines containing 2 integers each:
   - **Head position** of the snake.
   - **Tail position** of the snake.

2. **Number of ladders** (`l`), followed by `l` lines containing 2 integers each:
   - **Start position** of the ladder.
   - **End position** of the ladder.

3. **Number of players** (`p`), followed by `p` lines each containing a **player name**.

---

## 🎮 Output Format
For each move, print:
<player_name> rolled a <dice_value> and moved from <initial_position> to <final_position>

When a player wins, print:
<player_name> wins the game

---

## 📏 Rules of the Game
- The board has **100 cells** numbered from **1 to 100**.
- Each player starts at **position 0** (outside the board).
- Players roll a **six-sided dice (1–6)** on their turn.
- Movement:
  - Player moves forward by the dice value.
  - If a player overshoots position 100, they **do not move**.
  - Landing on a **snake head** → move down to its **tail**.
  - Landing on a **ladder base** → climb up to its **end**.
  - If the new position has another snake/ladder, continue moving accordingly until a free cell is reached.
- A player **wins** when they **exactly reach 100**.

---

## ✅ Assumptions
- No snake at position **100**.
- No duplicate snake or ladder starts at the same cell.
- The game setup ensures it’s always possible to win.
- Snakes and ladders will **not form infinite loops**.

---