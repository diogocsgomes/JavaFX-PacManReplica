package pt.isec.pa.tinypack.model.fsm;
import pt.isec.pa.tinypack.model.data.*;
import pt.isec.pa.tinypack.model.data.mazeElements.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameData {


    private static GameData instance = null;

    Context context;

    private PacMan PacMan;
    //Ghost[] ghosts;
    private Blinky Blinky;
    private Pinky Pinky;
    private Inky Inky;
    private Clyde Clyde;

    private SpawnPoint spawnPoint;

    private Fruit fruit;



    private Maze Maze;
    private ArrayList<String> tab_aux ;
    private String aux;



    private int num_linhas;
    private int num_colunas;

    /**
     * O tabuleiro de jogo irá ter 500 pixeis de altura e 600 de largura,
     * logo para que as dimensoes dos objetos continuem a ter um tamanho adequado vou pre-definir um valor maximo de linhas
     * e colunas.
     * O intuito é fazer com que o tamanho minimo de um quadrado seja de 10x10 pixeis
     */

    private final int MAX_NUM_LINH = 50;
    private final int MAX_NUM_COL = 50;


    private int numberBalls = 0;



    private int numberPowerBalls = 0;



    private int ghostPortalX; //linha
    private int ghostPortalY;//coluna




    private int pontos;



    private int pointsToFruit;



    private int fruitsEaten;

    private boolean addToFruit;


    private final int N_LIVES = 3;



    private int lives_left;



    private int current_level = 1;



    public GameData(Context context){
        this.context = context;
        pontos = 0;
        pointsToFruit = 0;
        fruitsEaten = 0;
        addToFruit = false;
        this.PacMan = pt.isec.pa.tinypack.model.data.mazeElements.PacMan.getInstance();
        this.Blinky = new Blinky();
        this.Pinky = new Pinky();
        this.Inky = new Inky();
        this.Clyde = new Clyde();

        lives_left = N_LIVES;

        //this.spawnPoint = new SpawnPoint();
        //int x ;
        //int y ;
        tab_aux = new ArrayList<>();

        try{


            FileReader obj = new FileReader("Level01.txt");
            BufferedReader reader = new BufferedReader(obj);

            //Scanner reader = new Scanner(obj);

            while ((aux =reader.readLine()) != null)
            {
                tab_aux.add(aux);
              //  System.out.println(aux);
            }

            //System.out.println(tab_aux.toString() + "\n" + tab_aux.size() + tab_aux.get(0).length());
            //System.out.println(tab_aux.size() + " " + tab_aux.get(0).length());

        }catch (IOException e){
            System.out.println("Ocorreu umm erro na leitura do ficheiro Level01.txt");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            num_linhas = tab_aux.size(); // numero de linhas aka height
            num_colunas = tab_aux.get(0).length();// numero de colunas aka width

            if (num_linhas > MAX_NUM_LINH)
                throw new Exception("Numero de linhas superior ao limite estipulado");

            if(num_colunas > MAX_NUM_COL)
                throw new Exception("Numero de colunas superior ao limite estipulado");


        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        this.Maze = new Maze(num_linhas,num_colunas);
        instance = this;


        InitiateBoard();
    }


    public void InitiateBoard(){
        int x,y;
        int ball_count = 0;
        int pBall_count =  0;
        x = num_linhas; // numero de linhas aka height
        y = num_colunas;// numero de colunas aka width
        System.out.println("Numeor de linhas: " + x + "\nNumero de colunas: " + y);

        if(x <= 0 || y <= 0)
        {
            System.out.println("Something went worng Initiating the board");
            return;
        }

        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                char Char = tab_aux.get(i).charAt(j);

                if(Char == 'o')
                    ball_count++;
                if (Char == 'O')
                    pBall_count++;
                switch (Char) {
                    case 'x' -> this.Maze.set(i, j, new Wall());
                    case 'W' -> this.Maze.set(i, j, new Warp());
                    case 'o'-> this.Maze.set(i,j,new Ball());
                    case 'F'-> {
                        fruit = new Fruit(i,j);
                        this.Maze.set(i, j, fruit);
                    }
                    case 'M'-> {
                        spawnPoint = new SpawnPoint(j,i);
                        this.Maze.set(i, j,spawnPoint);
                    }
                    case 'O' -> this.Maze.set(i,j,new PowerBall());
                    case 'Y' -> {
                        this.Maze.set(i,j, new GhostPortal());
                        setGhostPortalX(j);
                        setGhostPortalY(i);
                    }
                    case 'y' -> this.Maze.set(i,j, new GhostCave());
                    case 'B' ->this.Maze.set(i,j,new Blinky());//Para testes
                    case 'I'-> this.Maze.set(i,j,new Inky());
                    case 'C' -> this.Maze.set(i,j,new Clyde());
                    case 'P' -> this.Maze.set(i,j,new Pinky());
                    case ' ' -> this.Maze.set(i,j,new VoidSpace());

                    default -> throw new IllegalArgumentException("Caracter Invalido: " + Char);
                }

            }
        }

        this.numberBalls = ball_count;
        this.numberPowerBalls = pBall_count;
        detectAndSetCorners(num_linhas,num_colunas,getBoard());


    }


    public void changeBoard(){
       /* this.Blinky = new Blinky();
        this.Pinky = new Pinky();
        this.Inky = new Inky();
        this.Clyde = new Clyde();
        this.spawnPoint = new SpawnPoint();
        */
        //int x ;

        //int y ;
        //Incrementa o nivel
        current_level++;
        tab_aux = new ArrayList<>();
        String levelTemplate;
        //String levelTemplate = "Level0";
        if(current_level < 10)
        {
            levelTemplate = "Level0";
        }
        else {
            levelTemplate = "Level";
        }
        try{

            FileReader obj = new FileReader(levelTemplate + current_level +".txt");
            BufferedReader reader = new BufferedReader(obj);

            //Scanner reader = new Scanner(obj);

            while ((aux =reader.readLine()) != null)
            {
                tab_aux.add(aux);
                //  System.out.println(aux);
            }

            //System.out.println(tab_aux.toString() + "\n" + tab_aux.size() + tab_aux.get(0).length());
            //System.out.println(tab_aux.size() + " " + tab_aux.get(0).length());

        }catch (IOException e){
            System.out.println("Ocorreu umm erro na leitura do ficheiro Level01.txt");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            num_linhas = tab_aux.size(); // numero de linhas aka wight
            num_colunas = tab_aux.get(0).length();// numero de colunas aka width

            if (num_linhas > MAX_NUM_LINH)
                throw new Exception("Numero de linhas superior ao limite estipulado");

            if(num_colunas > MAX_NUM_COL)
                throw new Exception("Numero de colunas superior ao limite estipulado");


        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        this.Maze = new Maze(num_linhas,num_colunas);
        instance = this;


        InitiateBoard();
    }

    private void detectAndSetCorners(int num_linhas,int num_colunas,char[][] board){

       //Superior esquerdo
        Superior_esquerdo:
       for(int i = 0; i < num_linhas; i++)
       {
           for(int j = 0; j < num_colunas; j ++ )
           {
               if(board[i][j] != 'x')
               {
                   Pinky.setUpLeft(j,i);
                   Inky.setUpLeft(j,i);
                   System.out.println("Superior esquerdo: " + i + " " + j);
                   break Superior_esquerdo;
               }
           }
       }

       //Sperior direito
        SuperiorDireito:
        for(int i = 0; i < num_linhas; i++)
        {
            for(int j = num_colunas - 1; j >= 0; j-- )
            {
                if(board[i][j] != 'x')
                {
                    Pinky.setUpRight(j,i);
                    Inky.setUpRight(j,i);
                    System.out.println("Superior direito: " + i + " " + j);
                    break SuperiorDireito;
                }
            }
        }


        //Inferior esquerdo
        InferiorEsquerdo:
        for(int i = num_linhas - 1; i >= 0; i--)
        {
            for(int j = 0; j < num_colunas; j ++ )
            {
                if(board[i][j] != 'x')
                {
                    Pinky.setDownLeft(j,i);
                    Inky.setDownLeft(j,i);
                    System.out.println("Inferior esquerdo: " + i + " " + j);
                    break InferiorEsquerdo;
                }
            }
        }

        //Inferior Direito
        InferiorDireito:
        for(int i = num_linhas - 1; i >= 0; i--)
        {
            for(int j = num_colunas - 1; j >= 0; j--)
            {
                if(board[i][j] != 'x')
                {
                    Pinky.setDownRight(j,i);
                    Inky.setDownRight(j,i);
                    System.out.println("Inferior esquerdo: " + i + " " + j);
                    break InferiorDireito;
                }
            }
        }


    }


   /* public static GameData getInstance(Context context){
        if (instance == null) {

            return new GameData(context);
        }

        return null;
    }
    */



    public SpawnPoint getSpawnPoint() {
        return spawnPoint;
    }
    public PacMan getPacMan()
    {
        return this.PacMan;
    }

    public Blinky getBlinky()
    {
        return this.Blinky;
    }

    public Pinky getPinky(){
        return this.Pinky;
    }

    public Inky getInky(){
        return this.Inky;
    }

    public Clyde getClyde(){
        return this.Clyde;
    }


    public Maze getMaze() {
        return Maze;
    }

    public char[][] getBoard(){
        return this.Maze.getMaze();
    }

    public int getNumberBalls() {
        return numberBalls;
    }
    public int getNumberPowerBalls() {
        return numberPowerBalls;
    }


    public  int getMAX_NUM_LINH_AND_COL(){ //Visto que o numero maximo e o memso nao e perciso fazer dois getters
        return MAX_NUM_LINH;
    }
    public void setNumberPowerBalls(int numberPowerBalls) {
        this.numberPowerBalls = numberPowerBalls;
    }
    public void setNumberBalls(int numberBalls) {
        this.numberBalls = numberBalls;
    }




    public int getNum_linhas() {
        return num_linhas;
    }

    public int getNum_colunas() {
        return num_colunas;
    }


    public IMazeElement[][] getElementMaze() {

        IMazeElement[][] board = new IMazeElement[num_linhas][num_colunas];

        for(int i = 0; i < num_linhas; i++)
        {
            for(int j = 0; j < num_colunas; j++)
            {
               board[i][j] = Maze.get(j,i);
            }
        }

        return board;

    }

    public char[][] getCharMaze(){
        return Maze.getMaze();
    }



    public PacManDirections getPacDir(){
        return PacMan.getDirection();
    }


    public int getGhostPortalX() {
        return ghostPortalX;
    }

    public void setGhostPortalX(int ghostPortalX) {
        this.ghostPortalX = ghostPortalX;
    }

    public int getGhostPortalY() {
        return ghostPortalY;
    }

    public void setGhostPortalY(int ghostPortalY) {
        this.ghostPortalY = ghostPortalY;
    }

    public boolean isPacManDead(){
        if(Blinky.getPreviousElement() == null && Inky.getPreviousElement() == null &&
                Pinky.getPreviousElement() == null && Clyde.getPreviousElement() == null)
        {
            return false;
        }

       /* if(context.getState() == GameState.ONGOING_GAME &&
                ( Blinky.getY() == PacMan.getY() && Blinky.getX() == PacMan.getX() ) ||
                ( Pinky.getY() == PacMan.getY() && Pinky.getX() == PacMan.getX() ) ||
                (Clyde.getY() == PacMan.getY() && Clyde.getX() == PacMan.getX() ) ||
                (Inky.getX() == PacMan.getX() && Inky.getY() == PacMan.getY()))
        {
            System.out.println("O pacman morreu no estado " +context.getState() );
            return true;
        }

        */
        if (context.getState() == GameState.ONGOING_GAME && (
                ( Blinky.getY() == PacMan.getY() && Blinky.getX() == PacMan.getX() ) ||
                        ( Pinky.getY() == PacMan.getY() && Pinky.getX() == PacMan.getX() ) ||
                        (Clyde.getY() == PacMan.getY() && Clyde.getX() == PacMan.getX() ) ||
                        (Inky.getX() == PacMan.getX() && Inky.getY() == PacMan.getY()))
        ) {
            System.out.println("O pacman morreu no estado " +context.getState() );


            return true;
        }
        return false;
    }

    public void restateLevel(){
        /**
         * Nesta funcao o tabuleiro é resposto
         */

        //Retirar os fantasmas de onde eles estavam
            System.out.println("Restate level");
            Maze.set(Blinky.getY(),Blinky.getX(),new VoidSpace());
            Maze.set(Pinky.getY(),Pinky.getX(),new VoidSpace());
            Maze.set(Clyde.getY(),Clyde.getX(),new VoidSpace());
            Maze.set(Inky.getY(),Inky.getX(),new VoidSpace());
            //Definir valores base para os fantasmas

        Blinky.setX(-1);
        Blinky.setY(-1);

        Pinky.setX(-1);
        Pinky.setY(-1);

        Clyde.setX(-1);
        Clyde.setY(-1);

        Inky.setX(-1);
        Inky.setY(-1);









    }


    public void eatFantom(){
        if (context.getState() == GameState.ONGOING_GAME_POWER && (
                ( Blinky.getY() == PacMan.getY() && Blinky.getX() == PacMan.getX() ) ||
                        ( Pinky.getY() == PacMan.getY() && Pinky.getX() == PacMan.getX() ) ||
                        (Clyde.getY() == PacMan.getY() && Clyde.getX() == PacMan.getX() ) ||
                        (Inky.getX() == PacMan.getX() && Inky.getY() == PacMan.getY()))
        ) {

            if(Blinky.getY() == PacMan.getY() && Blinky.getX() == PacMan.getX() )
            {
                Blinky.setX(-1);
                Blinky.setY(-1);
                Blinky.setState(GhostState.IN_CAVE);
            }

            if(Pinky.getY() == PacMan.getY() && Pinky.getX() == PacMan.getX())
            {
                Pinky.setX(-1);
                Pinky.setY(-1);
                Pinky.setState(GhostState.IN_CAVE);
            }

            if(Clyde.getY() == PacMan.getY() && Clyde.getX() == PacMan.getX())
            {
                Clyde.setX(-1);
                Clyde.setY(-1);
                Clyde.setState(GhostState.IN_CAVE);
            }
            if(Inky.getX() == PacMan.getX() && Inky.getY() == PacMan.getY())
            {
                Inky.setX(-1);
                Inky.setY(-1);
                Inky.setState(GhostState.IN_CAVE);
            }

        }
    }




    public int getPontos() {
        return pontos;
    }

    public void addPontos(int pointsToAdd) {


        this.pontos += pointsToAdd;

        if(addToFruit)
        {
            pointsToFruit +=pointsToAdd;
            if(pointsToFruit >= 20) {
                setFruitInMaze();
                setPointsToFruit(0);

            }
        }


    }


    public void setFruitInMaze(){
        while ( ( Blinky.getY() == fruit.getY() && Blinky.getX() == fruit.getX() ) ||
                ( Pinky.getY() == fruit.getY() && Pinky.getX() == fruit.getX() ) ||
                (Clyde.getY() == fruit.getY() && Clyde.getX() == fruit.getX() ) ||
                (Inky.getX() == fruit.getX() && Inky.getY() == fruit.getY()) ||
                (PacMan.getX() == fruit.getX() && PacMan.getY() == fruit.getY()))
        {
            ;//Espera que nao esteja ninguem nas cordenadas da fruta
        }

        Maze.set(fruit.getX(), fruit.getY(), fruit);

    }


    public int getPointsToFruit() {
        return pointsToFruit;
    }

    public void setPointsToFruit(int pointsToFruit) {
        this.pointsToFruit = pointsToFruit;
    }


    public boolean isAddToFruit() {
        return addToFruit;
    }

    public void setAddToFruit(boolean addToFruit) {
        this.addToFruit = addToFruit;
    }


    public int getFruitsEaten() {
        return fruitsEaten;
    }

    public void setFruitsEaten(int fruitsEaten) {
        this.fruitsEaten = fruitsEaten;
    }

    public int getLives_left() {
        return lives_left;
    }

    public void setLives_left(int lives_left) {
        this.lives_left = lives_left;
    }


    public int getCurrent_level() {
        return current_level;
    }



}
