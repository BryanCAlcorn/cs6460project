package omscs.edtech.roster;

public class RosterReaderFactory {

    public static RosterReader getQReader(){
        return new QRosterReader();
    }
}
