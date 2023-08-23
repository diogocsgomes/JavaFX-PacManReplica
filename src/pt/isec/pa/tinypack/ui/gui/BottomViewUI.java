package pt.isec.pa.tinypack.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.model.fsm.GameState;
import pt.isec.pa.tinypack.model.fsm.PropertyChangeNames;

import java.util.Objects;

public class BottomViewUI extends VBox {

    private GameManager model;
    private Button menu_button;


    private Color backgroundColor;
    private BackgroundFill backgroundFill;

    private Background background;

    public BottomViewUI(GameManager gameManager)
    {
        this.model = gameManager;
        super.setPadding(new Insets(0,0,10,0));

        menu_button = new Button("MENU");
        menu_button.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");

        //menu_button.setPadding(new Insets(0,0,10,0));

        backgroundColor = Color.color(0,0,0.15);

        // Cria um BackgroundFill com a cor de fundo
        backgroundFill = new BackgroundFill(backgroundColor, null, null);

        // Cria um Background com o BackgroundFill
        background = new Background(backgroundFill);
        super.setBackground(background);


        createViews();
        registerHandlers();
        update();
    }

    private void createViews()
    {


        super.getChildren().add(menu_button);
        super.setAlignment(Pos.CENTER);



    }

    private void registerHandlers(){

        //this.model.changeState(GameState.GAME_PAUSED);
        model.addPropertyChangeListener(PropertyChangeNames.FIRE_MENU, evt -> {update();});
        menu_button.setOnAction(e->{
            model.menu();
            //this.model.changeState(GameState.GAME_PAUSED);
            System.out.println(model.getStateAsString());
        });




    }


    private void update(){

        if(Objects.equals(this.model.getStateAsString(), GameState.GAME_PAUSED.toString()))
        {
            menu_button.setVisible(false);
           // System.out.println("Interface visivel");
            return;
        }

        menu_button.setVisible(true);
        //System.out.println("Interface invisivel");

    }

}
