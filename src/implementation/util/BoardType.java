package implementation.util;

public enum BoardType {

    CHALK(1),
    WHITE(2),
    ;

    private int type;

    BoardType(int type) {
        this.type = type;
    }

    public static BoardType valueOf(int value) {
        switch (value) {
            case 1: return CHALK;
            case 2: return WHITE;
            default: return null;
        }
    }

    public int getTypeValue() {
        return type;
    }

}
