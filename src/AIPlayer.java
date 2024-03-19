public class AIPlayer {
    private final int MAX_DEPTH = 5;

    // Minimax algorithm with alpha-beta pruning
    public int minimax(Connect4 board, int depth, int alpha, int beta, boolean maximizingPlayer, char AISymbol) {
        char playerSymbol = (AISymbol=='R') ? 'Y' : 'R';
        if (depth == 0 || board.checkWin(AISymbol) || board.checkWin(playerSymbol) || board.isBoardFull()) {
            if (board.checkWin(AISymbol)) {
                return Integer.MIN_VALUE + depth;
            } else if (board.checkWin(playerSymbol)) {
                return Integer.MAX_VALUE - depth;
            } else {
                return 0;
            }
        }

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (int move : board.getAvailableMoves()) {
                if (move != -1) {
                    Connect4 newBoard = board.copyBoard();
                    newBoard.dropDisc(move, playerSymbol);
                    value = Math.max(value, minimax(newBoard, depth - 1, alpha, beta, false, AISymbol));
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (int move : board.getAvailableMoves()) {
                if (move != -1) {
                    Connect4 newBoard = board.copyBoard();
                    newBoard.dropDisc(move, AISymbol);
                    value = Math.min(value, minimax(newBoard, depth - 1, alpha, beta, true, AISymbol));
                    beta = Math.min(beta, value);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return value;
        }
    }

    // Find the best move for the AI player
    public int findBestMove(Connect4 board, char AISymbol) {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;
        for (int move : board.getAvailableMoves()) {
            if (move != -1) {
                Connect4 newBoard = board.copyBoard();
                newBoard.dropDisc(move, AISymbol);
                int value = minimax(newBoard, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false, AISymbol);
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }
}