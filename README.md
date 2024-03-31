# CS102-CardGameProject-G7

## Intro
Welcome to Stress! The Game. Play against one other human opponent to test your skills and strategy in this classic card game.

Terminology: 
	HAND (both players): revealed cards
	DECK (both players): remaining cards unrevealed
	PILE (2): playing cards in the middle

Aim: Get rid of all the cards in your DECK. The faster player wins the game!

## Setup
Each player has 26 cards. At all times, each HAND consists of 4 open cards. The rest of the cards are hidden in the respective DECKs. Players are to use the keyboard to interact with the game.

## Gameplay
Both players' cards are shuffled at the beginning of the game. The game starts with two open cards in the middle, one from each player. Players are to select a card from their HAND to stack onto either PILE in numerical order (increment/decrement by 1) or stack the same card onto the PILE. Players can toggle between the two PILEs. The card played is replaced with the top card from their DECK. 

During the game, players are to watch out for matching cards in the middle. Once both of the cards in the middle are the same, players are to call Stress! using their respective hotkeys. All of the cards in both PILES will be added to the slower player's DECK. The game continues.

In the case of a stalemate where both players' do not have a card to play into the middle, the game goes into Timeout! All cards are reshuffled. The game continues.

The first player to play all their cards wins. Players can choose to replay or exit the game.

## Hotkeys
						  Player 1 (left side of keyboard)/Player 2 (right side of keyboard)
Select a card from HAND : QWER                            /UIOP
Toggle between PILEs	: AD                              /JL
Stress!					: S                               /K 

## Getting the game started
Compile the source files
	compile.bat			javac -d classes -cp ./src/ ./src/App.java 
						xcopy /s /e classes\assets

Run App.java 

Follow the instructions on the screen

## Instructions on the screen during the game
Start game: S + K
Once game ends:
	Restart game: S + K
	Exit game: esc