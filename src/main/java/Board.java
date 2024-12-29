class Board {
    private Tile[][] tiles;
    private static final int SIZE = 8;

    public Board() {
        tiles = new Tile[SIZE][SIZE];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i + j) % 2 == 1) {
                    if (i < 3) {
                        tiles[i][j] = new Tile('o');
                    } else if (i > 4) {
                        tiles[i][j] = new Tile('x');
                    } else {
                        tiles[i][j] = new Tile('.');
                    }
                } else {
                    tiles[i][j] = new Tile(' ');
                }
            }
        }
    }

    public char getPiece(int row, int col) {
        return tiles[row][col].getPiece();
    }

    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol, player)) {
            return false;
        }

        char piece = tiles[fromRow][fromCol].getPiece();
        tiles[fromRow][fromCol].setPiece('.');
        tiles[toRow][toCol].setPiece(piece);

        if (Math.abs(fromRow - toRow) == 2) { // Capture move
            int capturedRow = (fromRow + toRow) / 2;
            int capturedCol = (fromCol + toCol) / 2;
            tiles[capturedRow][capturedCol].setPiece('.');
        }
        return true;
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        if (fromRow < 0 || fromRow >= SIZE || fromCol < 0 || fromCol >= SIZE ||
                toRow < 0 || toRow >= SIZE || toCol < 0 || toCol >= SIZE) {
            return false;
        }

        if (tiles[fromRow][fromCol].getPiece() != player.getSymbol() || tiles[toRow][toCol].getPiece() != '.') {
            return false;
        }

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;

        if (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1) {
            return true; // Regular move
        }

        if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2) {
            int capturedRow = (fromRow + toRow) / 2;
            int capturedCol = (fromCol + toCol) / 2;
            if (tiles[capturedRow][capturedCol].getPiece() == player.getOpponentSymbol()) {
                return true; // Capture move
            }
        }

        return false;
    }

    public boolean checkWinCondition(Player opponent) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j].getPiece() == opponent.getSymbol()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getSize() {
        return SIZE;
    }
}