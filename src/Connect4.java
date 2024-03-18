import java.util.Scanner;

public class Connect4 {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY_SLOT = '-';
    private static final char PLAYER_ONE = 'X';
    private static final char PLAYER_TWO = 'O';
    private final char[][] board = new char[ROWS][COLS];

    public void play() {
        initializeBoard();
        printBoard();

        Scanner scanner = new Scanner(System.in);
        char currentPlayer = PLAYER_ONE;

        while (!isGameOver()) {
            if (currentPlayer == PLAYER_ONE) {
                // Human player's turn
                int column;
                do {
                    System.out.print("Player X, enter column (0-6): ");
                    column = scanner.nextInt();
                } while (!isValidMove(column));
                makeMove(currentPlayer, column);
            } else {
                // AI player's turn
                int[] bestMove = minimax(3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                int column = bestMove[0];
                makeMove(currentPlayer, column);
            }

            printBoard();
            currentPlayer = (currentPlayer == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
        }

        char winner = getWinner();
        if (winner == EMPTY_SLOT) {
            System.out.println("It's a draw!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY_SLOT;
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6");
    }

    private boolean isValidMove(int col) {
        return col >= 0 && col < COLS && board[0][col] == EMPTY_SLOT;
    }

    private void makeMove(char player, int col) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY_SLOT) {
                board[i][col] = player;
                break;
            }
        }
    }

    private boolean isGameOver() {
        return getWinner() != EMPTY_SLOT || isBoardFull();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY_SLOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private char getWinner() {
        // Check horizontally
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] != EMPTY_SLOT && board[i][j] == board[i][j + 1] &&
                        board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    return board[i][j];
                }
            }
        }
        // Check vertically
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] != EMPTY_SLOT && board[i][j] == board[i + 1][j] &&
                        board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    return board[i][j];
                }
            }
        }
        // Check diagonally (down-right)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] != EMPTY_SLOT && board[i][j] == board[i + 1][j + 1] &&
                        board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    return board[i][j];
                }
            }
        }
        // Check diagonally (up-right)
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] != EMPTY_SLOT && board[i][j] == board[i - 1][j + 1] &&
                        board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                    return board[i][j];
                }
            }
        }
        return EMPTY_SLOT;
    }

    private int[] minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        char[][] currboard = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(board[i], 0, currboard[i], 0, COLS);
        }
        int[] bestMove = {-1, 0};
        if (depth == 0 || isGameOver()) {
            int score = evaluateBoard();
            bestMove[1] = score;
            return bestMove;
        }

        if (maximizingPlayer) {
            bestMove[1] = Integer.MIN_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (isValidMove(col)) {
                    makeMove(PLAYER_TWO, col);
                    int[] currentMove = minimax(depth - 1, alpha, beta, false);
                    if (currentMove[1] > bestMove[1]) {
                        bestMove[0] = col;
                        bestMove[1] = currentMove[1];
                    }
                    alpha = Math.max(alpha, currentMove[1]);
                    if (beta <= alpha)
                        break;
                    for (int i = 0; i < ROWS; i++) { // return board to original state
                        System.arraycopy(currboard[i], 0, board[i], 0, COLS);
                    }
                }
            }
            return bestMove;
        } else {
            bestMove[1] = Integer.MAX_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (isValidMove(col)) {
                    makeMove(PLAYER_ONE, col);
                    int[] currentMove = minimax(depth - 1, alpha, beta, true);
                    if (currentMove[1] < bestMove[1]) {
                        bestMove[0] = col;
                        bestMove[1] = currentMove[1];
                    }
                    beta = Math.min(beta, currentMove[1]);
                    if (beta <= alpha)
                        break;
                    for (int i = 0; i < ROWS; i++) { // return board to original state
                        System.arraycopy(currboard[i], 0, board[i], 0, COLS);
                    }
                }
            }
            return bestMove;
        }
    }

    private int evaluateBoard() {
        int score = 0;
        // Evaluate rows
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                score += evaluateWindow(i, j, i, j + 1, i, j + 2, i, j + 3);
            }
        }
        // Evaluate columns
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j < COLS; j++) {
                score += evaluateWindow(i, j, i + 1, j, i + 2, j, i + 3, j);
            }
        }
        // Evaluate diagonals (down-right)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                score += evaluateWindow(i, j, i + 1, j + 1, i + 2, j + 2, i + 3, j + 3);
            }
        }
        // Evaluate diagonals (up-right)
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                score += evaluateWindow(i, j, i - 1, j + 1, i - 2, j + 2, i - 3, j + 3);
            }
        }
        return score;
    }

    private int evaluateWindow(int r1, int c1, int r2, int c2, int r3, int c3, int r4, int c4) {
        int score = 0;
        char[] window = {board[r1][c1], board[r2][c2], board[r3][c3], board[r4][c4]};
        int countX = 0;
        int countO = 0;
        int countEmpty = 0;

        for (char c : window) {
            if (c == PLAYER_ONE) {
                countX++;
            } else if (c == PLAYER_TWO) {
                countO++;
            } else {
                countEmpty++;
            }
        }

        if (countX == 4) {
            score += 100;
        } else if (countX == 3 && countEmpty == 1) {
            score += 5;
        } else if (countX == 2 && countEmpty == 2) {
            score += 2;
        }
        if (countO == 4) {
            score -= 100;
        } else if (countO == 3 && countEmpty == 1) {
            score -= 5;
        } else if (countO == 2 && countEmpty == 2) {
            score -= 2;
        }

        return score;
    }
}
