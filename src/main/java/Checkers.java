import javax.swing.*;

public class Checkers {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFacade gameFacade = new GameFacade();
            gameFacade.start();
        });
    }
}