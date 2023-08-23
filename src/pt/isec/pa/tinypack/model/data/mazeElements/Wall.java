package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.IMazeElement;

public class Wall implements IMazeElement {
    @Override
    public char getSymbol() {
        return 'x';
    }
}
