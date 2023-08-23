package pt.isec.pa.tinypack.model.fsm;

//import com.googlecode.lanterna.input.KeyType;
import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.mazeElements.PacMan;

public interface IGameState {

    boolean getInput(KeyCode input);
    GameState getState();

     PacMan getPacMan();

     String getStateAsString();



    char[][] getOutput();

    void processTick();

    char[][] getGameMenu();
    void menu();

    void retomar();

    void evolve();
     
    // Maze Maze = null;

     //Ghost getGhost();


}
