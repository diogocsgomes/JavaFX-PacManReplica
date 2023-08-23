package pt.isec.pa.tinypack.model.fsm.states;

//import com.googlecode.lanterna.input.KeyType;
import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.*;
import pt.isec.pa.tinypack.model.fsm.*;

import static pt.isec.pa.tinypack.model.data.PacManDirections.*;

public class PreGameState extends GameStateAdapter {


    GameState state = GameState.PRE_GAME;

    private GhostMovement ghostMovement;

    public PreGameState(GameData GameEntities, Context context,GhostMovement GhostMoevement){
        super(GameEntities,context);
        ghostMovement = GhostMoevement;
        ghostMovement.setInterval(500);


        GameEntities.getPacMan().setY_x(GameEntities.getSpawnPoint().getY(),GameEntities.getSpawnPoint().getX());//Definir as cordenadas do pacman
        GameEntities.getMaze().set(GameEntities.getSpawnPoint().getY(),GameEntities.getSpawnPoint().getX()
                ,GameEntities.getPacMan());//Instanciarr o pacman no tabuleiro

    }


    @Override
    public boolean getInput(PacManDirections input) {

       /* if(input == KeyType.F1)
        {
            this.context.changeState(GameState.GAME_PAUSED);

        }
        if(input == KeyType.F2)
        {
           // this.context.changeState(GameState.GAME_OVER);

        }

        */

        if(input != LEFT && input != RIGHT &&
                input != UP && input != DOWN)
        {
            return false;
        }



        IMazeElement previous,next;
        int y,x = 0; //cordenadas do pacman
        char[][] char_board = super.GameEntities.getBoard();

       /* Iteracao_linhas:
        for(y = 0; y < char_board.length;y++)//iterar as linhas representadas por y
        {
            for(x = 0; x < char_board[0].length; x++)
            {
                if(super.GameEntities.getMaze().get(y,x).getSymbol() == super.GameEntities.getSpawnPoint().getSymbol())
                {
                    break Iteracao_linhas;
                }
            }
        }*/

        y = GameEntities.getSpawnPoint().getY();
        x = GameEntities.getSpawnPoint().getX();



    /*    System.out.println("As cordenadas do pacman sao: " + x + y);
        GameEntities.getPacMan().setY_x(y,x);//Definir as cordenadas do pacman
        GameEntities.getMaze().set(y,x,GameEntities.getPacMan());//Instanciarr o pacman no tabuleiro
        GameEntities.getPacMan().setDirection(input);//indicar ao pacman qual é a sua direcao atravez do input
        GameEntities.getPacMan().setNext_direction(input);
       */
        GameEntities.getPacMan().setDirection(input);//indicar ao pacman qual é a sua direcao atravez do input
        GameEntities.getPacMan().setNext_direction(input);

        this.context.changeState(GameState.FANTOMS_DONT_MOVE);
        //super.context.stateFactory.changeState(GameState.ONGOING_GAME,super.GameEntities,super.context);


        return true;


    }

    @Override
    public GameState getState() {
        return state;
    }


    @Override
    public String getStateAsString(){
        return "PRE_GAME";
    }

    @Override
    public char[][] getOutput(){
        return GameEntities.getBoard();
    }

    @Override
    public char[][] getGameMenu(){
        String str1 = "f1 - Menu";
        String str2 = "f2 - Sair do jogo";

        char[][]aux = new char[2][str2.length()];
        aux[0] = str1.toCharArray();
        aux[1] = str2.toCharArray();

        return aux;
    }

    @Override
    public void menu()
    {
        context.changeState(GameState.GAME_PAUSED);
    }

    @Override
    public void evolve() {
        System.out.println("O meu estado é:" + state.toString());
    }



}
