package pt.isec.pa.tinypack;

import javafx.application.Application;
import pt.isec.pa.tinypack.model.fsm.Context;
import pt.isec.pa.tinypack.model.fsm.GameManager;
import pt.isec.pa.tinypack.ui.gui.MainJFX;
import pt.isec.pa.tinypack.ui.text.UserInterface;

import java.io.IOException;

public class Main {

    public static GameManager model = null;
    public static void main(String[] args) throws IOException, InterruptedException {
        //System.out.println("Hello world!");

        model = new GameManager();
        Application.launch(MainJFX.class,args);

        //Context cxt = new Context();
        //UserInterface ui = new UserInterface(cxt);
        //ui.display();



    }
}




/**
 *
 * estou a testar java docs :)))
 * @diogo
 *
 */