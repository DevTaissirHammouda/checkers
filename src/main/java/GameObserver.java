interface GameObserver {
    void update(Board board, Player currentPlayer);
    void updateMessage(String message);
}
