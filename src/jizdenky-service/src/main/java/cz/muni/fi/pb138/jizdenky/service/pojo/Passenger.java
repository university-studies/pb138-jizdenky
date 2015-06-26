package cz.muni.fi.pb138.jizdenky.service.pojo;

/**
 * Trieda reprezentujuca jedneho pasaziera vramci uzivatelom zadanych dat.
 * @author Oliver Pentek
 */
public class Passenger implements Comparable<Passenger>{

    private PassengerType type;

    public PassengerType getType() {
        return type;
    }

    public void setType(PassengerType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Passenger o) {
        return o.getType().ordinal() - this.getType().ordinal();
    }

}
