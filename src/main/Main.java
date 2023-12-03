package main;

import parsing.CSVReader;
import parsing.TimeSlotParser;

public class Main {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        TimeSlotParser parser = new TimeSlotParser();
        reader.parseFile("res/simulated_data/sim_time_slots_k.csv", parser);
        parser.getTimeSlotList().forEach(t -> {
            System.out.print("TS: ");
            t.getDaysOfWeek().forEach(d -> System.out.print(d + " "));
            System.out.print(t.getMinutesStart() + " - ");
            System.out.println(t.getMinutesEnd());
        });
    }

}
