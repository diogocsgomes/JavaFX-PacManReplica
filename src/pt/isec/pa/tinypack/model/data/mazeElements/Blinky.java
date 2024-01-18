package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.GhostDirections;
import pt.isec.pa.tinypack.model.data.GhostState;
import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.fsm.GameData;
import pt.isec.pa.tinypack.model.help_functions.HelpFunctions;

import java.util.ArrayList;
import java.util.Random;

public class Blinky extends Ghost{



    private GhostState state;



    private GhostDirections direction;

    private int x;//linha



    private int y;//coluna



    private IMazeElement previousElement;

    public Blinky(){

        state = GhostState.IN_CAVE;
        direction = GhostDirections.UP;
    }


    @Override
    public char getSymbol() {
        return 'B';
    }

    public Blinky getBlinky(){
        return this;
    }

    @Override
    public Blinky getGhost(){
        return this;
    }

    @Override
    public void move(GameData gameData){
        if(getState() == GhostState.IN_CAVE)
        {
            System.out.println("Fui chamado");
            setX(gameData.getGhostPortalX());
            setY(gameData.getGhostPortalY());

            int auxCordx = getX();
            int auxCordY = getY();
            if (!HelpFunctions.detectWallORGhostPortal(auxCordY - 1, auxCordx, gameData.getBoard()))
            {
                setPreviousElement(gameData.getMaze().get(auxCordY - 1, auxCordx));
                gameData.getMaze().set(auxCordY - 1, auxCordx, this); //Pacman avanca uma casa
                setX(auxCordx);
                setY(auxCordY - 1);
                setState(GhostState.MOVING);

                return;
            }
        }
        if(!detectWallAhead(gameData)){
            detectCrossing(gameData);
        }
        int auxCordx = getX();
        int auxCordY = getY();
        boolean sinAlteraciones = previousElement.getSymbol() != gameData.getPacMan().getSymbol();
        switch (direction){
            case UP -> {
                gameData.getMaze().set(auxCordY,auxCordx,previousElement);
                if(sinAlteraciones)
                    setPreviousElement(gameData.getMaze().get(auxCordY - 1 , auxCordx ));
                gameData.getMaze().set(auxCordY - 1, auxCordx, this);
                setX(auxCordx);
                setY(auxCordY - 1);
            }
            case DOWN -> {
                gameData.getMaze().set(auxCordY,auxCordx,previousElement);
                if(sinAlteraciones)
                    setPreviousElement(gameData.getMaze().get(auxCordY + 1 , auxCordx));
                gameData.getMaze().set(auxCordY + 1, auxCordx, this);
                setX(auxCordx);
                setY(auxCordY + 1);
            }
            case LEFT -> {
                gameData.getMaze().set(auxCordY,auxCordx,previousElement);
                if(sinAlteraciones)
                    setPreviousElement(gameData.getMaze().get(auxCordY , auxCordx -1));
                gameData.getMaze().set(auxCordY , auxCordx - 1, this);
                setX(auxCordx - 1);
                setY(auxCordY );
            }
            case RIGHT -> {
                gameData.getMaze().set(auxCordY,auxCordx,previousElement);
                if(sinAlteraciones)
                    setPreviousElement(gameData.getMaze().get(auxCordY , auxCordx + 1));
                gameData.getMaze().set(auxCordY , auxCordx + 1, this);
                setX(auxCordx + 1);
                setY(auxCordY );


            }
        }


    }


