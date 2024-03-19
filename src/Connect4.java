public class Connect4 {
    private final int ROWS = 6;
    private final int COLS = 7;
    private final char EMPTY_SLOT = '-';
    private char[][] board;

    public Connect4() {
        board = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY_SLOT;
            }
        }
    }

    // Display the current state of the board
    public void displayBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6");
    }

    // Drop a disc in the specified column
    public boolean dropDisc(int col, char player) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY_SLOT) {
                board[i][col] = player;
                return true;
            }
        }
        return false; // Column is full
    }

    // Check if the board is full
    public boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY_SLOT) {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if the current player has won
    public boolean checkWin(char player) {
        // Check horizontal
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player &&
                        board[i][j + 1] == player &&
                        board[i][j + 2] == player &&
                        board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check vertical
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i <= ROWS - 4; i++) {
                if (board[i][j] == player &&
                        board[i + 1][j] == player &&
                        board[i + 2][j] == player &&
                        board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // Check diagonal (top-left to bottom-right)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player &&
                        board[i + 1][j + 1] == player &&
                        board[i + 2][j + 2] == player &&
                        board[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check diagonal (top-right to bottom-left)
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = COLS - 1; j >= 3; j--) {
                if (board[i][j] == player &&
                        board[i + 1][j - 1] == player &&
                        board[i + 2][j - 2] == player &&
                        board[i + 3][j - 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    // Get available moves
    public int[] getAvailableMoves() {
        int[] moves = new int[COLS];
        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == EMPTY_SLOT) {
                moves[i] = i;
            } else {
                moves[i] = -1;
            }
        }
        return moves;
    }

    // Make a copy of the board
    public Connect4 copyBoard() {
        Connect4 newBoard = new Connect4();
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(board[i], 0, newBoard.board[i], 0, COLS);
        }
        return newBoard;
    }
}
