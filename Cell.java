package com.example.hossein.chess;

public class Cell
{
    private Bead object;
    boolean is_empty;

    public Cell()
    {
        is_empty = true;
        object = null;
    }


    public Bead getObject() {
        return object;
    }

    public void setObject(Bead object) {
        this.object = object;
    }


}
