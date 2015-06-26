package cz.muni.fi.pb138.jizdenky.data.access.dao;

import cz.muni.fi.pb138.jizdenky.data.access.model.Station;

import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public interface StationDAO {
    Station getById(Long id);
    List<Station> getAll();
    Station getByName(String name);
    List<Station> getByZone(Integer zone);
}
