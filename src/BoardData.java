public class BoardData {
    private static int hashStartPosition;
    private int data[];
    private int hash;
    private Area area;

    public BoardData() {
        this.area = new Area(0, 0, Board.width, Board.height);
        data = new int[Board.height];
        int pos = 0;
        if (hashStartPosition == 0) {
            while (Board.height >> pos != 0) {
                pos++;
            }
            hashStartPosition = pos;
        }
        hash = 0;
    }

    public BoardData(BoardData boardData) {
        this.area = boardData.area;
        this.data = new int[Board.height];
        System.arraycopy(boardData.data, area.y1, data, area.y1, area.y2 - area.y1);
    }

    public void setArea(Area area) {
        this.area = area;
        hash = 0;
    }

    public boolean get(int y, int x) {
        return (data[y] & 1 << x) != 0;
    }

    public int get(int y) {
        return data[y];
    }

    public void set(int y, int x, boolean value) {
        if (value) {
            data[y] |= 1 << x;
        } else {
            data[y] &= ~(1 << x);
        }
    }

    public void set(int y, int row) {
        data[y] = row;
    }

    public void toggleBlock(Block block, int[] position) {
        for (int i = 0; i < block.height; i++) {
            data[i + position[0]] ^= block.get(i) << position[1];
        }
    }

    public void mirrorData() {
        int[] newData = new int[Board.height];
        for (int i = area.y1; i < area.y2; i++) {
            for (int j = area.x1; j < area.x2; j++) {
                newData[i] |= ((data[i] & 1 << j) >> j) << (area.x2 - (j - area.x1) - 1);
                // For better reading:
                // long bit = (data[i] & 1 << j) >> j;
                // newData[i] |= bit << (area.x2 - (j - area.x1) - 1);
            }
        }
        data = newData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardData boardData = (BoardData) o;

        if (area.height != boardData.area.height) return false;
        if (area.width != boardData.area.width) return false;
        for (int i = area.y1; i < area.y2; i++) {
            if (data[i] != boardData.data[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hash != 0) {
            return hash;
        }
        int key = area.height;
        int pos = hashStartPosition;
        int iStart = area.y1 + 2;
        if (area.y2 - 1 < iStart) {
            iStart = area.y2 - 1;
        }
//        long start = System.currentTimeMillis();
        for (int i = iStart; i >= area.y1; i--) {
            for (int j = area.x2 - 1; j >= area.x1; j--) {
                key ^= (data[i] & 1 << j) >> j << pos;
                pos++;
                if (pos == 32) {
                    pos = hashStartPosition;
                }
            }
        }
//        Tetris.fooCounter += System.currentTimeMillis() - start;
        hash = key;
        return key;
    }
}
