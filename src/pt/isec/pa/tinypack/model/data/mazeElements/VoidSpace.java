package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.IMazeElement;

public class VoidSpace implements IMazeElement {
    @Override
    public char getSymbol() {
        return ' ';
    }
}
