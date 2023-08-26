package pt.isec.pa.tinypack.model.fsm;

import pt.isec.pa.tinypack.gameengine.GameEngine;
import pt.isec.pa.tinypack.gameengine.IGameEngine;
import pt.isec.pa.tinypack.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypack.model.data.GhostState;
import pt.isec.pa.tinypack.model.data.mazeElements.Blinky;
import pt.isec.pa.tinypack.model.data.mazeElements.Ghost;
import pt.isec.pa.tinypack.model.help_functions.HelpFunctions;

import java.util.Stack;

public class GhostMovement implements IGameEngineEvolve {

    private IGameEngine movingGhosts;
    private GameData gameData;

    Stack<Ghost> executeStack = new Stack<>();
    Stack<Ghost> auxStack = new Stack<>();

    Ghost[] GhostArray = new Ghost[4];


    public GhostMovement(GameData GameData){
        gameData = GameData;

        executeStack.push(gameData.getClyde());
        executeStack.push(gameData.getInky());
        executeStack.push(gameData.getPinky());
        executeStack.push(gameData.getBlinky());

        movingGhosts = new GameEngine();
        movingGhosts.registerClient(this);
    }




    public void changeGhostState(GhostState state){
        gameData.getClyde().setState(state);
        gameData.getInky().setState(state);
        gameData.getBlinky().setState(state);
        gameData.getPinky().setState(state);
    }




    public void start(){
        movingGhosts.start(250);
        //movingGhosts.start(500);
    }

    public void stop(){
        movingGhosts.stop();
    }

    public void pause(){
        movingGhosts.pause();
    }

    public void resume(){
        movingGhosts.resume();
    }



   public long getInterval(){
        return movingGhosts.getInterval();
   }
    public void setInterval(long newInterval){
        movingGhosts.setInterval(newInterval);
    }


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {


        //executa os elementos stack e coloca-os em outro stack
        if(executeStack.size() < 4)
        {
            executeStack.push(gameData.getClyde());
            executeStack.push(gameData.getInky());
            executeStack.push(gameData.getPinky());
            executeStack.push(gameData.getBlinky());
        }
        while (!executeStack.empty())
        {
            Ghost aux = executeStack.pop();
            aux.move(gameData);
            auxStack.push(aux);

        }
        //cooloca os elemetnos do 2 stack em um array
        for(int i = 0; i < 4; i++)
        {
            GhostArray[3- i] = auxStack.pop();
        }

        //Volta a colocar no stack principal os elementos pela ordem correta
        for(int i = 0; i < 4; i++)
        {
            executeStack.push(GhostArray[i]);
        }

       // System.out.println("Ghost movement");
    }
}
