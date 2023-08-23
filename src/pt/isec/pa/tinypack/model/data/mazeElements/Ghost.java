package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.IMazeElement;
import pt.isec.pa.tinypack.model.fsm.GameData;

public abstract class Ghost implements IMazeElement {


    public Ghost getGhost(){
        return this;
    }

    public void move(GameData gameData){}

}
