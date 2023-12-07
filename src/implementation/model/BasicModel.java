package implementation.model;

public class BasicModel {

    // assign each together:
    //      course section i
    //      classroom j
    //      time module k
    //
    // goal: minimize difference of # of course sections between these 2 rows:
    //      MW, WF, MF, MWF     (50% of course sections)
    //      TR                  (50% of course sections)
    //
    // constraints:
    //      j and k assigned to at most one i
    //      each i assigned to exactly one j and k
    //      no 3-credit i assigned to 4-credit k
    //      no 4-credit i assigned to 3-credit k
    //      j assigned at most one i during morning, afternoon, or evening
    //      # of M,W,F courses and # of T,R courses are within some delta (W) of any even split (minimize W)
    //      each i is assigned exactly once
    //      all ijk is assigned 0,1. 0=not chosen, 1=chosen.




}
