package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.IMazeElement;

public class Fruit implements IMazeElement {


    private int x,y;


    public Fruit(int x,int y){
        this.x = x;
        this.y = y;
    }


    @Override
    public char getSymbol() {
        return 'F';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



}
