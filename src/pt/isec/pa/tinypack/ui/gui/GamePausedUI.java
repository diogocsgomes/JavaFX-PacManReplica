package pt.isec.pa.tinypack.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.model.fsm.GameState;
import pt.isec.pa.tinypack.model.fsm.PropertyChangeNames;

import java.util.Objects;

public class GamePausedUI extends VBox {

    private Label menu;
    private Button retomar;
    private Button gravar;
    private Button sair;

    private GameManager model;

    public GamePausedUI(GameManager gameManager){
        model = gameManager;

        //super.setStyle("-fx-background-color: rgba(0,0,38,90);");
        super.setStyle("-fx-background-color: rgb(0,0,38);");
        super.setOpacity(0.8);
        super.setSpacing(10);

        menu = new Label("Pausa");
        menu.setTextFill(Color.WHITE);
        menu.setStyle("-fx-font-family: 'Orbitron-Regular';\n" +
                "    -fx-font-size: 20;");



        retomar = new Button("Voltar ao jogo");
        retomar.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        retomar.setPrefWidth(100);





        gravar = new Button("Gravar");
        gravar.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        gravar.setPrefWidth(100);




        sair = new Button("Sair");
        sair.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        sair.setPrefWidth(100);

        createViews();
        registerHandlers();
        update();

    }


    private void createViews(){

        super.getChildren().addAll(menu,retomar,gravar,sair);
        super.setAlignment(Pos.CENTER);



    }
    private void registerHandlers(){
        model.addPropertyChangeListener(PropertyChangeNames.FIRE_MENU, evt -> Platform.runLater(()->update()));
        retomar.setOnAction(e->{
        model.retomar();

    });

    gravar.setOnAction(e->{
        model.gravar();
    });

    sair.setOnAction(e->{
        //LER COMENTARIOS NO METODO sairJogo()
        model.sairJogo();
    });

        //update();
    }

    private void update(){

       // System.out.println("Vim aqui");

        if(Objects.equals(this.model.getStateAsString(), GameState.GAME_PAUSED.toString()))
        {
            this.setVisible(true);
         //   System.out.println("Interface visivel");
            return;
        }

        this.setVisible(false);
       // System.out.println("Interface invisivel");

    }



}
