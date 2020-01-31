package phonosemantics.output.header;

public class Header {
    private int row;
    private int column;
    private String text;
    private int width;
    private int height;

    public Header(int row, int column, String text, int width, int height) {
        this.row = row;
        this.column = column;
        this.text = text;
        this.width = width;
        this.height = height;
    }

    public Header(int row, int column, String text) {
        this.row = row;
        this.column = column;
        this.text = text;
        this.width = 1;
        this.height = 1;
    }

    public Header(int row, int column, String text, int width) {
        this.row = row;
        this.column = column;
        this.text = text;
        this.width = width;
        this.height = 1;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getText() {
        return text;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
