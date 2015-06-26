package cz.muni.fi.pb138.jizdenky.service;

import cz.muni.fi.pb138.jizdenky.data.access.model.Station;
import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public interface StationService {
    public List<Station> getStationsByNamePattern(String pattern);
}
