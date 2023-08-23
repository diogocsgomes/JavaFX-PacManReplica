package pt.isec.pa.tinypack.model.fsm;

//import com.googlecode.lanterna.input.KeyType;
import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.PacManDirections;
import pt.isec.pa.tinypack.model.data.mazeElements.PacMan;
//import com.googlecode.lanterna.input;

public abstract class GameStateAdapter implements  IGameState{

    protected final GameData GameEntities;
    //protected KeyType keyType;

    protected Context context;


    protected static int level = 1;
    protected  final int maxLevel = 2;








    public GameStateAdapter(GameData GameEntities, Context context)
    {
        this.GameEntities = GameEntities;
        this.context = context;
    }



    @Override
    public boolean getInput(PacManDirections input) {
        return false;
    }

    @Override
    public GameState getState() {
        return null;
    }

    @Override
    public String getStateAsString(){
        return "State Adapter";
    }


   /* @Override
    public Ghost getGhost(){

    }
    */
    public PacMan getPacMan(){
        return GameEntities.getPacMan();
    }


    public char[][] getOutput(){
        return GameEntities.getBoard();
    }


    @Override
    public void menu(){

    }
    @Override
    public void retomar(){

    }

    @Override
    public void processTick(){

    }

}
