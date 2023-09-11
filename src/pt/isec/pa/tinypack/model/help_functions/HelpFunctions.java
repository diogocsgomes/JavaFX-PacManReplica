package pt.isec.pa.tinypack.model.help_functions;

import pt.isec.pa.tinypack.model.data.PacManDirections;
import pt.isec.pa.tinypack.model.data.mazeElements.PacMan;
import pt.isec.pa.tinypack.model.data.mazeElements.VoidSpace;
import pt.isec.pa.tinypack.model.fsm.*;

public abstract class HelpFunctions {

    public static boolean detectGhost(int y,int x,char [][] board){
        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;

        return board[y][x] == 'B' || board[y][x] == 'I' || board[y][x] == 'P' || board[y][x] == 'C';
    }

    public static boolean detectPowerBall(int y,int x,char [][] board){
        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;
        return board[y][x] == 'O';
    }

    public static boolean detectFruit(int y,int x,char [][] board){
        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;
        return board[y][x] == 'F';
    }

    public static boolean detectBall(int y,int x,char [][] board){
        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;

        return board[y][x] == 'o';
    }

    public static boolean detectVoid(int y,int x,char [][] board)
    {
        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;
        return board[y][x] == ' ';
    }


    public static boolean detectWallORGhostPortal(int y, int x, char [][] board){ /** Informar à função as posições a verificar */

        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;

        return board[y][x] == 'x' || board[y][x] == 'Y';
    }


    public static boolean detectWall(int y, int x, char [][] board){ /** Informar à função as posições a verificar */

        if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;

        return board[y][x] == 'x';
    }

    public static boolean detectWarp(int y,int x,char [][] board,GameData gameEntities){
        //Não confundir com processWarp esta funcao é unicamente responsavel por detetar um warp
        if(y < 0 || y >= gameEntities.getNum_linhas() || x < 0 || x >= gameEntities.getNum_colunas())
            return false;
        /*if(y < 0 || y >= board.length  || x < 0 || x >= board[0].length )
            return false;

         */

        return board[y][x] == 'W';
    }


    public static void eatBall(PacManDirections type, GameData GameEntities){

        GameEntities.setNumberBalls(GameEntities.getNumberBalls() - 1 );
        //GameEntities.setPontos(GameEntities.getPontos() + 1);
        GameEntities.addPontos(1);

    }

    public static void eatPowerBall(GameData GameEntities){

        GameEntities.setNumberPowerBalls(GameEntities.getNumberPowerBalls() - 1 );
        //GameEntities.setPontos(GameEntities.getPontos() + 10);
        GameEntities.addPontos( 10);



    }


    public static void eatGhost(int num_ghosts,GameData GameEntities){
        //GameEntities.setPontos(GameEntities.getPontos() + 50 * num_ghosts);
        GameEntities.addPontos( 50 * num_ghosts);
    }

    public  static void processWarp(GameData GameEntities){
        //Nao confundir com detectWarp, esta funcao é unicamente responsavel pela logica de entrar no warp
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
                        && (y != CordPacY || x != CordPacx - 1) && (y != CordPacY || x != CordPacx + 1)) // verifica se é a outra zona Warp
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
                        //return GameEntities.getBoard();


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
                        //return GameEntities.getBoard();

                    }


                    //DIREITA
                    if (!HelpFunctions.detectWallORGhostPortal(y, x + 1, board)) // se for para a direita , por outras palavras, se para direita form uma direcao valida
                    {
                        System.out.println("Vim aqui o meu x é " + x);
                        if (HelpFunctions.detectGhost(y, x + 1, board)) {
                            //super.context.changeState(GameState.PAC_MAN_DEAD);
                            System.out.println("PacManMorreu");
                        }

                        GameEntities.getMaze().set(y, x + 1, GameEntities.getPacMan()); //Pacman avanca uma casa
                        //GameEntities.getMaze().set(y,x, new Warp());
                        GameEntities.getMaze().set(CordPacY,CordPacx,new VoidSpace());
                        GameEntities.getPacMan().setY_x(y, x + 1);
                        GameEntities.getPacMan().setDirection(PacManDirections.RIGHT);
                        //return GameEntities.getBoard();
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
                        GameEntities.getPacMan().setDirection(PacManDirections.LEFT);
                        //return GameEntities.getBoard();
                    }

                    break board_loop;

                }
            }
        }
    }


    public static boolean evaluateNextDirection(char [][] board,GameData GameEntities){
        /**
         * Esta função tem como objetivo,avaliar se a mais recente direção intorduzida pelo jogador é possivel ser
         * seguida ou não.
         */

        PacManDirections nextDir = GameEntities.getPacMan().getNext_direction();
        PacMan pacMan = GameEntities.getPacMan();
        int CordPacY = GameEntities.getPacMan().getY_x()[0];
        int CordPacX = GameEntities.getPacMan().getY_x()[1];


        switch (nextDir) {
            case UP -> {
                if (CordPacY - 1 < 0)
                    return false; // O valor nao pode ser menor que 0 senaoo ocorre erro de indexação
                return !detectWallORGhostPortal(CordPacY - 1, CordPacX, board);
            }
            case DOWN -> {
                if (CordPacY + 1 > GameEntities.getNum_linhas())
                    return false;
                return !detectWallORGhostPortal(CordPacY + 1, CordPacX, board);
            }
            case LEFT -> {
                if (CordPacX - 1 > GameEntities.getNum_colunas())
                    return false;
                return !detectWallORGhostPortal(CordPacY, CordPacX - 1, board);
            }
            case RIGHT -> {
                if (CordPacX + 1 > GameEntities.getNum_colunas())
                    return false;
                return !detectWallORGhostPortal(CordPacY, CordPacX + 1, board);
            }
        }

        return false;
    }



    public static void eatFruit(GameData GameEntities){

        GameEntities.setAddToFruit(false);

        GameEntities.setFruitsEaten(GameEntities.getFruitsEaten() + 1);

        //GameEntities.setPontos(25 * GameEntities.getFruitsEaten());
        GameEntities.addPontos( 25 * GameEntities.getFruitsEaten());

        GameEntities.setPointsToFruit(0); //E necessario que esta instrucao veha antes da proxima para evitar excesso de pontos na variavel
        //points to fruit da classe GameData

        GameEntities.setAddToFruit(true);





    }








}
