package pt.isec.pa.tinypack.ui.text;

/*import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

 */
import pt.isec.pa.tinypack.model.fsm.Context;

import java.io.IOException;

public class UserInterface {

    Context GameContext;

    public UserInterface(Context Game)
    {
        this.GameContext = Game;
    }


    public void display()throws IOException, InterruptedException{
        /*
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(80, 40)).createTerminal();
        Screen screen = new TerminalScreen(terminal);
        TextGraphics tGraphics = screen.newTextGraphics();

        char[][] gameContextOutput = GameContext.getOutput();
        System.out.println(gameContextOutput.length + " " + gameContextOutput[0].length);



        terminal.enterPrivateMode();

        KeyStroke keyStroke = new KeyStroke(KeyType.Enter);
        //KeyStroke keyStroke = terminal.pollInput();

        //TextGraphics txt = screen.newTextGraphics();

        screen.startScreen();
        screen.clear();
       // int i = 0;
       /* do {
            gameContextOutput = GameContext.getBoard();

            for(int x = 0; x < gameContextOutput[0].length;x++)
            {
                for(int y = 0; y < gameContextOutput.length;y++)
                {
                    screen.setCharacter(x + 22, y + 5, new TextCharacter(gameContextOutput[y][x]));

                }
            }
            // keyStroke = screen.readInput();metodo bloqueante
            keyStroke = screen.pollInput(); // metodo nao bolquenate
            if(keyStroke != null)
                GameContext.SetInput(keyStroke.getKeyType());

            screen.refresh();
        }while (keyStroke.getKeyType() != KeyType.Escape);
        */

        /*
        while (true)
        {
           // tGraphics.putString(GameContext.getStateAsString());
            tGraphics.putString(0,0,GameContext.getStateAsString());
            gameContextOutput = GameContext.getOutput();
            char [][] gameMenu = GameContext.getMenu();

            for(int x = 0; x < gameContextOutput[0].length;x++)
            {
                for(int y = 0; y < gameContextOutput.length;y++)
                {
                    screen.setCharacter(x + 22, y + 5, new TextCharacter(gameContextOutput[y][x]));

                }
            }
            for(int x = 0; x < gameMenu[0].length;x++)
            {
                for(int y = 0; y < gameMenu.length;y++)
                {
                    screen.setCharacter(x , y + 5, new TextCharacter(gameMenu[y][x]));

                }
            }



            // keyStroke = screen.readInput();metodo bloqueante
            keyStroke = screen.pollInput(); // metodo nao bolquenate

            if(keyStroke != null) {
                GameContext.SetInput(keyStroke.getKeyType());
                if (keyStroke.getKeyType() == KeyType.Escape)
                {
                    break;
                }
            }
            screen.refresh();
        }


        screen.readInput();
        screen.stopScreen();



        /*while (true)
        {
            i++;
            keyStroke = screen.readInput();


                    // Obtém o caractere digitado
                    //char c = keyStroke.getCharacter();


                    // System.out.println("Caractere digitado: " + c);
                    screen.setCharacter(10 + i, 10, new TextCharacter(gameContextOutput[0][0]));
                   // System.out.println(gameContextOutput[0][1]);


                 if (keyStroke.getKeyType() == KeyType.Escape) {
                    // Encerra o programa se a tecla ESC for pressionada
                    break;
                }

            screen.refresh();
        }

         */



/**
 * Devido a maneira como a biblioteca lanterna esta construida o X representa alargura o Y a altura
 logo o a cariavel X representa cada coluna e o y cada linha.

 */




    }

}
