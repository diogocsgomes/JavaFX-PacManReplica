package pt.isec.pa.tinypack.model.fsm.states;
//import com.googlecode.lanterna.input.KeyType;
import pt.isec.pa.tinypack.model.fsm.*;


public class GamePausedState extends GameStateAdapter {

    IGameState prev_state;
    GameState state = GameState.GAME_PAUSED;

    private GhostMovement ghostMovement;
    /*public GamePausedState(GameData GameEntities, Context context, IGameState prev){
        super(GameEntities,context);
        prev_state = prev;

    }

     */
    public GamePausedState(GameData GameEntities, Context context,GhostMovement GhostMovement){
        super(GameEntities,context);
        ghostMovement = GhostMovement;
        ghostMovement.pause();

    }


   /* @Override
    public boolean getInput(KeyType input) {
        if(input == KeyType.F1)
        {
            this.context.changeState(prev_state.getState());

        }
        /*if(input == KeyType.F2)
        {
            this.context.changeState(GameState.GAME_OVER);

        }


        return true;
    }
    */

    @Override
    public GameState getState() {
        return state;
    }


    @Override
    public String getStateAsString(){
        return "GAME_PAUSED";
    }

    @Override
    public char[][] getOutput(){
        return GameEntities.getBoard();
    }




    @Override
    public char[][] getGameMenu(){
        String str1 = "f1 - Retomar";
        String str2 = "f2 - Sair do jogo";

        char[][]aux = new char[2][str2.length()];
        aux[0] = str1.toCharArray();
        aux[1] = str2.toCharArray();

        return aux;
    }


    @Override
    public void retomar(){
        //context.changeState(GameState.ONGOING_GAME);
        //System.out.println("Vai voltar para" + context.getPrevState().toString());
        ghostMovement.resume();
        context.changeState(context.getPrevState());

    }

    @Override
    public void evolve() {
        System.out.println("O meu estado é:" + state.toString());
    }

}
