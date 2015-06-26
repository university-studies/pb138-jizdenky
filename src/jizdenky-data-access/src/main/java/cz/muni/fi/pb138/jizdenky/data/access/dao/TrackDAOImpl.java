package cz.muni.fi.pb138.jizdenky.data.access.dao;

import cz.muni.fi.pb138.jizdenky.data.access.model.Station;
import cz.muni.fi.pb138.jizdenky.data.access.model.Track;
import cz.muni.fi.pb138.jizdenky.data.access.model.Train;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
@Repository
public class TrackDAOImpl implements TrackDAO {

    private final Document doc;

    private StationDAO stationDao;
    private TrainDAO trainDAO;

    @Autowired
    public TrackDAOImpl(StationDAO stationDao, TrainDAO trainDAO) {
        System.out.println("TrackDAO constructor");
        if (stationDao == null) {
            System.out.println("TrackDAO ---> stationDAO is null");
        }
        if (trainDAO == null) {
            System.out.println("TrackDAO ---> trainDAO is null");
        }

        this.stationDao = stationDao;
        this.trainDAO = trainDAO;

        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("data/tracks.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private NodeList getNodeList() {
        return doc.getElementsByTagName("track");
    }

    private Track getTrackFromElement(Element e) {
        final Track track = new Track();
        track.setId(Long.valueOf(e.getAttribute("tr_id")));

        NodeList stationsNodeList = e.getElementsByTagName("id_station");
        List<Station> stations = new ArrayList<>();
        for (int i = 0; i < stationsNodeList.getLength(); i++) {
            Node stationNode = stationsNodeList.item(i);
            Element stationElement = (Element) stationNode;
            Long stationID = Long.valueOf(stationElement.getTextContent());
            stations.add(stationDao.getById(stationID));
        }
        track.setStations(stations);
        track.setLength(Integer.valueOf(e.getElementsByTagName("length").item(0).getTextContent()));

        NodeList trainsNodeList = e.getElementsByTagName("id_train");
        List<Train> trains = new ArrayList<>();
        for (int i = 0; i < trainsNodeList.getLength(); i++) {
            Node trainNode = trainsNodeList.item(i);
            Element trainElement = (Element) trainNode;
            Long trainID = Long.valueOf(trainElement.getTextContent());
            trains.add(trainDAO.getById(trainID));
        }
        track.setTrains(trains);

        return track;
    }

    @Override
    public Track getById(Long id) {
        Validate.notNull(id);
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("tr_id").equals(id.toString())) {
                return getTrackFromElement(eElement);
            }
        }
        return null;
    }

    @Override
    public List<Track> getAll() {
        List<Track> tracks = new ArrayList<>();
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            tracks.add(getTrackFromElement(eElement));
        }
        return tracks;
    }
}
