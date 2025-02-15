# 🎲 Sorry! Board Game (Java)

## 📖 About the Project
This is a **Java-based digital version** of the classic board game **"Sorry!"**.  
The game follows the original rules where players **draw cards, move their pawns, and compete** to get all their pawns to the home zone first.  
It features **randomized turns, strategic moves, and competitive gameplay mechanics**.

---

## 🚀 Features
- **Turn-based game mechanics**
- **Randomized card draws** for movement control
- **Special rules for pawn replacement and start-zone movement**
- **Automatic game state tracking**
- **All-in square mechanics implemented**
- **Interactive UI for selecting moves** (if applicable)

---

## 🎮 How to Run the Game
1. **Open the project** in an IDE (e.g., **NetBeans, IntelliJ, Eclipse**).
2. **Navigate to the controller class**.
3. **Click Run** to start the game.

---

## 🎯 Game Rules
- The game starts with a **randomly assigned turn**.
- Each player **draws a card** and selects a pawn to move.
- A pawn can **only leave the start zone** if the player draws a **1 or 2**.
- The turn switches after **each played card**.
- If no valid moves exist, the player **must fold a card**.
- **Pawn interactions**:
  - If a player moves to a position occupied by an opponent’s pawn, **they send that pawn back to the start zone**.
  - A player **cannot move onto a square occupied by their own pawn**.
- The **first player to move all pawns to the home zone wins**.

---

## 🃏 Card Functions
- **Card 1**: Move 1 square forward (can leave start zone).
- **Card 2**: Move 2 squares forward and play again (can leave start zone).
- **Card 3**: Move both pawns forward 3 squares (if possible).
- **Card 4**: Move a pawn **4 squares backward**.
- **Card 5**: Move both pawns forward **5 squares** (if possible).
- **Card 7**: Move a pawn **7 squares forward**.
- **Card 8**: Move a pawn **8 squares forward** or **draw a new card**.
- **Card 10**: Move **10 squares forward** or **1 square backward**.
- **Card 11**: Move **11 squares forward** or **swap places with an enemy pawn**.
- **Card 12**: Move **12 squares forward** or **draw a new card**.
- **Sorry! Card**: If the player has a pawn in the **start zone** and an opponent has an active pawn, they can **swap their pawn with an opponent's and send them back to start**.

---

## 🔧 Technologies Used
- **Language:** Java
- **Game Logic:** OOP principles with Java classes
- **Development Tools:** NetBeans, IntelliJ, Eclipse

---

## 📌 Future Improvements
- Implement **graphical UI** instead of text-based interaction.
- Add **multiplayer support**.
- Include **AI-controlled opponents** for single-player mode.
- Enhance animations for smoother gameplay.

---

## 📫 Contact Me
📧 Email: <dimitrispapadopoulos138@gmail.com>  
💻 GitHub: [Dimitrispap123](https://github.com/Dimitrispap123)  

---
