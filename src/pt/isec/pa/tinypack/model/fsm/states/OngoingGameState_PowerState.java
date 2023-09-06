package pt.isec.pa.tinypack.model.fsm.states;

import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.*;
import pt.isec.pa.tinypack.model.data.mazeElements.VoidSpace;
import pt.isec.pa.tinypack.model.fsm.*;
import pt.isec.pa.tinypack.model.help_functions.HelpFunctions;

import java.time.Instant;

import static pt.isec.pa.tinypack.model.data.PacManDirections.*;


public class OngoingGameState_PowerState extends GameStateAdapter {

    GameState state = GameState.ONGOING_GAME_POWER;

    private GhostMovement ghostMovement;


    private int PowerTimer;

    //Instant instant;
    long start;
    long stop;

    long timer;

    private final int MAX_TIME = 5;
    private int timeout = MAX_TIME;

    private  int ghostsEaten = 0;

    public OngoingGameState_PowerState(GameData GameEntities, Context context,GhostMovement GhostMoevement){
        super(GameEntities,context);
        ghostMovement = GhostMoevement;

        start = Instant.now().getEpochSecond();

        PowerTimer = 10;
    }




    @Override
    public boolean getInput(PacManDirections input) {


        if(input == RIGHT || input == LEFT
                ||input == UP || input == DOWN) {

            GameEntities.getPacMan().setNext_direction(input);
            // super.GameEntities.getPacMan().setDirection(input);

            System.out.println("Diração do pacman: " +GameEntities.getPacMan().getDirection().toString() );


            return true;
        }
        System.out.println("Input rejeitado");
        return false;


    }

    @Override
    public GameState getState() {
        return state;
    }


    @Override
    public String getStateAsString(){
        return "ONGOING_GAME_POWER";
    }



