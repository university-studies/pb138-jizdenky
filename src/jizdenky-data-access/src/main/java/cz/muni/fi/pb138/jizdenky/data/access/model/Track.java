package cz.muni.fi.pb138.jizdenky.data.access.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public class Track extends AbstractEntity {
    private List<Station> stations;
    private int length;
    private List<Train> trains;

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }
    
    public void orderStations(Station firstStation) {
        if (!stations.contains(firstStation)) {
            throw new IllegalArgumentException("Stations does not contain first station.");
        }
        if (!stations.get(0).equals(firstStation)) {
            if (!stations.get(1).equals(firstStation)) {
                throw new IllegalStateException("The first nor second station equals first station.");
            } else {
                List<Station> newList = new LinkedList<>();
                newList.add(stations.get(1));
                newList.add(stations.get(0));
                this.setStations(newList);
            }
        }
    }
  
}
