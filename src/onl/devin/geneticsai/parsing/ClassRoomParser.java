package onl.devin.geneticsai.parsing;

import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.util.BoardType;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomParser implements FileParser {

    private List<ClassRoom> classRoomList;

    public ClassRoomParser() {
        this.classRoomList = new ArrayList<>();
    }

    public List<ClassRoom> getClassRoomList() {
        return new ArrayList<>(classRoomList);
    }

    @Override
    public void parseLine(String line, int lineNumber) {
        // ignore header
        if (lineNumber == 1) {
            return;
        }
        String[] split = line.split(",");
        int roomNumber = Integer.parseInt(split[0]);
        int boardType = Integer.parseInt(split[1]);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setRoomNumber(roomNumber);
        classRoom.setBoardType(BoardType.valueOf(boardType));
        classRoomList.add(classRoom);
    }

}