    private char[][] detectWarp(){
        char[][] board = GameEntities.getBoard();
        PacManDirections direcao = GameEntities.getPacMan().getDirection();
        int CordPacY = GameEntities.getPacMan().getY_x()[0];
        int CordPacx = GameEntities.getPacMan().getY_x()[1];
        System.out.println("Warp detected at "+ (CordPacY  -1)  + CordPacx);
        int y = 0;
        int x = 0;
        //char[][] aux_board = super.context.GameEntities.getBoard();
        board_loop:
        for (y = 0; y < board.length; y++) {
            for (x = 0; x < board[0].length; x++) {

                //System.out.println("Trying to find the warp: " + y + " " + x);

                if (HelpFunctions.detectWarp(y, x, board,GameEntities) && (y != CordPacY - 1|| x != CordPacx) && (y != CordPacY + 1 || x != CordPacx)
                        && (y != CordPacY || x != CordPacx - 1) && (y != CordPacY || x != CordPacx + 1)) // verifica se e a outra zona Warp
                {
                    // System.out.println("Found the other warp: " + y + " " + x);
                    //E necessario verificar a direcao para a qual o pacman tem que seguir quando sair do warp
                    //CIMA
                    if (!HelpFunctions.detectWallORGhostPortal(y - 1, x, board)) // se for para CIMA , por outras palavras, se para cima form uma direcao valida
                    {
                        if (HelpFunctions.detectGhost(y - 1, x, board))
                        {
                            //super.context.changeState(GameState.PAC_MAN_DEAD);
                            System.out.println("PacManMorreu");
                        }

                        GameEntities.getMaze().set(y - 1, x, GameEntities.getPacMan()); //Pacman avanca uma casa
                        GameEntities.getMaze().set(CordPacY,CordPacx, new VoidSpace());
                        //GameEntities.getMaze().set(y,x, new Warp());
                        GameEntities.getPacMan().setY_x(y - 1, x);
                        GameEntities.getPacMan().setDirection(PacManDirections.UP);
                        return GameEntities.getBoard();


                        //HelpFunctions.warpManagement(y,x,GameEntities,board,context);

                    }


                    //BAIXO
                    if (!HelpFunctions.detectWallORGhostPortal(y + 1, x, board)) // se for para baixo , por outras palavras, se para baixo form uma direcao valida
                    {
                        System.out.println("Estou aqqui");
                        if (HelpFunctions.detectGhost(y + 1, x, board)) {
                            //super.context.changeState(GameState.PAC_MAN_DEAD);
                            System.out.println("PacManMorreu");
                        }
                        GameEntities.getMaze().set(y + 1, x, GameEntities.getPacMan()); //Pacman avanca uma casa
                        //GameEntities.getMaze().set(y,x, new Warp());
                        GameEntities.getMaze().set(CordPacY,CordPacx,new VoidSpace());
                        GameEntities.getPacMan().setY_x(y + 1, x);
                        //System.out
                        GameEntities.getPacMan().setDirection(PacManDirections.DOWN);
                        return GameEntities.getBoard();

                    }


                    //DIREITA
                    if (!HelpFunctions.detectWallORGhostPortal(y, x + 1, board)) // se for para a direita , por outras palavras, se para direita form uma direcao valida
                    {
                        if (HelpFunctions.detectGhost(y, x + 1, board)) {
                           // super.context.changeState(GameState.PAC_MAN_DEAD);
                            System.out.println("PacManMorreu");
                        }
                        GameEntities.getMaze().set(y, x + 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                        //GameEntities.getMaze().set(y,x, new Warp());
                        GameEntities.getMaze().set(CordPacY,CordPacx,new VoidSpace());
                        GameEntities.getPacMan().setY_x(y, x + 1);
                        GameEntities.getPacMan().setDirection(PacManDirections.RIGHT);
                        return GameEntities.getBoard();
                    }

                    //ESQUERDA
                    if (!HelpFunctions.detectWallORGhostPortal(y, x - 1, board)) // se for para a esquerda , por outras palavras, se para esquerda form uma direcao valida
                    {
                        if (HelpFunctions.detectGhost(y, x - 1, board)) {
                            //super.context.changeState(GameState.PAC_MAN_DEAD);
                            System.out.println("PacManMorreu");
                        }
                        GameEntities.getMaze().set(y, x - 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                        //GameEntities.getMaze().set(y,x, new Warp());
                        GameEntities.getMaze().set(CordPacY,CordPacx,new VoidSpace());
                        GameEntities.getPacMan().setY_x(y, x - 1);
                        GameEntities.getPacMan().setDirection(PacManDirections.RIGHT);
                        return GameEntities.getBoard();
                    }

                    break board_loop;

                }
            }
        }
        return GameEntities.getBoard();
    }



    @Override
    public void processTick(){
        char[][] board = GameEntities.getBoard();
        PacManDirections direcao = GameEntities.getPacMan().getDirection();
        int CordPacY = GameEntities.getPacMan().getY_x()[0];
        int CordPacx = GameEntities.getPacMan().getY_x()[1];

        //IMazeElement nextElement = GameEntities.getMaze().get(CordPacY - 1,CordPacx);
        IMazeElement nextElement = null;

        if(HelpFunctions.evaluateNextDirection(board, GameEntities))
        {
            GameEntities.getPacMan().setDirection(GameEntities.getPacMan().getNext_direction());
            direcao = GameEntities.getPacMan().getDirection();
        }


        switch (direcao) {
            case UP:
                //o PacMan move-se para cima
                //nextElement = GameEntities.getMaze().get(CordPacY - 1, CordPacx);


                if (HelpFunctions.detectWarp(CordPacY - 1, CordPacx, board,GameEntities)) {


                    HelpFunctions.processWarp(GameEntities);
                    return;
                    //return this.detectWarp();

                }


                if (!HelpFunctions.detectWallORGhostPortal(CordPacY - 1, CordPacx, board))
                {
                    if (HelpFunctions.detectGhost(CordPacY - 1, CordPacx, board))
                    {
                        GameEntities.getMaze().set(CordPacY - 1, CordPacx, new VoidSpace());
                        ghostsEaten++;
                        HelpFunctions.eatGhost(ghostsEaten,GameEntities);
                        //super.context.changeState(GameState.PAC_MAN_DEAD);


                    }else {
                        GameEntities.getMaze().set(CordPacY - 1, CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa

                        GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());

                        GameEntities.getPacMan().setY_x(CordPacY - 1, CordPacx);
                    }

                }

                if(HelpFunctions.detectPowerBall(CordPacY - 1,CordPacx,board))
                {
                    HelpFunctions.eatPowerBall(GameEntities);
                    timeout += timer;
                }


                if(HelpFunctions.detectBall(CordPacY - 1,CordPacx,board)){
                    HelpFunctions.eatBall(direcao,GameEntities);

                }


                break;





            case DOWN:
                //CordPacY = GameEntities.getPacMan().getY_x()[0];
                //CordPacx = GameEntities.getPacMan().getY_x()[1];
                //O PacMan move-se para baixo

                // nextElement = GameEntities.getMaze().get(CordPacY + 1, CordPacx);


                if (HelpFunctions.detectWarp(CordPacY + 1, CordPacx, board,GameEntities)) {

                    HelpFunctions.processWarp(GameEntities);
                    return;

                    //return  this.detectWarp();

                }
                if (!HelpFunctions.detectWallORGhostPortal(CordPacY + 1, CordPacx, board))
                {
                    if (HelpFunctions.detectGhost(CordPacY + 1, CordPacx, board)) {
                        GameEntities.getMaze().set(CordPacY + 1, CordPacx, new VoidSpace());
                        ghostsEaten++;
                        HelpFunctions.eatGhost(ghostsEaten,GameEntities);
                        //super.context.changeState(GameState.PAC_MAN_DEAD);

                    }else {// System.out.println("Estou no case ArrowDown e as cords sao: " + CordPacY + " " + CordPacx);
                        GameEntities.getMaze().set(CordPacY + 1, CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa
                        GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                        GameEntities.getPacMan().setY_x(CordPacY + 1, CordPacx);
                    }

                }

                if(HelpFunctions.detectPowerBall(CordPacY + 1,CordPacx,board))
                {
                    HelpFunctions.eatPowerBall(GameEntities);
                    timeout += timer;
                }
                if(HelpFunctions.detectBall(CordPacY + 1,CordPacx,board)){
                    HelpFunctions.eatBall(direcao,GameEntities);

                }
                break;

            case LEFT:
                //O PacMan move-se para a esquerda
                nextElement = GameEntities.getMaze().get(CordPacY, CordPacx - 1);


                if (HelpFunctions.detectWarp(CordPacY, CordPacx - 1, board,GameEntities)) {

                    HelpFunctions.processWarp(GameEntities);
                    return;

                    // return this.detectWarp();

                }
                if (!HelpFunctions.detectWallORGhostPortal(CordPacY, CordPacx - 1, board)) {

                    if (HelpFunctions.detectGhost(CordPacY, CordPacx - 1, board)) {
                        GameEntities.getMaze().set(CordPacY, CordPacx , new VoidSpace());
                        ghostsEaten++;
                        HelpFunctions.eatGhost(ghostsEaten,GameEntities);
                        //super.context.changeState(GameState.PAC_MAN_DEAD);

                    }
                    else {
                        GameEntities.getMaze().set(CordPacY, CordPacx - 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                        GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                        GameEntities.getPacMan().setY_x(CordPacY, CordPacx - 1);
                    }

                }

                if(HelpFunctions.detectPowerBall(CordPacY ,CordPacx - 1,board))
                {
                    HelpFunctions.eatPowerBall(GameEntities);
                    timeout += timer;
                }
                if(HelpFunctions.detectBall(CordPacY ,CordPacx - 1,board)){
                    HelpFunctions.eatBall(direcao,GameEntities);

                }
                break;

            case RIGHT:
                //O PacMan move-se para a direita
                nextElement = GameEntities.getMaze().get(CordPacY, CordPacx + 1);


                if (HelpFunctions.detectWarp(CordPacY, CordPacx + 1, board,GameEntities)) {
                    System.out.println("Detetei");
                    HelpFunctions.processWarp(GameEntities);
                    return;

                    //return this.detectWarp();

                }

                if (!HelpFunctions.detectWallORGhostPortal(CordPacY, CordPacx + 1, board)) {
                    if (HelpFunctions.detectGhost(CordPacY, CordPacx + 1, board)) {
                        GameEntities.getMaze().set(CordPacY, CordPacx + 1 , new VoidSpace());
                        ghostsEaten++;
                        HelpFunctions.eatGhost(ghostsEaten,GameEntities);
                        //super.context.changeState(GameState.PAC_MAN_DEAD);

                    }
                    else {
                        GameEntities.getMaze().set(CordPacY, CordPacx + 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                        GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                        GameEntities.getPacMan().setY_x(CordPacY, CordPacx + 1);
                    }

                }

                if(HelpFunctions.detectPowerBall(CordPacY ,CordPacx + 1,board))
                {
                    HelpFunctions.eatPowerBall(GameEntities);
                    timeout += timer;
                }
                if(HelpFunctions.detectBall(CordPacY ,CordPacx + 1,board)){

                    HelpFunctions.eatBall(direcao,GameEntities);

                }
                break;
        }

    }


    @Override
    public char[][] getOutput(){


        char[][] board = GameEntities.getBoard();
        PacManDirections direcao = GameEntities.getPacMan().getDirection();
        int CordPacY = GameEntities.getPacMan().getY_x()[0];
        int CordPacx = GameEntities.getPacMan().getY_x()[1];

        PowerTimer--;
        System.out.println("PoerTimer: " + PowerTimer);
        if (PowerTimer <= 0) {
            //this.context.changeState(GameState.LEVEL_TRANSITION);
            this.context.changeState(GameState.ONGOING_GAME);
            //this.context.state = new OngoingGameState(GameEntities,context);
            System.out.println("Muda para normal");
        }
        //IMazeElement nextElement = GameEntities.getMaze().get(CordPacY - 1,CordPacx);
        IMazeElement nextElement = null;
        switch (direcao) {
            case UP:
                //o PacMan move-se para cima
                nextElement = GameEntities.getMaze().get(CordPacY - 1, CordPacx);


                if (HelpFunctions.detectWarp(CordPacY - 1, CordPacx, board,GameEntities)) {
                    return this.detectWarp();

                }
                if (!HelpFunctions.detectWallORGhostPortal(CordPacY - 1, CordPacx, board))
                {
                    GameEntities.getMaze().set(CordPacY - 1, CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY - 1, CordPacx);

                }

                if (HelpFunctions.detectGhost(CordPacY - 1, CordPacx, board))
                {
                    this.eatGhost(CordPacY - 1,CordPacx,direcao);
                    /*GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");


                     */
                }

                if(HelpFunctions.detectPowerBall(CordPacY - 1,CordPacx,board))
                {

                    this.eatPowerBall(CordPacY - 1,CordPacx,direcao);

                }
                if(HelpFunctions.detectBall(CordPacY - 1,CordPacx,board)){
                    this.eatBall(CordPacY - 1,CordPacx,direcao);
                }


                break;





            case DOWN:
                //CordPacY = GameEntities.getPacMan().getY_x()[0];
                //CordPacx = GameEntities.getPacMan().getY_x()[1];
                //O PacMan move-se para baixo

                nextElement = GameEntities.getMaze().get(CordPacY + 1, CordPacx);


                if (HelpFunctions.detectWarp(CordPacY + 1, CordPacx, board,GameEntities)) {

                    return  this.detectWarp();

                }
                if (!HelpFunctions.detectWallORGhostPortal(CordPacY + 1, CordPacx, board))
                {
                    System.out.println("Estou no case ArrowDown e as cords sao: " + CordPacY + " " + CordPacx);
                    GameEntities.getMaze().set(CordPacY + 1, CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY + 1, CordPacx);

                }

                if (HelpFunctions.detectGhost(CordPacY + 1, CordPacx, board)) {
                    /*GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");

                     */
                    this.eatGhost(CordPacY + 1,CordPacx,direcao);

                }
                if(HelpFunctions.detectPowerBall(CordPacY + 1,CordPacx,board))
                {

                    this.eatPowerBall(CordPacY + 1,CordPacx,direcao);

                }
                if(HelpFunctions.detectBall(CordPacY + 1,CordPacx,board)){
                    this.eatBall(CordPacY + 1,CordPacx,direcao);
                }
                break;

            case LEFT:
                //O PacMan move-se para a esquerda
                nextElement = GameEntities.getMaze().get(CordPacY, CordPacx - 1);


                if (HelpFunctions.detectWarp(CordPacY, CordPacx - 1, board,GameEntities)) {

                    return this.detectWarp();

                }
                if (!HelpFunctions.detectWallORGhostPortal(CordPacY, CordPacx - 1, board)) {
                    GameEntities.getMaze().set(CordPacY, CordPacx - 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY, CordPacx - 1);

                }

                if (HelpFunctions.detectGhost(CordPacY, CordPacx - 1, board)) {
                    /*GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");

                     */
                    this.eatGhost(CordPacY ,CordPacx - 1,direcao);


                }
                if(HelpFunctions.detectPowerBall(CordPacY ,CordPacx - 1,board))
                {

                    this.eatPowerBall(CordPacY ,CordPacx - 1,direcao);

                }
                if(HelpFunctions.detectBall(CordPacY ,CordPacx - 1,board)){
                    this.eatBall(CordPacY ,CordPacx - 1,direcao);
                }
                break;

            case RIGHT:
                //O PacMan move-se para a esquerda
                nextElement = GameEntities.getMaze().get(CordPacY, CordPacx + 1);


                if (HelpFunctions.detectWarp(CordPacY, CordPacx + 1, board,GameEntities)) {
                    return this.detectWarp();

                }

                if (!HelpFunctions.detectWallORGhostPortal(CordPacY, CordPacx + 1, board)) {
                    GameEntities.getMaze().set(CordPacY, CordPacx + 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY, CordPacx + 1);

                }

                if (HelpFunctions.detectGhost(CordPacY, CordPacx + 1, board)) {
                   /* GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");*/
                    this.eatGhost(CordPacY ,CordPacx + 1,direcao);


                }
                if(HelpFunctions.detectPowerBall(CordPacY ,CordPacx + 1,board))
                {

                    this.eatPowerBall(CordPacY ,CordPacx + 1,direcao);

                }
                if(HelpFunctions.detectBall(CordPacY ,CordPacx + 1,board)){

                    this.eatBall(CordPacY ,CordPacx + 1,direcao);
                }
                break;
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("A Thread.sleep no movimento do pacaman nao correu bem");
        }




        return GameEntities.getBoard();
    }


    private void eatPowerBall(int CordPacY,int CordPacx,PacManDirections type){//os argumentos sao as psicoes para quis o pacman deve ir
        GameEntities.getMaze().set(CordPacY , CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa

        switch (type)
        {
            case UP:
                GameEntities.getMaze().set(CordPacY + 1, CordPacx, new VoidSpace());
                break;

            case DOWN:
                GameEntities.getMaze().set(CordPacY - 1, CordPacx, new VoidSpace());
                break;

            case LEFT:
                GameEntities.getMaze().set(CordPacY, CordPacx + 1, new VoidSpace());
                break;

            case  RIGHT:
                GameEntities.getMaze().set(CordPacY, CordPacx - 1, new VoidSpace());
                break;

        }

        GameEntities.getPacMan().setY_x(CordPacY , CordPacx);

        this.context.getGameData().setNumberPowerBalls(this.context.getGameData().getNumberPowerBalls() - 1 );

        if(this.context.getGameData().getNumberBalls() == 0 && this.context.getGameData().getNumberPowerBalls() == 0) {
            //this.context.changeState(GameState.LEVEL_TRANSITION);
        }
        else {
            this.context.changeState(GameState.ONGOING_GAME_POWER);
        }
    }

    private void eatBall(int CordPacY,int CordPacx,PacManDirections type){
        GameEntities.getMaze().set(CordPacY , CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa
        switch (type)
        {
            case UP:
                GameEntities.getMaze().set(CordPacY + 1, CordPacx, new VoidSpace());
                break;

            case DOWN:
                GameEntities.getMaze().set(CordPacY - 1, CordPacx, new VoidSpace());
                break;

            case LEFT:
                GameEntities.getMaze().set(CordPacY, CordPacx + 1, new VoidSpace());
                break;

            case  RIGHT:
                GameEntities.getMaze().set(CordPacY, CordPacx - 1, new VoidSpace());
                break;

        }
        GameEntities.getPacMan().setY_x(CordPacY , CordPacx);

        this.context.getGameData().setNumberBalls(this.context.getGameData().getNumberBalls() - 1 );
        System.out.println("NUmber of baalls" +this.context.getGameData().getNumberBalls() );
        if(this.context.getGameData().getNumberBalls() == 0 && this.context.getGameData().getNumberPowerBalls() == 0) {
            //this.context.changeState(GameState.LEVEL_TRANSITION);
        }

    }

    private void eatGhost(int CordPacY,int CordPacx,PacManDirections type){
        GameEntities.getMaze().set(CordPacY , CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa
        switch (type)
        {
            case UP:
                GameEntities.getMaze().set(CordPacY + 1, CordPacx, new VoidSpace());
                break;

            case DOWN:
                GameEntities.getMaze().set(CordPacY - 1, CordPacx, new VoidSpace());
                break;

            case LEFT:
                GameEntities.getMaze().set(CordPacY, CordPacx + 1, new VoidSpace());
                break;

            case  RIGHT:
                GameEntities.getMaze().set(CordPacY, CordPacx - 1, new VoidSpace());
                break;

        }
        GameEntities.getPacMan().setY_x(CordPacY , CordPacx);
    }


    public int getPowerTimer() {
        return PowerTimer;
    }

    public void setPowerTimer(int powerTimer) {
        PowerTimer = powerTimer;
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
        //getOutput();
        stop = Instant.now().getEpochSecond();
        timer = stop - start;

        if(timer >= timeout){
            context.changeState(context.getPrevState());
        }
        this.processTick();
        //System.out.println("O meu estado é:" + state.toString());
        long timer = timeout -(stop -start);
        System.out.println("Tempo restante" +  timer);
        System.out.println("O meu estado é:" + state.toString());

    }



}
