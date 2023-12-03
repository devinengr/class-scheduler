package implementation;

import genetics.representation.Category;
import implementation.util.BoardType;

public class ClassRoom extends Category {

    private int roomNumber;
    private BoardType boardType;

    public ClassRoom() {
        super(0, false);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

}
