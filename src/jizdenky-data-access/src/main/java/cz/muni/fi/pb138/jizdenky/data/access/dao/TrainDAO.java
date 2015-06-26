package cz.muni.fi.pb138.jizdenky.data.access.dao;

import cz.muni.fi.pb138.jizdenky.data.access.model.Train;
import cz.muni.fi.pb138.jizdenky.data.access.model.TrainType;

import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public interface TrainDAO {
    Train getById(Long id);
    List<Train> getAll();
    Train getByName(String name);
    List<Train> getByType(TrainType type);
}
