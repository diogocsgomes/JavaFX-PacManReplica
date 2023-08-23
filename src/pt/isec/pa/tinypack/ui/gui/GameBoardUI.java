package pt.isec.pa.tinypack.ui.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.model.fsm.GameState;
import pt.isec.pa.tinypack.model.fsm.PropertyChangeNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoardUI extends GridPane {

    private GameManager model;

    //private Shape[][] boardElements;

    private  int NUM_ROWNS;
    private  int NUM_COLUMS;

    private double rect_width; //sera um qudrado por isso nao e perciso altura
    //private double rect_height;

    private double small_circle_radius;

    private double big_circle_radius;

    Image PacImage = null;
   // private final String FIRE_EVOLVE = "Fire evolve";


    public GameBoardUI(GameManager model){



        //IMazeElement[][] aux_board = model.getBoard();

        this.model = model;
        Color backgroundColor = Color.color(0,0,0.15);

        // Cria um BackgroundFill com a cor de fundo
        BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, null, null);

        // Cria um Background com o BackgroundFill
        Background background = new Background(backgroundFill);
        super.setBackground(background);
        //super.setHgap(5);
        //super.setVgap(5);
        this.setAlignment(Pos.CENTER);
        this.setFocusTraversable(true);
        createViews();
        registerHandlers();
        update();


    }

    private void createViews(){
        NUM_ROWNS = model.getNumLinhas();
        NUM_COLUMS = model.getNumColunas();

        if(NUM_ROWNS >= NUM_COLUMS)
        {
            rect_width = model.getBORAD_WIDTH_AND_WEIGHT_PIXEL()/(double) NUM_ROWNS;
            big_circle_radius = 0.3 * rect_width;
            small_circle_radius = 0.15 * rect_width;
        }
        if(NUM_COLUMS > NUM_ROWNS)
        {
            rect_width = model.getBORAD_WIDTH_AND_WEIGHT_PIXEL()/(double) NUM_COLUMS;
            big_circle_radius = 0.3 * rect_width;
            small_circle_radius = 0.15 * rect_width;
        }
        System.out.println("Pix: " + rect_width);

        //showMaze();

    }

    private void registerHandlers(){


        model.addPropertyChangeListener(PropertyChangeNames.FIRE_EVOLVE, evt -> Platform.runLater(()->update()));

        // model.addPropertyChangeListener(FIRE_EVOLVE,evt -> Platform.runLater(()->update()));

        this.setOnKeyPressed(e->{
            //if(e.getCode() == KeyCode.UP)
              //  System.out.println("ola");
            model.setInput(e.getCode());
        });



        /*this.setOnKeyPressed(e -> {
            System.out.println("Tecla pressionada: " + e.getCode());
        });

         */


       /* this.setOnMouseClicked(e->{
            System.out.println("ola");

        });

        */

    }

    private void update(){
        requestFocus();
        //this.setFocusTraversable(true);
       // System.out.println(isFocused());
        removePreviousElements();
        showMaze();

    }


    private void showMaze(){
        char [][] aux_board  = model.getCharMaze();

        for(int i = 0; i < NUM_ROWNS; i++)
        {
            for(int j = 0; j < NUM_COLUMS; j++)
            {
                switch (aux_board[i][j]){

                    case 'x' -> {
                        Rectangle ret = new Rectangle(rect_width,rect_width);//largura,altura

                        ret.setFill(Color.PINK);
                        ret.setStroke(Color.PINK);
                        //ret.setStroke(Color.BLACK);
                        super.add(ret,j,i);

                    }//j primeiro porque ordem e coluna linha

                    case 'W'->{


                        Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\portal-pixel.gif");

                        //Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\warp.png");
                        //Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\cereja.jfif");

                        ImageView portal = new ImageView(image);
                        portal.setFitWidth(rect_width);
                        portal.setFitHeight(rect_width);
                        super.add(portal,j,i);

                        //ret.setStroke(Color.BLACK);
                        //super.add(ret,j,i);
                    }

                    case 'o'-> {
                        Circle circ = new Circle(small_circle_radius);
                        circ.setFill(Color.WHITE);
                        circ.setStroke(Color.BLACK);

                        super.add(circ,j,i);
                        GridPane.setHalignment(circ, HPos.CENTER);
                        GridPane.setValignment(circ, VPos.CENTER);
                    }
                    case 'F' ->{

                        Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\laranja.png");
                        //Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\cereja.jfif");

                        ImageView fruta = new ImageView(image);
                        fruta.setFitWidth(rect_width);
                        fruta.setFitHeight(rect_width);
                        super.add(fruta,j,i);

                    }
                    case 'M'->{
                        PacImage = model.getPacImage();
                        if(PacImage == null) {
                            Circle PacManNoMove = new Circle(0.49 * rect_width);
                            PacManNoMove.setStroke(Color.YELLOW);
                            PacManNoMove.setFill(Color.YELLOW);
                            super.add(PacManNoMove, j, i);
                            GridPane.setHalignment(PacManNoMove, HPos.CENTER);
                            GridPane.setValignment(PacManNoMove, VPos.CENTER);
                        }
                        else {
                            ImageView PacManGif = new ImageView(PacImage);
                            PacManGif.setFitHeight(0.99 * rect_width);
                            PacManGif.setFitWidth(0.99 * rect_width);
                            super.add(PacManGif, j, i);

                        }

                        //Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\pacmanRight.gif");
                        //ImageView PacManGif = new ImageView(image);
                        //PacManGif.setFitHeight(0.99 * rect_width);
                        //PacManGif.setFitWidth(0.99 * rect_width);
                        //super.add(PacManGif,j,i);
                    }

                    case 'O' -> {
                        Circle circ = new Circle(big_circle_radius);
                        circ.setFill(Color.WHITE);
                        circ.setStroke(Color.BLACK);
                        super.add(circ,j,i);
                        GridPane.setHalignment(circ, HPos.CENTER);
                        GridPane.setValignment(circ, VPos.CENTER);
                    }

                    case 'Y' ->{
                        Rectangle ret = new Rectangle(rect_width, 0.10 * rect_width);//largura,altura

                        ret.setFill(Color.WHITE);
                        ret.setStroke(Color.WHITE);
                        //ret.setStroke(Color.BLACK);
                        super.add(ret,j,i);
                    }

                    case 'y' ->{
                        Rectangle ret = new Rectangle(rect_width,rect_width);

                        ret.setFill(Color.BLACK);
                        ret.setStroke(Color.BLACK);
                        super.add(ret,j,i);
                    }

                    case ' ' -> {
                        Rectangle ret = new Rectangle(rect_width,rect_width);

                        ret.setFill(Color.TRANSPARENT);
                        ret.setStroke(Color.TRANSPARENT);
                        super.add(ret,j,i);

                    }

                    case 'B'->{
                        if(model.getState() == GameState.ONGOING_GAME_POWER)
                        {
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\blueghost.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }


                        else{
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\Blinky.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }

                    }

                    case 'I'->{
                        if(model.getState() == GameState.ONGOING_GAME_POWER)
                        {
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\blueghost.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }


                        else{
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\Inky.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }
                    }

                    case 'P'->{
                        if(model.getState() == GameState.ONGOING_GAME_POWER)
                        {
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\blueghost.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }


                        else{
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\Pinky.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }
                    }
                    case 'C'->{
                        if(model.getState() == GameState.ONGOING_GAME_POWER)
                        {
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\blueghost.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }


                        else{
                            Image image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\Clyde.gif");
                            ImageView portal = new ImageView(image);
                            portal.setFitWidth(rect_width);
                            portal.setFitHeight(rect_width);
                            super.add(portal,j,i);
                        }
                    }

                    default -> throw new IllegalArgumentException("Caracter Invalido: " + aux_board[i][j]);
                }
            }
        }

    }



    private void removePreviousElements(){
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : super.getChildren()) {
                nodesToRemove.add(node);

        }

        super.getChildren().removeAll(nodesToRemove);

        /*for (Node node : super.getChildren()) {
                super.getChildren().remove(node);


        }

         */
    }




}
