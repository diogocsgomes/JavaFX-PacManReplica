package pt.isec.pa.tinypack.model.data.mazeElements;

import pt.isec.pa.tinypack.model.data.IMazeElement;

public class GhostPortal implements IMazeElement {
    @Override
    public char getSymbol() {
        return 'Y';
    }
}
