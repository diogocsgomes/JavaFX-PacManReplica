package pt.isec.pa.tinypack.model.fsm;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.tinypack.gameengine.GameEngine;
import pt.isec.pa.tinypack.gameengine.IGameEngine;
import pt.isec.pa.tinypack.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypack.model.data.HighScores;
import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.data.PacManDirections;
import pt.isec.pa.tinypack.model.data.Score;
import pt.isec.pa.tinypack.model.help_functions.HelpFunctions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;

import static pt.isec.pa.tinypack.model.data.PacManDirections.*;

public class GameManager implements IGameEngineEvolve {

    Context fsm;//finite state machine

    IGameEngine movePacMan;
   // IGameEngine moveGhosts;

    private PropertyChangeSupport pcs;
    /*private final String  FIRE_MENU= "Fire Menu";
    private final String  FIRE_INICIAR_JOGO = "Fire iniciar jogo";

    private final String FIRE_FINALIZAR_JOGO = "Fire finalizar o jogo";

   // private final String FIRE_EVOLVE = "Fire evolve";

     */

    private final double WINDOW_WIDTH = 600.0;
    private final double WINDOW_HEIGHT = 700.0;



    private final double BORAD_WIDTH_AND_WEIGHT_PIXEL = 500.0;



    Image PacUp;
    Image PacDown;
    Image PacLeft;
    Image PacRight;

    private HighScores highScores;

    private List<Score>  Top5;
    private boolean MainMenuVisible;
    private boolean GameViewVisible;




    public GameManager(){
        this.fsm = new Context();

        pcs = new PropertyChangeSupport(this);

        MainMenuVisible = true;
        GameViewVisible = false;

        movePacMan = new GameEngine();
        movePacMan.registerClient(this);

        highScores = new HighScores();

        Top5 = highScores.getTop5();



    }


    public Image getPacImage(){

        PacUp = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\pacmanUp.gif");
        PacDown = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\pacmanDown.gif");
        PacLeft = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\pacmanLeft.gif");
        PacRight = new Image("\\pt\\isec\\pa\\tinypack\\ui\\gui\\resources\\images\\pacmanRight.gif");

        if(fsm.getState() == GameState.PRE_GAME)
        {
            return null;
        }

        if(fsm.getPacDir() == UP)
            return PacUp;
        if (fsm.getPacDir() == DOWN)
            return PacDown;
        if(fsm.getPacDir() == LEFT)
            return PacLeft;
        if (fsm.getPacDir() == RIGHT)
            return PacRight;

        return null;

    }

    public void addPropertyChangeListener(String string,PropertyChangeListener listener) {
        //pcs.addPropertyChangeListener(listener);
        pcs.addPropertyChangeListener(string,listener);
    }




    public void setInput(PacManDirections input)
    {

         fsm.setInput(input);
         //pcs.firePropertyChange(PropertyChangeNames.FIRE_IN_GAME,null,null);
    }


    public GameState getState(){
        return fsm.getState();
    }

    public void changeState(GameState state)
    {

        fsm.changeState(state);
       // this.state = stateFactory.changeState(state,GameEntities,this);
        //this.state = this.stateFactory.changeState(state,this.state.getPacMan(),this);
    }



    public char[][] getOutput(){

        //state.getEntities.getMaze.getBoard;
        // return this.GameEntities.getOutput(); //TEM QUE SER SUBSTTUIDO POR STATE.GETOUTPUT

       // return this.state.getOutput();
        return fsm.getOutput();
    }



    public String getStateAsString(){
        return fsm.getStateAsString();
        //return state.getStateAsString();
    }

    public char[][] getMenu()
    {
        return fsm.getMenu();
        //return state.getGameMenu();
    }



    public GameStateFactory getStateFactory() {
        return fsm.getStateFactory();
        //return stateFactory;
    }

    public GameData getGameEntities() {

        return fsm.getGameData();
        //return GameEntities;
    }


    public IMazeElement[][] getBoard() {

        return fsm.getElementMaze();
    }


    public char[][] getCharMaze(){
        return fsm.getCharMaze();
    }

    public int getNumLinhas(){

        return fsm.getNumLinhas();
    }

    public int getNumColunas(){
        return fsm.getNumColunas();
    }

    public int getMAX_NUM_LINH_AND_COL()
    {
        return fsm.getMAX_NUM_LINH_AND_COL();
    }


