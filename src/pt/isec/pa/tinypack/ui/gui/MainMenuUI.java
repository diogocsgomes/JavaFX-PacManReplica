package pt.isec.pa.tinypack.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.model.fsm.PropertyChangeNames;

public class MainMenuUI extends BorderPane {

    private GameManager model;
    private Button iniciarJogo;
    private Button consultarTop5;
    private Button sair;

    private Label infoLabel; // o DEIS-ISEC-IPC, curso,unidade curricular,nome,numero

    private  Label tinyPac;
    private Image ISEC_IMAGE;
    private ImageView ISEC_ImageView;

    private Image pac_image;
    private ImageView pac_imageView;


    public MainMenuUI(GameManager gameManager)
    {
        model = gameManager;
        iniciarJogo = new Button("Inicar o jogo");
        iniciarJogo.setPrefWidth(100);
        iniciarJogo.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");

        consultarTop5 = new Button("Consultar top 5");
        consultarTop5.setPrefWidth(100);
        consultarTop5.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");

        sair = new Button("Sair");
        sair.setPrefWidth(100);
        sair.setStyle("-fx-background-color: #000026FF; -fx-border-color: #ffffff; -fx-text-fill: #FFFFFF;" +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5;");


        infoLabel = new Label("Engenharia informática\nProgramação Avancada\nDEIS-ISEC-IPC\nDiogo Gomes 2021137427");
        infoLabel.setTextFill(Color.WHITE);
        infoLabel.setStyle("-fx-font-family: 'Orbitron-Regular';\n" +
                "    -fx-font-size: 20;");

        tinyPac = new Label("Tiny-Pac");
        tinyPac.setTextFill(Color.YELLOW);
        tinyPac.setStyle("-fx-font-family: 'Orbitron-Regular';\n" +
                "    -fx-font-size: 50;");



        ISEC_IMAGE = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\logo_isec.png");

        ISEC_ImageView = new ImageView(ISEC_IMAGE);
        ISEC_ImageView.setFitHeight(100);
        ISEC_ImageView.setFitWidth(300);

        pac_image = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\pacmanRight.gif");

        pac_imageView = new ImageView(pac_image);
        pac_imageView.setFitHeight(100);
        pac_imageView.setFitWidth(100);





        Color backgroundColor = Color.color(0,0,0.15);

        // Cria um BackgroundFill com a cor de fundo
        BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, null, null);

        // Cria um Background com o BackgroundFill
        Background background = new Background(backgroundFill);
        super.setBackground(background);


        createViews();
        registerHandlers();
        update();

    }


    private void createViews(){

        HBox hBox = new HBox(infoLabel, ISEC_ImageView);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        setTop(hBox);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(0,0,10,10));
        hBox2.getChildren().addAll(iniciarJogo,consultarTop5,sair);

         VBox vBox2 = new VBox();
         vBox2.setSpacing(10);
         vBox2.getChildren().addAll(tinyPac,pac_imageView);
         vBox2.setAlignment(Pos.CENTER);
         setCenter(vBox2);


         setBottom(hBox2);


    }

    private void registerHandlers(){

        model.addPropertyChangeListener(PropertyChangeNames.FIRE_INICIAR_JOGO, evt -> {update();});

        iniciarJogo.setOnAction(e->{

            model.setMainMenuVisible(false);//desliga a viw do main menu
            model.setGameViewVisible(true);//liga a vista do jogo
           model.iniciarJogo();
        });

        sair.setOnAction(e->{
            model.sair();
        });


        consultarTop5.setOnAction(e->{
            model.consultarTop5();
        });


        /*this.setOnMouseClicked(e->{
            System.out.println("Pressionado up");

        });

         */





    }

    private  void update(){

        super.setVisible(model.isMainMenuVisible());

    }






}
