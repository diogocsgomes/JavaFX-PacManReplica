package pt.isec.pa.tinypack.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//import javafx.scene.text.*;

import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.model.fsm.PropertyChangeNames;

public class TopViewUI extends HBox {

    private GameManager model;

    private Label highScoreLabel,scoreLabel, lives_leftLabel;

    private double highScore, score;

    private Color backgroundColor;
    private BackgroundFill backgroundFill;

    private Background background;


    public TopViewUI(GameManager model){
        this.model = model;
        super.setPadding(new Insets(10,0,0,0));

        this.highScore = model.getHighScore();
        this.score = 0;

        this.highScoreLabel = new Label("High Score: " + highScore);
        this.highScoreLabel.setTextFill(Color.WHITE);
        //this.highScoreLabel.setFont(new Font("Orbitron-Regular",20));
        //this.highScoreLabel.setStyle(".setStyle(\"-fx-font-family: 'Courier'; -fx-font-size: 20;\");");
        this.highScoreLabel.setStyle("-fx-font-family: 'Orbitron-Regular';\n" +
                "    -fx-font-size: 20;");
        this.highScoreLabel.setPadding(new Insets(0,0,0,10));


        this.scoreLabel = new Label("Score: " + score);
        this.scoreLabel.setTextFill(Color.WHITE);
        //this.highScoreLabel.setFont(new Font("Orbitron-Regular",20));
        //this.highScoreLabel.setStyle(".setStyle(\"-fx-font-family: 'Courier'; -fx-font-size: 20;\");");
        this.scoreLabel.setStyle("-fx-font-family: 'Orbitron-Regular';\n" +
                "    -fx-font-size: 20;");


        this.lives_leftLabel = new Label(" Lives left: " + model.getLivesLeft());
        lives_leftLabel.setTextFill(Color.WHITE);
        lives_leftLabel.setStyle("-fx-font-family: 'Orbitron-Regular';\n" +
                "    -fx-font-size: 20;");


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


        super.getChildren().add(this.scoreLabel);
        super.getChildren().add(this.highScoreLabel);
        super.getChildren().add(this.lives_leftLabel);
        super.setAlignment(Pos.CENTER);


    }

    private void registerHandlers(){
        model.addPropertyChangeListener(PropertyChangeNames.FIRE_EVOLVE, evt -> Platform.runLater(()->update()));
    }


    private void update(){

        //scoreLabel = new Label();
        scoreLabel.setText("Score: " + model.getPontos());
        lives_leftLabel.setText(" Lives left: " + model.getLivesLeft());


    }


}
