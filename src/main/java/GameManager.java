import javax.swing.*;
import java.awt.*;

class GameManager {
    private static GameManager instance;
    private Board board;
    private Player[] players;
    private int currentPlayer;
    private GameObserver observer;

    private GameManager() {
        initializeGame();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    public void startGame() {
        updateObserver();
    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        Player player = players[currentPlayer];
        if (board.movePiece(fromRow, fromCol, toRow, toCol, player)) {
            if (board.checkWinCondition(players[1 - currentPlayer])) {
                showGameOverAlert(player);
                return;
            }
            currentPlayer = 1 - currentPlayer;
            updateObserver();
        } else {
            updateObserver("Invalid move. Try again.");
        }
    }

    private void updateObserver() {
        if (observer != null) {
            observer.update(board, players[currentPlayer]);
        }
    }

    private void updateObserver(String message) {
        if (observer != null) {
            observer.updateMessage(message);
        }
    }

    private void showGameOverAlert(Player winner) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(
                "Congratulations, " + winner.getName() + "! You are the winner!",
                SwingConstants.CENTER
        );
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                null,
                panel,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );

        resetGame();
    }


    private void resetGame() {
        initializeGame();
        updateObserver();
    }

    private void initializeGame() {
        board = new Board();
        players = new Player[]{new Player("White", 'x'), new Player("Black", 'o')};
        currentPlayer = 0;
    }
}