    private void detectCrossing(GameData gameData){
        /**
         * Esta função é chamada depois de a verificação da proxima casa ser umaparede ou nao logo so é perciso verificar
         * se a esquerda e a direita sao opções viaveis caso contario o fantasma volta para trás
         */

        switch (direction){
            case UP -> {
                ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                //possibleDirections.add(direction);
                //Condirmar esquerda e direita
                if(!HelpFunctions.detectWallORGhostPortal(getY() - 1,getX() , gameData.getBoard())){
                    possibleDirections.add(direction);
                }

                if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() - 1, gameData.getBoard())){
                    possibleDirections.add(GhostDirections.LEFT);
                }
                if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() + 1, gameData.getBoard())){
                    possibleDirections.add(GhostDirections.RIGHT);
                }

                if (possibleDirections.size() > 1) //Nao pode ir para a esquerda nem para a direita
                {
                    //Escolhe aleatorioamente uma das duas direções possiveis
                    Random rand = new Random();
                    int index = rand.nextInt(possibleDirections.size());
                    direction = possibleDirections.get(index);

                }
            }
            case DOWN -> {
                ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                if(!HelpFunctions.detectWallORGhostPortal(getY() + 1,getX() , gameData.getBoard())){
                    possibleDirections.add(direction);
                }
                //Condirmar esquerda e direita
                if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() + 1, gameData.getBoard())){
                    possibleDirections.add(GhostDirections.RIGHT);
                }
                if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() - 1, gameData.getBoard())){
                    possibleDirections.add(GhostDirections.LEFT);
                }

                if (possibleDirections.size() > 1) //Nao pode ir para a esquerda nem para a direita
                {
                    //Escolhe aleatorioamente uma das duas direções possiveis
                    Random rand = new Random();
                    int index = rand.nextInt(possibleDirections.size());
                    direction = possibleDirections.get(index);

                }
            }
            case LEFT -> {
                ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                if(!HelpFunctions.detectWallORGhostPortal(getY() ,getX() - 1 , gameData.getBoard())){
                    possibleDirections.add(direction);
                }
                //Condirmar esquerda e direita
                if(!HelpFunctions.detectWallORGhostPortal(getY() +1,getX() , gameData.getBoard())){
                    possibleDirections.add(GhostDirections.DOWN);
                }
                if(!HelpFunctions.detectWallORGhostPortal(getY() - 1,getX() , gameData.getBoard())){
                    possibleDirections.add(GhostDirections.UP);
                }

                if (possibleDirections.size() > 1) //Nao pode ir para a esquerda nem para a direita
                {
                    //Escolhe aleatorioamente uma das duas direções possiveis
                    Random rand = new Random();
                    int index = rand.nextInt(possibleDirections.size());
                    direction = possibleDirections.get(index);

                }
            }

            case RIGHT -> {

                ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                if(!HelpFunctions.detectWallORGhostPortal(getY() ,getX() + 1 , gameData.getBoard())){
                    possibleDirections.add(direction);
                }
                //Condirmar esquerda e direita
                if(!HelpFunctions.detectWallORGhostPortal(getY() - 1,getX() , gameData.getBoard())){
                    possibleDirections.add(GhostDirections.UP);
                }
                if(!HelpFunctions.detectWallORGhostPortal(getY() + 1,getX() , gameData.getBoard())){
                    possibleDirections.add(GhostDirections.DOWN);
                }

                if (possibleDirections.size() > 1) //Nao pode ir para a esquerda nem para a direita
                {
                    //Escolhe aleatorioamente uma das duas direções possiveis
                    Random rand = new Random();
                    int index = rand.nextInt(possibleDirections.size());
                    direction = possibleDirections.get(index);

                }
            }
        }


    }
    private boolean detectWallAhead(GameData gameData){
        switch (direction){
            case UP -> {

                //Verificar se aquilo que se encontra a sa frente é uma parede, se sim sortear nova direção
                if(HelpFunctions.detectWallORGhostPortal(getY() - 1,getX(), gameData.getBoard())){
                    ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                    //Condirmar esquerda e direita
                    if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() - 1, gameData.getBoard())){
                        possibleDirections.add(GhostDirections.LEFT);
                    }
                    if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() + 1, gameData.getBoard())){
                        possibleDirections.add(GhostDirections.RIGHT);
                    }

                    if (possibleDirections.size() == 0)
                    {
                        direction = GhostDirections.DOWN;//volta para trás
                        return true;
                    }else {
                        //Escolhe aleatorioamente uma das  direções possiveis
                        Random rand = new Random();
                        int index = rand.nextInt(possibleDirections.size());
                        direction = possibleDirections.get(index);
                        return true;
                    }

                }



            }

            case DOWN -> {

                if(HelpFunctions.detectWallORGhostPortal(getY() + 1,getX(), gameData.getBoard())){
                    ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                    //Condirmar esquerda e direita
                    if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() + 1, gameData.getBoard())){
                        possibleDirections.add(GhostDirections.RIGHT);
                    }
                    if(!HelpFunctions.detectWallORGhostPortal(getY(),getX() - 1, gameData.getBoard())){
                        possibleDirections.add(GhostDirections.LEFT);
                    }

                    if (possibleDirections.size() == 0)
                    {
                        direction = GhostDirections.UP;//volta para trás
                        return true;
                    }else {
                        //Escolhe aleatorioamente uma das duas direções possiveis
                        Random rand = new Random();
                        int index = rand.nextInt(possibleDirections.size());
                        direction = possibleDirections.get(index);
                        return true;
                    }

                }
            }

            case LEFT -> {
                if(HelpFunctions.detectWallORGhostPortal(getY() ,getX() - 1, gameData.getBoard())){
                    ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                    //Condirmar esquerda e direita
                    if(!HelpFunctions.detectWallORGhostPortal(getY() + 1,getX(), gameData.getBoard())){
                        possibleDirections.add(GhostDirections.DOWN);
                    }
                    if(!HelpFunctions.detectWallORGhostPortal(getY() - 1,getX(), gameData.getBoard())){
                        possibleDirections.add(GhostDirections.UP);
                    }

                    if (possibleDirections.size() == 0)
                    {
                        direction = GhostDirections.RIGHT;//volta para trás
                        return true;
                    }else {
                        //Escolhe aleatorioamente uma das duas direções possiveis
                        Random rand = new Random();
                        int index = rand.nextInt(possibleDirections.size());
                        direction = possibleDirections.get(index);
                        return true;
                    }

                }

            }
            case RIGHT -> {
                if(HelpFunctions.detectWallORGhostPortal(getY() ,getX() + 1, gameData.getBoard())){
                    ArrayList<GhostDirections> possibleDirections = new ArrayList<>();
                    //Condirmar esquerda e direita
                    if(!HelpFunctions.detectWallORGhostPortal(getY() - 1,getX(), gameData.getBoard())){
                        possibleDirections.add(GhostDirections.UP);
                    }
                    if(!HelpFunctions.detectWallORGhostPortal(getY() + 1,getX(), gameData.getBoard())){
                        possibleDirections.add(GhostDirections.DOWN);
                    }

                    if (possibleDirections.size() == 0)
                    {
                        direction = GhostDirections.LEFT;//volta para trás
                        return true;
                    }else {
                        //Escolhe aleatorioamente uma das duas direções possiveis
                        Random rand = new Random();
                        int index = rand.nextInt(possibleDirections.size());
                        direction = possibleDirections.get(index);
                        return true;
                    }

                }
            }
        }


        return false;
    }

    public GhostState getState() {
        return state;
    }

    public void setState(GhostState state) {
        this.state = state;
    }


    public GhostDirections getDirection() {
        return direction;
    }

    public void setDirection(GhostDirections direction) {
        this.direction = direction;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public IMazeElement getPreviousElement() {
        return previousElement;
    }

    public void setPreviousElement(IMazeElement previousElement) {
        this.previousElement = previousElement;
    }

    //...
}
