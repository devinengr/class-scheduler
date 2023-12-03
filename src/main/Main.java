package main;

import implementation.TimeSlot;
import parsing.TimeSlotParser;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        long minutesStart = TimeUnit.HOURS.toMinutes(5);



        // split[0]: ignore (time slot number)
        // split[1]: days of week

        TimeSlotParser parser = new TimeSlotParser();

        ///////////////////////////
        // split[2]: time start
        ///////////////////////////
        //parseTime(split[2]);
        //parser.parseTime(split[2], split[3]);

        // if am/pm on 1st:
        //      just make 1st am/pm

        // split[3]: time end
    }

}
