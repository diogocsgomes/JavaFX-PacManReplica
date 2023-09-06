package pt.isec.pa.tinypack.model.fsm;
import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.GhostState;
import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.data.PacManDirections;
import pt.isec.pa.tinypack.model.fsm.states.OngoingGameState_PowerState;


public class Context {

    private IGameState state;



    private GameState prevState;




    private IMazeElement[][] board;


    private GameStateFactory stateFactory;

    private GameData gameData;

    private  GhostMovement ghostMovement;



    private boolean leave_game;



    /*public Context(PacMan PacMan, Blinky Blinky, Pinky Pinky, Inky Inky, Clyde Clyde, Maze Maze){
        //GameEntitiesContainer = new GameEntitiesContainer(PacMan, Blinky, Pinky, Inky, Clyde,Maze);
          GameEntities = GameEntitiesContainer.getInstance();



    }*/
    public Context(){
        //gameData = GameData.getInstance(this);

        gameData = new GameData(this);
        stateFactory = new GameStateFactory();

        ghostMovement = new GhostMovement(gameData);

        state = stateFactory.changeState(GameState.PRE_GAME, gameData,this,ghostMovement);

        leave_game = false;



    }


    public void setInput(PacManDirections input)
    {
        state.getInput(input);
    }


    GameState getState(){
        return state.getState();
    }

    public void changeState(GameState state)
    {

        prevState = getState();
        this.state = stateFactory.changeState(state, gameData,this,ghostMovement);
        //this.state = this.stateFactory.changeState(state,this.state.getPacMan(),this);
    }



    public char[][] getOutput(){

        //state.getEntities.getMaze.getBoard;
       // return this.GameEntities.getOutput(); //TEM QUE SER SUBSTTUIDO POR STATE.GETOUTPUT

        return this.state.getOutput();
    }



    public String getStateAsString(){
        return state.getStateAsString();
    }

    public char[][] getMenu()
    {
        return state.getGameMenu();
    }



    public GameStateFactory getStateFactory() {
        return stateFactory;
    }

    public GameData getGameData() {
        return gameData;
    }


    public IMazeElement[][] getElementMaze() {

        return gameData.getElementMaze();
    }

    public char[][] getCharMaze(){
        return gameData.getCharMaze();
    }

    public int getNumLinhas(){
        return gameData.getNum_linhas();
    }

    public int getNumColunas(){
        return gameData.getNum_colunas();
    }

    public int getMAX_NUM_LINH_AND_COL()
    {
        return  gameData.getMAX_NUM_LINH_AND_COL();
    }


    public  void menu()
    {
        state.menu();
    }

    public void retomar(){state.retomar();}


    public GameState getPrevState() {
        return prevState;
    }


    public void evolve(){
        /*if(state.getState() == GameState.ONGOING_GAME_POWER){
            ghostMovement.changeGhostState(GhostState.EDIBLE);
        }
        if(state.getState() == GameState.ONGOING_GAME)
        {
            ghostMovement.changeGhostState(GhostState.MOVING);
        }

         */

        //SUBIR DE NIVEL
        /*if(gameData.getNumberBalls() == 0 && gameData.getNumberPowerBalls() == 0)
        {
            //Subir de nivel
            if(gameData.getCurrent_level() == 2)
            {
                leave_game = true;
            }
            changeState(GameState.PRE_GAME);
            ghostMovement.changeGhostState(GhostState.IN_CAVE);
            gameData.changeBoard();
            return;


        }*/
        if(gameData.getNumberBalls() <= 0 && gameData.getNumberPowerBalls() <= 0)
        {
            /*if(ghostMovement.getInterval() > 200)
            {
                ghostMovement.setInterval(ghostMovement.getInterval() - 50);
            }

             */
            System.out.println("Nivel acabou");
            leave_game = true;
        }

        if(gameData.isPacManDead()) {
            ghostMovement.stop();
            //System.out.println("O pacman Morreu!!!");
            gameData.restateLevel();
            gameData.setLives_left(gameData.getLives_left() - 1);
            if(gameData.getLives_left() == 0){
                leave_game = true;
            }else {
                ghostMovement.changeGhostState(GhostState.IN_CAVE);
                changeState(GameState.PRE_GAME);
            }



        }
        gameData.eatFantom();
        state.evolve();


    }


    public PacManDirections getPacDir(){
        return gameData.getPacDir();
    }


    public int getPontos(){return gameData.getPontos();}


    public boolean isLeave_game() {
        return leave_game;
    }

    public void setLeave_game(boolean leave_game) {
        this.leave_game = leave_game;
    }


    public void stopGhostMovement()
    {
        ghostMovement.stop();
    }

    public int getLivesLeft(){
        return gameData.getLives_left();
    }




}
