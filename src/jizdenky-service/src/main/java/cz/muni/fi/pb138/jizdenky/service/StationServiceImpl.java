package cz.muni.fi.pb138.jizdenky.service;

import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAO;
import cz.muni.fi.pb138.jizdenky.data.access.model.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Oliver Pentek
 */
public class StationServiceImpl implements StationService {
    
    @Autowired
    private StationDAO stationDAO;

    @Nonnull
    @Override
    public List<Station> getStationsByNamePattern(@Nonnull String pattern) {
        Validate.notBlank(pattern);
        final Pattern pat = Pattern.compile(".*" + pattern.trim() + ".*");
        List<Station> satisfyingStations = new ArrayList<>();
        for (final Station station : stationDAO.getAll()) {
            Matcher m = pat.matcher(station.getName());
            if (m.find()) {
                satisfyingStations.add(station);
            }
        }
        return satisfyingStations;
    }

}
