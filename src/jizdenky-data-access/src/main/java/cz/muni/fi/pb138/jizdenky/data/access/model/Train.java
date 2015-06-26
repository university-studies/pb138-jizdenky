package cz.muni.fi.pb138.jizdenky.data.access.model;

import org.joda.time.LocalTime;

/**
 *
 * @author Oliver Pentek
 */
public class Train extends AbstractEntity {
    private TrainType type;
    private String name;
    private Station startStation;
    private LocalTime startTime;

    public TrainType getType() {
        return type;
    }

    public void setType(TrainType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    
}
