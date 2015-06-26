package cz.muni.fi.pb138.jizdenky.data.access.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.Arrays;

/**
 *
 * @author Oliver Pentek
 */
public enum TrainType {
    
    RYCHLIK("R"),
    OSOBAK("OS"),
    IC("IC"),
    EC("EC"),
    SP("SP"),
    EX("EX"),
    EN("EN"),
    SC("SC");
    
    private final String id;

    private TrainType(String id) {
        this.id = id;
    }
    
    public static TrainType getById(final String id) {
        return Iterables.find(Arrays.asList(TrainType.values()), new Predicate<TrainType>() {
            public boolean apply(TrainType input) {
                return input.getId().equals(id);
            }
        });
    }

    public String getId() {
        return id;
    } 
    
}
