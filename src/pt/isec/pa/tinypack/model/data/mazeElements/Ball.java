package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.IMazeElement;

public class Ball implements IMazeElement {
    @Override
    public char getSymbol() {
        return 'o';
    }
}