    public double getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public double getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public double getBORAD_WIDTH_AND_WEIGHT_PIXEL() {
        return BORAD_WIDTH_AND_WEIGHT_PIXEL;
    }


    public  void menu()
    {
        movePacMan.pause();
        fsm.menu();
        pcs.firePropertyChange(PropertyChangeNames.FIRE_MENU,null,null);
    }

    public void retomar(){
        movePacMan.resume();
        fsm.retomar();
        pcs.firePropertyChange(PropertyChangeNames.FIRE_MENU,null,null);

    }

    public void iniciarJogo(){


        movePacMan.start(250);
        //movePacMan.start(250);
        pcs.firePropertyChange(PropertyChangeNames.FIRE_INICIAR_JOGO,null,null);
    }


    public boolean isMainMenuVisible() {
        return MainMenuVisible;
    }

    public void setMainMenuVisible(boolean mainMenuVisible) {
        MainMenuVisible = mainMenuVisible;
    }

    public boolean isGameViewVisible() {
        return GameViewVisible;
    }

    public void setGameViewVisible(boolean gameViewVisible) {
        GameViewVisible = gameViewVisible;
    }

    public void gravar(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("Funcionalidade por implementar!");
        alert.showAndWait();
    }

    public void consultarTop5(){
       /* Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("Funcionalidade por implementar!");
        alert.showAndWait();

        */

        Top5 = highScores.getTop5();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Top 5");

        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                Top5.get(0).getNome() + " score: " + Top5.get(0).getScore(),
                Top5.get(1).getNome() + " score: " + Top5.get(1).getScore(),
                Top5.get(2).getNome() + " score: " + Top5.get(2).getScore(),
                Top5.get(3).getNome() + " score: " + Top5.get(3).getScore(),
                Top5.get(4).getNome() + " score: " + Top5.get(4).getScore());
        listView.setItems(items);

        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();


        /*for(Score i : Top5){
            if(i != null) {
                System.out.println(i.getNome() + " " + i.getScore());
            }
        }

         */
    }


    public void sair(){//SAI DA APLICAÇÃO NAO CONFUNDIR COM O METODO sairJogo QUE APENAS SAO DO TABULEIRO
        highScores.save();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza de que deseja sair?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmação de saída");
        alert.setHeaderText(null);

        alert.showAndWait()
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> Platform.exit());
    }

    public void sairJogo(){//SAI DO TABULEIRO, NÃO CONUNDIR COM O METODO SAIR que sai da aplicação


        //POR IMPLEMENTAR FUNCIONALIDADES DE ZERAR O JOGO
        //DE MOMENTO SE O UTILIZADOR SAIR E VOLTAR AO JOGO ESTE ESTA NO MESMO ESTADO QUE ANTES, OQ UE SE QUER É QUE
        //O JOGO ESTEJA NUM ESTADO DE JOGO NOVO
        movePacMan.stop();
        fsm.stopGhostMovement();

        int score = fsm.getPontos();
        if(highScores.IsTop5(score))
        {
            Platform.runLater(() -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Solicitação de Texto");
            dialog.setHeaderText(null);
            dialog.setContentText("Parabens chegou ao Top 5!!\nPor favor, insira o seu nome:");

            // Esta linha armazena o resultado na variável "resultado"
            final String[] resultado = new String[1];

            dialog.showAndWait()
                    .ifPresent(result -> resultado[0] = result);

            // Imprime o resultado
            //System.out.println("A string inserida foi: " + resultado[0]);
            highScores.addScore(resultado[0],score);
            });

        }

       // highScores.addScore("Maria",12);

        this.fsm = new Context();

        setGameViewVisible(false);
        setMainMenuVisible(true);
        pcs.firePropertyChange(PropertyChangeNames.FIRE_INICIAR_JOGO,null,null);

    }


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if(!fsm.isLeave_game()) {
            fsm.evolve();
            pcs.firePropertyChange(PropertyChangeNames.FIRE_EVOLVE, null, null);
            return;
        }

        System.out.println("O JOGO ACABOUUU");

        sairJogo();
        pcs.firePropertyChange(PropertyChangeNames.FIRE_EVOLVE, null, null);


    }



    public  int getPontos(){
        return fsm.getPontos();
    }

    public int getLivesLeft(){return fsm.getLivesLeft();}


    public int getHighScore()
    {
        if(Top5.get(0) == null)
        {
            return 0;
        }
        return Top5.get(0).getScore();
    }


}
