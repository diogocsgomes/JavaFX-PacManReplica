package pt.isec.pa.tinypack.model.fsm.states;

import javafx.scene.input.KeyCode;
import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.data.PacManDirections;
import pt.isec.pa.tinypack.model.data.mazeElements.VoidSpace;
import pt.isec.pa.tinypack.model.fsm.*;
import pt.isec.pa.tinypack.model.help_functions.HelpFunctions;

import java.time.Instant;

import static pt.isec.pa.tinypack.model.data.PacManDirections.*;

public class FantomsDontMoveState extends GameStateAdapter {

    GameState state = GameState.FANTOMS_DONT_MOVE;

    private GhostMovement ghostMovement;

   // Instant instant;
    long start;
    long  stop;
    static long timer = 0;

    private int timeout = 5;

    public FantomsDontMoveState (GameData GameEntities, Context context,GhostMovement GhostMovement){
        super(GameEntities,context);
        ghostMovement = GhostMovement;
        //ghostMovement.start();



            start = Instant.now().getEpochSecond();
            timeout -= timer; // Reduz o timeout caso o jogador coma uma bola com poderes

        //System.out.println("O meu stop é" + stop);

    }



    @Override
    public boolean getInput(PacManDirections input){
        /*if(input != KeyCode.LEFT && input != KeyCode.RIGHT &&
                input != KeyCode.UP && input != KeyCode.DOWN)
        {
            return false;
        }

         */
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
        return "FANTOMS_DONT_MOVE";
    }

    @Override
    public char[][] getOutput(){
        System.out.println("Fui chamado");

        this.processTick();
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


                if ( ! HelpFunctions.detectWallORGhostPortal(CordPacY - 1, CordPacx, board))
                {


                    GameEntities.getMaze().set(CordPacY - 1, CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa

                    GameEntities.getMaze().set(CordPacY , CordPacx, new VoidSpace());

                    GameEntities.getPacMan().setY_x(CordPacY -1, CordPacx);

                }

                /*if (HelpFunctions.detectGhost(CordPacY - 1, CordPacx, board))
                {
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    //super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");

                }

                 */

                if(HelpFunctions.detectPowerBall(CordPacY - 1,CordPacx,board))
                {

                    HelpFunctions.eatPowerBall(GameEntities);


                    if(this.context.getGameData().getNumberBalls() == 0 && this.context.getGameData().getNumberPowerBalls() == 0) {
                        //this.context.changeState(GameState.LEVEL_TRANSITION);
                    }


                    else {
                       // System.out.println("Fiz isto");
                        this.context.changeState(GameState.ONGOING_GAME_POWER);

                    }

                }

                if(HelpFunctions.detectFruit(CordPacY - 1,CordPacx,board)){
                    HelpFunctions.eatFruit(GameEntities);
                }



                if(HelpFunctions.detectBall(CordPacY - 1,CordPacx,board)){
                    //System.out.println("Ball Detected");
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
                   // System.out.println("Estou no case ArrowDown e as cords sao: " + CordPacY + " " + CordPacx);
                    GameEntities.getMaze().set(CordPacY + 1, CordPacx, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY + 1, CordPacx);

                }

               /* if (HelpFunctions.detectGhost(CordPacY + 1, CordPacx, board)) {
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    //super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");

                }

                */
                if(HelpFunctions.detectPowerBall(CordPacY + 1,CordPacx,board))
                {

                    HelpFunctions.eatPowerBall(GameEntities);

                   // this.context.getGameData().setNumberPowerBalls(this.context.getGameData().getNumberPowerBalls() - 1 );

                    if(this.context.getGameData().getNumberBalls() == 0 && this.context.getGameData().getNumberPowerBalls() == 0) {
                        //this.context.changeState(GameState.LEVEL_TRANSITION);
                    }


                    else {
                        // System.out.println("Fiz isto");
                        this.context.changeState(GameState.ONGOING_GAME_POWER);

                    }

                }
                if(HelpFunctions.detectBall(CordPacY + 1,CordPacx,board)){
                    HelpFunctions.eatBall(direcao,GameEntities);
                    //this.eatBall(CordPacY + 1,CordPacx,direcao);
                }

                if(HelpFunctions.detectFruit(CordPacY + 1,CordPacx,board)){
                    HelpFunctions.eatFruit(GameEntities);
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
                    GameEntities.getMaze().set(CordPacY, CordPacx - 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY, CordPacx - 1);

                }

              /*  if (HelpFunctions.detectGhost(CordPacY, CordPacx - 1, board)) {
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    //super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");

                }

               */
                if(HelpFunctions.detectPowerBall(CordPacY ,CordPacx - 1,board))
                {

                    HelpFunctions.eatPowerBall(GameEntities);

                    //this.context.getGameData().setNumberPowerBalls(this.context.getGameData().getNumberPowerBalls() - 1 );

                    if(this.context.getGameData().getNumberBalls() == 0 && this.context.getGameData().getNumberPowerBalls() == 0) {
                        //this.context.changeState(GameState.LEVEL_TRANSITION);
                    }


                    else {
                        // System.out.println("Fiz isto");
                        this.context.changeState(GameState.ONGOING_GAME_POWER);

                    }

                }
                if(HelpFunctions.detectBall(CordPacY ,CordPacx - 1,board)){
                    HelpFunctions.eatBall(direcao,GameEntities);
                    //this.eatBall(CordPacY ,CordPacx - 1,direcao);
                }

                if(HelpFunctions.detectFruit(CordPacY ,CordPacx - 1,board)){
                    HelpFunctions.eatFruit(GameEntities);
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
                    GameEntities.getMaze().set(CordPacY, CordPacx + 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    GameEntities.getPacMan().setY_x(CordPacY, CordPacx + 1);

                }

               /* if (HelpFunctions.detectGhost(CordPacY, CordPacx + 1, board)) {
                    GameEntities.getMaze().set(CordPacY, CordPacx, new VoidSpace());
                    //super.context.changeState(GameState.PAC_MAN_DEAD);
                    System.out.println("PacManMorreu");

                }

                */
                if(HelpFunctions.detectPowerBall(CordPacY ,CordPacx + 1,board))
                {

                    HelpFunctions.eatPowerBall(GameEntities);


                    if(this.context.getGameData().getNumberBalls() == 0 && this.context.getGameData().getNumberPowerBalls() == 0) {
                        //this.context.changeState(GameState.LEVEL_TRANSITION);
                    }


                    else {
                        // System.out.println("Fiz isto");
                        this.context.changeState(GameState.ONGOING_GAME_POWER);

                    }

                }
                if(HelpFunctions.detectBall(CordPacY ,CordPacx + 1,board)){

                    HelpFunctions.eatBall(direcao,GameEntities);
                }

                if(HelpFunctions.detectFruit(CordPacY ,CordPacx + 1,board)){
                    HelpFunctions.eatFruit(GameEntities);
                }

                break;
        }
       /* try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("A Thread.sleep no movimento do pacaman nao correu bem");
        }

        */




        //return GameEntities.getBoard();
    }

    @Override
    public char[][] getGameMenu() {
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

        stop = Instant.now().getEpochSecond();
        timer =  stop - start;

        if(timer >= timeout){
            super.context.changeState(GameState.ONGOING_GAME);
        }
        this.processTick();
       // System.out.println("Direção: " + GameEntities.getPacMan().getDirection() );

        //System.out.println("O meu estado é:" + state.toString());
        //timer = timeout - timer; // So para puser dispor o tempo restante
        long aux = timeout - timer;
        System.out.println("Restam: " + aux);
        System.out.println("O meu estado é:" + state.toString());


    }


}


