package com.cppsemantictool.backend.model.responsemodel;

public class SyntaxErrorResponse {
    private int row;
    private int col;
    private String message;

    public SyntaxErrorResponse(int row, int col, String message) {
        this.row = row;
        this.col = col;
        this.message = message;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
