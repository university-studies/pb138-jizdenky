package cz.muni.fi.pb138.jizdenky.data.access.model;

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public class Journey implements Comparable<Journey>{
    private List<Track> tracks = new LinkedList<>();
    private Integer totalLength;
    private BigDecimal price;
    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
    
    /**
     * Returns lazy loaded total length of journey.
     * @return 
     */
    @Nonnull
    public Integer getTotalLength() {
        if (this.totalLength == null) {
            Integer result = 0;
            for (final Track track : this.getTracks()) {
                result += track.getLength();
            }
            return result;
        }
        return this.totalLength;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPriceAfterDiscount(BigDecimal priceAfterDiscount) {
        this.price = priceAfterDiscount;
    }    

    @Override
    public String toString() {
        StringBuilder bobTheBuilder = new StringBuilder("Journey: total length = " + this.getTotalLength());
        bobTheBuilder.append(System.getProperty("line.separator"));
        for (Track track : getTracks()) {
            for (Station station : track.getStations())
            bobTheBuilder.append(station);
            bobTheBuilder.append(System.getProperty("line.separator"));
        }
        bobTheBuilder.append("===========================================================");
        bobTheBuilder.append(System.getProperty("line.separator"));
        return bobTheBuilder.toString();
    }

    @Override
    public int compareTo(Journey o) {
        return this.getTotalLength() - o.getTotalLength();
    }
}
