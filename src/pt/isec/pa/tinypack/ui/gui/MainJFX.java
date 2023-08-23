package pt.isec.pa.tinypack.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pt.isec.pa.tinypack.Main;
import pt.isec.pa.tinypack.model.fsm.GameManager;

public class MainJFX extends Application {


   private GameManager model;

    public MainJFX() {
        this.model = Main.model;
    }

    @Override
    public void start(Stage stage) throws Exception {


        RootPane root = new RootPane(model);
        //Scene scene = new Scene(root,model.getWINDOW_WIDTH(), model.getWINDOW_HEIGHT()); //Width height
        Scene scene = new Scene(new StackPane(root,new MainMenuUI(model)),model.getWINDOW_WIDTH(), model.getWINDOW_HEIGHT()); //Width height

        stage.setTitle("Tiny-PacMan");
        stage.setScene(scene);
        //stage.setResizable(true);
        stage.setResizable(false);
        stage.show();
        //newStageForTesting(new Stage(),"For testing");

    }

    private void newStageForTesting(Stage stage, String title){
        RootPane root = new RootPane(model);
        //Scene scene = new Scene(root,model.getWINDOW_WIDTH(), model.getWINDOW_HEIGHT()); //Width height
        Scene scene = new Scene(new StackPane(root,new MainMenuUI(model)),model.getWINDOW_WIDTH(), model.getWINDOW_HEIGHT()); //Width height

        stage.setTitle(title);
        stage.setScene(scene);
        //stage.setResizable(true);
        stage.setResizable(false);
        stage.show();

    }
}
