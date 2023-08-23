package pt.isec.pa.tinypack.model.data.mazeElements;

//import com.googlecode.lanterna.input.KeyType;
import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.data.PacManDirections;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class PacMan implements IMazeElement {

    public  static PacMan instance = null;

    private int[] y_x;

    //Image imagem = new Image("")
    //ImageView imagePac = new ImageView(imagem);


   int x1,x2;
    int y1, y2;

    //private KeyCode direction = null;
    private PacManDirections direction = null;


    //private KeyCode next_direction = null;
    private PacManDirections next_direction = null;


   /* private directions direction2;
    private enum directions{
        UP,DOWN,LEFT,RIGHT;
    }


    */


    private PacMan(){
        y_x = new int[2];
    }

    public static PacMan getInstance(){
        if(instance == null)
            instance = new PacMan();
        return instance;
    }


    @Override
    public char getSymbol() {
        return 'M';
    }

    public PacMan getPacMan(){
        return this;
    }


    public PacManDirections getDirection() {
        return direction;
    }

    public void setDirection(PacManDirections direction) {
        this.direction = direction;
    }


    public PacManDirections getNext_direction() {
        return next_direction;
    }

    public void setNext_direction(PacManDirections next_direction) {
        this.next_direction = next_direction;
    }

    public void setY_x(int y, int x)
    {
        y_x[0] = y;
        y_x[1] = x;
    }

    public int[] getY_x(){
        return y_x;
    }

    public  int getX(){return y_x[1];}

    public  int getY(){return y_x[0];}

   /* public directions getDirection2() {
        return direction2;
    }

    public void setDirection2(directions direction2) {
        this.direction2 = direction2;
    }

    */


/*
    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

 */

}
