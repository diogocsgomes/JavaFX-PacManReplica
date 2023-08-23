package pt.isec.pa.tinypack.model.data;

public enum PacManDirections {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    @Override
    public String toString() {
        switch (this) {
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case LEFT:
                return "LEFT";
            case RIGHT:
                return "RIGHT";
            default:
                return null;
        }
    }

}

