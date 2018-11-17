package com.example.hossein.chess;


public class Move {
    private Bead FirstBead ;
    private Bead secondBead;

    private int xForFirst ;
    private int yForFirst ;

    private int xFinal ;
    private int yFinal ;

    public Move(Bead firstBead, Bead secondBead, int xForFirst, int yForFirst, int xFinal, int yFinal) {
        this.FirstBead = firstBead;
        this.secondBead = secondBead;
        this.xForFirst = xForFirst;
        this.yForFirst = yForFirst;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
    }

    public Bead getFirstBead() {
        return FirstBead;
    }

    public void setFirstBead(Bead firstBead) {
        FirstBead = firstBead;
    }

    public Bead getSecondBead() {
        return secondBead;
    }

    public void setSecondBead(Bead secondBead) {
        this.secondBead = secondBead;
    }

    public int getxForFirst() {
        return xForFirst;
    }

    public void setxForFirst(int xForFirst) {
        this.xForFirst = xForFirst;
    }

    public int getyForFirst() {
        return yForFirst;
    }

    public void setyForFirst(int yForFirst) {
        this.yForFirst = yForFirst;
    }

    public int getxFinal() {
        return xFinal;
    }

    public void setxFinal(int xFinal) {
        this.xFinal = xFinal;
    }

    public int getyFinal() {
        return yFinal;
    }

    public void setyFinal(int yFinal) {
        this.yFinal = yFinal;
    }
}
