package cz.muni.fi.pb138.jizdenky.data.access.model;

import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;

/**
 *
 * @author Oliver Pentek
 */
public class Station extends AbstractEntity {
    private Integer zone;
    @Nonnull
    private String name;

    public Station() {
    }
    
    public Station(int zone, @Nonnull String name) {
        Validate.notNull(name);
        this.zone = zone;
        this.name = name;
    }

    public int getZone() {
        return zone;
    }
    
    @Nonnull
    public String getName() {
        return name;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void setName(String name) {
        this.name = name;
    }
   
}
