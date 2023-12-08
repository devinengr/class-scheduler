package parsing;

import implementation.category.ClassRoom;
import implementation.util.BoardType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ClassRoomParserTest {

    void testParser(ClassRoomParser parser, int index,
                    String line, int lineNumber,
                    int roomNumber, BoardType boardType) {
        parser.parseLine(line, lineNumber);
        ClassRoom classRoom = parser.getClassRoomList().get(index);
        assertEquals(roomNumber, classRoom.getRoomNumber());
        assertEquals(boardType, classRoom.getBoardType());
    }

    @Test
    void parseLineIgnoresHeader() {
        ClassRoomParser parser = new ClassRoomParser();
        Executable exec = () -> testParser(parser, 0, "1,1", 1, 1, BoardType.CHALK);
        assertThrows(IndexOutOfBoundsException.class, exec);
        testParser(parser, 0, "6,2", 2, 6, BoardType.WHITE);
    }

    @Test
    void parseLineMultiple() {
        ClassRoomParser parser = new ClassRoomParser();
        testParser(parser, 0, "1,1", 0, 1, BoardType.CHALK);
        testParser(parser, 1, "6,2", 2, 6, BoardType.WHITE);
    }

    @Test
    void parseLine() {
        testParser(new ClassRoomParser(), 0, "1,1", 0, 1, BoardType.CHALK);
        testParser(new ClassRoomParser(), 0, "6,2", 2, 6, BoardType.WHITE);
    }

}
