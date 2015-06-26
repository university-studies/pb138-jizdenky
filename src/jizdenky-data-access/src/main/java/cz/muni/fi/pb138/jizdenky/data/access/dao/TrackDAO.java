package cz.muni.fi.pb138.jizdenky.data.access.dao;

import cz.muni.fi.pb138.jizdenky.data.access.model.Track;

import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public interface TrackDAO {
    Track getById(Long id);
    List<Track> getAll();
}
