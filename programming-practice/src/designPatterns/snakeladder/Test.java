package designPatterns.snakeladder;


public class Test {
    public static void main(String[] args) {

    }
}


interface DiceRollingStrategy {
    int simpleRollDiceStrategy(Board board);
}

interface PlayerMovementStrategy {
    boolean updatePositionWhenSnakeOrLadderStrategy(Cell cell);
    boolean updatePositionNormally(Cell cell);
}

class Board {
    int row, col;
    Cell[][] cells;

    public Board(int row, int col) {
        this.row=row;
        this.col=col;
        cells = new Cell[row][col];

        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                Cell cell = new Cell(i,j,null);
                cells[i][j] = cell;
            }
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}

class Cell {
    int x, y;
    Item item; //can be snake or ladder

    public Cell(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public Cell(int x, int y, Item item) {
        this.x=x;
        this.y=y;
        this.item=item;
    }
}

class Item {
    int[] src, destination;
    ItemType itemType;

    public Item(int[] src, int[] destination, ItemType itemType) {
        this.src = src;
        this.destination = destination;
        this.itemType = itemType;
    }
}

class ItemFactory {
    public static Item createItem(int[] src, int[] destination, String itemType) {
        Item item = null;
        if(itemType!=null) {
            ItemType itemType1 = null;
            if(itemType.equalsIgnoreCase("snake"))
                itemType1 = ItemType.SNAKE;
            else if(itemType.equalsIgnoreCase("ladder"))
                itemType1 = ItemType.LADDER;
            item = new Item(src, destination, itemType1);
        }
        else item = new Item(src, destination, null);
        return item;
    }
}

class Player implements DiceRollingStrategy, PlayerMovementStrategy {
    private final String name;
    private final int[] currentPos;
    public Player(String name) {
        this.name=name;
        currentPos = new int[]{0,0};
    }
    public Player(int id, String name) {
        this.name = name;
        currentPos = new int[]{0,0};
    }

    public int[] getCurrentPos() {
        return currentPos;
    }

    public String getName() {
        return name;
    }

    @Override
    public synchronized int simpleRollDiceStrategy(Board board) {
        int totalCells = board.getRow() * board.getCol();
        int min = 1;
        int range = totalCells - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }


    @Override
    public synchronized boolean updatePositionWhenSnakeOrLadderStrategy(Cell cell) {
        Item item = cell.item;
        if(item.itemType == ItemType.SNAKE || item.itemType == ItemType.LADDER) {
            int dx = item.destination[0];
            int dy = item.destination[1];
            currentPos[0] = dx;
            currentPos[1] = dy;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean updatePositionNormally(Cell cell) {
        int dx = cell.x;
        int dy = cell.y;
        currentPos[0] = dx;
        currentPos[1] = dy;
        return true;
    }
}

enum ItemType {
    SNAKE, LADDER
}

