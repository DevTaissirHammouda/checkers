class GameFacade {
    private GameManager gameManager;
    private GameUI gameUI;

    public GameFacade() {
        gameManager = GameManager.getInstance();
        gameUI = new GameUI(this);
        gameManager.setObserver(gameUI);
    }

    public void start() {
        gameManager.startGame();
    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        gameManager.makeMove(fromRow, fromCol, toRow, toCol);
    }
}