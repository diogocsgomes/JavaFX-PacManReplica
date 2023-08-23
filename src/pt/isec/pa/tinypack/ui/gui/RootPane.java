package pt.isec.pa.tinypack.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.model.fsm.PropertyChangeNames;

import java.util.PropertyPermission;

public class RootPane extends BorderPane {

    private GameManager model;
    private GameBoardUI gameBoard;
    private TopViewUI topViewUI;
    private  BottomViewUI bottomViewUI;
    private GamePausedUI gamePausedUI;

    private StackPane centerStack;

    public RootPane(GameManager model)
    {

        this.model = model;
        this.topViewUI = new TopViewUI(this.model);
        this.gameBoard = new GameBoardUI(this.model);
        this.bottomViewUI = new BottomViewUI(this.model);
        this.gamePausedUI = new GamePausedUI(this.model);

        this.centerStack = new StackPane(this.gameBoard,this.gamePausedUI);

        createViews();
        registerHandlers();
        update();
    }


    private void createViews()
    {

       // this.setAlignment();
        //Pane test = new Pane();
       /* Circle test = new Circle(10);
        setTop(test);
        Node top = super.getTop();
        top.prefHeight(0.1 * model.getWINDOW_HEIGHT());
        top.prefWidth(model.getWINDOW_WIDTH());


        */
        this.setTop(topViewUI);

        //this.setCenter(gameBoard);
        this.setCenter(centerStack);


        this.setBottom(bottomViewUI);


        //this.setCenter(gameBoard);
             /* Node center = getCenter();
        center.prefHeight(0.2 * model.getWINDOW_HEIGHT());
        center.prefWidth(0.9 * model.getWINDOW_WIDTH());
        setAlignment(center, Pos.BOTTOM_LEFT);


        */
/*
        setBottom(new Circle(10));
        Node bottom = getBottom();
        bottom.prefHeight(0.1 * model.getWINDOW_HEIGHT());
        bottom.prefWidth(model.getWINDOW_WIDTH());


 */





        //setCenter(gameBoard);
        //this.getChildren().add(gameBoard);


    }


    private void registerHandlers(){

        model.addPropertyChangeListener(PropertyChangeNames.FIRE_INICIAR_JOGO, evt -> {update();});

        //update();
    }

    private void update(){

        super.setVisible(model.isGameViewVisible());
        //super.setVisible(true);
    }

}
