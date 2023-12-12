package implementation.category;

import genetics.representation.Category;
import genetics.util.BitStringGenerator;
import implementation.util.BoardType;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom extends Category {

    private static List<ClassRoom> classRoomList = new ArrayList<>();

    private int roomNumber;
    private BoardType boardType;

    public ClassRoom() {
        classRoomList.add(this);
    }

    public static ClassRoom getClassRoomByRoomNumber(int roomNumber) {
        for (ClassRoom classRoom : classRoomList) {
            if (classRoom.getRoomNumber() == roomNumber) {
                return classRoom;
            }
        }
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(roomNumber);
        return classRoom;
    }

    public static int getNumberOfClassRooms() {
        return classRoomList.size();
    }

    @Override
    public int getOutcomeCount() {
        return classRoomList.size();
    }

    @Override
    public List<Category> getOutcomeList() {
        return new ArrayList<>(classRoomList);
    }

    /**
     * call after initializing all objects
     */
    public static void initializeBitStringData() {
        BitStringGenerator bitGen = new BitStringGenerator(classRoomList.get(0));
        classRoomList.forEach(classRoom -> {
            classRoom.setBitString(bitGen.next());
        });
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof ClassRoom) {
            ClassRoom other = (ClassRoom) o;
            return other.roomNumber == roomNumber;
        }
        return false;
    }

}
