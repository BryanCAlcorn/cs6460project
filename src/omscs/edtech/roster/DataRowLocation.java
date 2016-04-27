package omscs.edtech.roster;

class DataRowLocation {
    private int row;
    private int[] cols;
    private int sheet;

    public DataRowLocation(int sheet, int row, int... cols) {
        this.row = row;
        this.cols = cols;
        this.sheet = sheet;
    }

    public int getRow() {
        return row;
    }

    public int[] getCols() {
        return cols;
    }

    public int getSheet() {
        return sheet;
    }
}
