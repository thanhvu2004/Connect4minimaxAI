import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connect4 board = new Connect4();
        AIPlayer aiPlayer = new AIPlayer();

        System.out.println("Game mode: ");
        System.out.println("1. Human vs AI");
        System.out.println("2. Human vs Human");
        System.out.println("Enter option (1 or 2): ");

        int gameMode = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                gameMode = scanner.nextInt();
                if (gameMode == 1 || gameMode == 2) {
                    break;
                } else {
                    System.out.println("Invalid option. Please enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next();
            }
        }

        char player1Symbol = 'R';
        char player2Symbol = 'Y';
        char currentPlayer = player1Symbol;
        int fturn = 0;

        if (gameMode == 2) {
            System.out.println("Player 1 name: ");
            String p1 = scanner.next();
            System.out.println("Player 1 symbol (R or Y): ");
            while (true) {
                String symbol = scanner.next();
                if (symbol.equals("R") || symbol.equals("Y")) {
                    player1Symbol = symbol.charAt(0);
                    break;
                } else {
                    System.out.println("Invalid symbol. Please enter R or Y.");
                }
            }
            System.out.println("Player 2 name: ");
            String p2 = scanner.next();
            player2Symbol = (player1Symbol == 'R') ? 'Y' : 'R';
        } else if (gameMode == 1) {
            System.out.println("Player name: ");
            String p = scanner.next();
            System.out.println("Player symbol (R or Y): ");
            while (true) {
                String symbol = scanner.next().toUpperCase();
                if (symbol.equals("R") || symbol.equals("Y")) {
                    player1Symbol = symbol.charAt(0);
                    player2Symbol = (player1Symbol == 'Y') ? 'R' : 'Y';
                    break;
                } else {
                    System.out.println("Invalid symbol. Please enter R or Y.");
                }
            }
            System.out.println("Do you want to go first? (yes or no): ");
            while (true) {
                String ans = scanner.next().toLowerCase();
                if (ans.equals("yes")){
                    fturn = 1;
                    break; // Add this line to break out of the loop
                } else if (ans.equals("no")) {
                    fturn = 2;
                    break; // Add this line to break out of the loop
                } else {
                    System.out.println("Invalid option. Please enter yes or no.");
                }
            }

        }

        boolean gameOver = false;

        while (!gameOver) {
            System.out.println(fturn);
            // Human player's turn
            if ((fturn % 2 != 0 && gameMode == 1) || gameMode == 2) {
                board.displayBoard();
                System.out.println(currentPlayer + "'s turn. Enter column (0-6): ");
                while (true) {
                    if (scanner.hasNextInt()) {
                        int col = scanner.nextInt();
                        if (col >= 0 && col <= 6) {
                            if (board.dropDisc(col, currentPlayer)) {
                                break;
                            } else {
                                System.out.println("Column is full, please try again.");
                            }
                        } else {
                            System.out.println("Invalid column, please try again.");
                        }
                    } else {
                        System.out.println("Invalid input, please enter a number.");
                        scanner.next();
                    }
                }
                fturn++;
            }

            // Check for win or draw after human player's turn
            if (board.checkWin(currentPlayer)) {
                board.displayBoard();
                System.out.println(currentPlayer + " wins!");
                gameOver = true;
            } else if (board.isBoardFull()) {
                board.displayBoard();
                System.out.println("It's a draw!");
                gameOver = true;
            }

            // Switch players
            currentPlayer = (currentPlayer == player1Symbol) ? player2Symbol : player1Symbol;

            // Check if game over after switching players
            if (gameOver)
                break;

            // AI player's turn
            if (gameMode == 1 && fturn % 2 == 0) {
                int aiMove = aiPlayer.findBestMove(board, (player1Symbol == 'R') ? 'Y' : 'R');
                System.out.println("AI Player chooses column: " + aiMove);
                board.dropDisc(aiMove, currentPlayer);
                fturn++;
            }

            // Check for win or draw after AI player's turn
            if (board.checkWin(currentPlayer)) {
                board.displayBoard();
                System.out.println(currentPlayer + " wins!");
                gameOver = true;
            } else if (board.isBoardFull()) {
                board.displayBoard();
                System.out.println("It's a draw!");
                gameOver = true;
            }

            // Switch players
            currentPlayer = (currentPlayer == player1Symbol) ? player2Symbol : player1Symbol;
        }
        scanner.close();
    }
}
