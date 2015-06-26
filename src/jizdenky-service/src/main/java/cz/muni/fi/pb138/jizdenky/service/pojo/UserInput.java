package cz.muni.fi.pb138.jizdenky.service.pojo;

import cz.muni.fi.pb138.jizdenky.data.access.model.Station;

import java.util.List;
import org.joda.time.LocalDate;

/**
 * Sluzi ako obalka pre uzivatelom zadane data vo formulari. 
 * TODO zvazit a pridat potrebne vlastnosti
 *
 * @author Oliver Pentek
 */
public class UserInput {
    
    private Station from;
    private Station to;
    private List<Passenger> passengers;
    private LocalDate dayOfDeparture;

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public LocalDate getDayOfDeparture() {
        return dayOfDeparture;
    }

    public void setDayOfDeparture(LocalDate dayOfDeparture) {
        this.dayOfDeparture = dayOfDeparture;
    }   
    
}
