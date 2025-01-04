import javax.swing.*;
import java.awt.*;

// User interface
class GameUI extends JFrame implements GameObserver {
    private GameFacade gameFacade;
    private JLabel messageLabel;
    private JLabel turnLabel;
    private JButton[][] buttons;
    private int fromRow, fromCol;
    private boolean isSelecting;

    public GameUI(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
        this.fromRow = -1;
        this.fromCol = -1;
        this.isSelecting = true;
        setupUI();
    }

    private void setupUI() {
        setTitle("Checkers Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        buttons = new JButton[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                int row = i;
                int col = j;
                buttons[i][j].addActionListener(e -> handleClick(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        JPanel infoPanel = new JPanel(new BorderLayout());

        turnLabel = new JLabel("Turn: White", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));

        messageLabel = new JLabel("Welcome to Checkers!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        infoPanel.add(turnLabel, BorderLayout.NORTH);
        infoPanel.add(messageLabel, BorderLayout.SOUTH);

        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void handleClick(int row, int col) {
        if (isSelecting) {
            fromRow = row;
            fromCol = col;
            isSelecting = false;
            messageLabel.setText("Select destination.");
        } else {
            gameFacade.makeMove(fromRow, fromCol, row, col);
            isSelecting = true;
        }
    }

    @Override
    public void update(Board board, Player currentPlayer) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                char piece = board.getPiece(i, j);
                if (piece == '.') {
                    buttons[i][j].setText("");
                } else if (piece == 'x') {
                    buttons[i][j].setText("♙"); // White pawn symbol
                    buttons[i][j].setForeground(Color.WHITE);
                } else if (piece == 'o') {
                    buttons[i][j].setText("♟"); // Black pawn symbol
                    buttons[i][j].setForeground(Color.BLACK);
                } else {
                    buttons[i][j].setText("");
                }
            }
        }
        turnLabel.setText("Turn: " + currentPlayer.getName());
        messageLabel.setText("Your turn, " + currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ").");
    }

    @Override
    public void updateMessage(String message) {
        messageLabel.setText(message);
    }
}
