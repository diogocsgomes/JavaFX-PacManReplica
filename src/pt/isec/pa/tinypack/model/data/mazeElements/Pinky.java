package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.GhostDirections;
import pt.isec.pa.tinypack.model.data.GhostState;
import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.fsm.GameData;
import pt.isec.pa.tinypack.model.help_functions.HelpFunctions;

import java.util.ArrayList;
import java.util.Random;

public class Pinky extends Ghost {


    private GhostState state;



    private GhostDirections direction;

    private int x;//linha



    private int y;//coluna



    private IMazeElement previousElement;


    private enum NextCorner{
        UP_RIGHT,
        DOWN_RIGHT,
        UP_LEFT,
        DOWN_LEFT;
    }
    private class Corner{


        int cX;
        int cY;


        public int getcX() {
            return cX;
        }

        public void setcX(int cX) {
            this.cX = cX;
        }

        public int getcY() {
            return cY;
        }

        public void setcY(int cY) {
            this.cY = cY;
        }


    }

    private NextCorner nextCorner;



    private Corner upRight,downRight,upLeft,downLeft;



    public Pinky(){
        state = GhostState.IN_CAVE;
        direction = GhostDirections.UP;
        nextCorner = NextCorner.UP_RIGHT;


        upRight =   new Corner();
        upLeft =    new Corner();
        downLeft =  new Corner();
        downRight = new Corner();
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

    public Pinky getPinky(){
        return this;
    }


    @Override
    public void move(GameData gameData){


        //Blinky aux = gameData.getBlinky();


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

        checkNextConrner();

        if(!detectWallAhead(gameData)){
            detectCrossing(gameData);
        }


        int auxCordx = getX();
        int auxCordY = getY();
        switch (direction){
            case UP -> {
                //Mover para cima

                gameData.getMaze().set(auxCordY,auxCordx,previousElement);

                if(previousElement.getSymbol() != gameData.getPacMan().getSymbol())
                    setPreviousElement(gameData.getMaze().get(auxCordY - 1 , auxCordx ));//auxCordX e auxCordY nao sofrem alterações
                // aqui pq ja correspoondem as cordenadas antigas
                gameData.getMaze().set(auxCordY - 1, auxCordx, this); //Pacman avanca uma casa

                // gameData.getMaze().set(auxCordY - 1, auxCordx, this); //Pacman avanca uma casa


                setX(auxCordx);
                setY(auxCordY - 1);



            }

            case DOWN -> {
                //Mover para baixo

                gameData.getMaze().set(auxCordY,auxCordx,previousElement);


                if(previousElement.getSymbol() != gameData.getPacMan().getSymbol())
                    setPreviousElement(gameData.getMaze().get(auxCordY + 1 , auxCordx));//auxCordX e auxCordY nao sofrem alterações
                // aqui pq ja correspoondem as cordenadas antigas

                gameData.getMaze().set(auxCordY + 1, auxCordx, this); //Pacman avanca uma casa


                setX(auxCordx);
                setY(auxCordY + 1);

            }

            case LEFT -> {
                //Mover para a esquerda

                gameData.getMaze().set(auxCordY,auxCordx,previousElement);


                if(previousElement.getSymbol() != gameData.getPacMan().getSymbol())
                    setPreviousElement(gameData.getMaze().get(auxCordY , auxCordx -1));//auxCordX e auxCordY nao sofrem alterações
                // aqui pq ja correspoondem as cordenadas antigas
                gameData.getMaze().set(auxCordY , auxCordx - 1, this); //Pacman avanca uma casa


                setX(auxCordx - 1);
                setY(auxCordY );


            }
            case RIGHT -> {
                //Mover para a direita
                gameData.getMaze().set(auxCordY,auxCordx,previousElement);


                if(previousElement.getSymbol() != gameData.getPacMan().getSymbol())
                    setPreviousElement(gameData.getMaze().get(auxCordY , auxCordx + 1));//auxCordX e auxCordY nao sofrem alterações
                // aqui pq ja correspoondem as cordenadas antigas

                gameData.getMaze().set(auxCordY , auxCordx + 1, this); //Pacman avanca uma casa


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
                    direction = decideDirection(possibleDirections);

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
                    direction = decideDirection(possibleDirections);

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
                    direction = decideDirection(possibleDirections);

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
                    direction = decideDirection(possibleDirections);

                }
            }
        }


    }
    private boolean detectWallAhead(GameData gameData){
        int largura =  upRight.cX - upLeft.cX ;
        int altura = downLeft.cY - upLeft.cY;

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
                      direction = decideDirection(possibleDirections);
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
                        direction = decideDirection(possibleDirections);
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
                        direction = decideDirection(possibleDirections);
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
                        direction = decideDirection(possibleDirections);
                        return true;
                    }

                }
            }
        }


        return false;
    }

    private void checkNextConrner(){
        switch (nextCorner)
        {
            case UP_RIGHT -> {
                if(x == upRight.cX && y == upRight.cY) {
                    nextCorner = NextCorner.DOWN_RIGHT;
                }

            }
            case DOWN_RIGHT -> {

                if(x == downRight.cX && y == downRight.cY)
                    nextCorner = NextCorner.UP_LEFT;

            }
            case UP_LEFT -> {
                if(x == upLeft.cX && y == upLeft.cY)
                    nextCorner = NextCorner.DOWN_LEFT;

            }
            case DOWN_LEFT -> {
                if(x == downLeft.cX && y == downLeft.cY)
                    nextCorner = NextCorner.UP_RIGHT;
            }
        }
    }

    private GhostDirections decideDirection(ArrayList<GhostDirections> possibleDirections){
        float standardFitness = 0;
        float bestFitness = 1000;
        int index = 0;
        int i = 0;

        for(GhostDirections possibleDirection:possibleDirections) {


            switch (possibleDirection) {

                case UP -> {

                    standardFitness = calculateManhattanDistance(y -1,x);
                    if(standardFitness < bestFitness)
                    {
                        bestFitness = standardFitness;
                        index = i;

                    }
                }
                case DOWN -> {
                    standardFitness = calculateManhattanDistance(y + 1,x);
                    if(standardFitness < bestFitness)
                    {
                        bestFitness = standardFitness;
                        index = i;

                    }
                }
                case LEFT -> {
                    standardFitness = calculateManhattanDistance(y ,x - 1);
                    if(standardFitness < bestFitness)
                    {
                        bestFitness = standardFitness;
                        index = i;

                    }
                }
                case RIGHT -> {

                    standardFitness = calculateManhattanDistance(y,x + 1);
                    if(standardFitness < bestFitness)
                    {
                        bestFitness = standardFitness;
                        index = i;

                    }

                }
            }
            i++;
        }

        return possibleDirections.get(index);
    }

    private float calculateManhattanDistance(int y,int x){
        float altura;
        float largura;
        switch (nextCorner)
        {

            case UP_RIGHT -> {
                altura = y - upRight.cY;
                largura = upRight.cX - x;

                return (float) Math.sqrt(Math.pow(altura,2) + Math.pow(largura,2));
            }
            case DOWN_RIGHT -> {
                altura =  downRight.cY - y;
                largura = downRight.cX - x;

                return (float) Math.sqrt(Math.pow(altura,2) + Math.pow(largura,2));

            }
            case UP_LEFT -> {
                altura =  y - upLeft.cY;
                largura = x - upLeft.cX;

                return (float) Math.sqrt(Math.pow(altura,2) + Math.pow(largura,2));

            }
            case DOWN_LEFT -> {

                altura =  downLeft.cY - y;
                largura = x - downLeft.cX;

                return (float) Math.sqrt(Math.pow(altura,2) + Math.pow(largura,2));
            }
        }
        return -1;
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




    public void setUpRight(int x, int y) {
        upRight.setcX(x);
        upRight.setcY(y);
    }

    public void setDownRight(int x, int y) {
        downRight.setcX(x);
        downRight.setcY(y);
    }

    public void setUpLeft(int x, int y) {
        upLeft.setcX(x);
        upLeft.setcY(y);
    }

    public void setDownLeft(int x, int y) {
        downLeft.setcX(x);
        downLeft.setcY(y);
    }


}
