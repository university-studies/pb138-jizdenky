package cz.muni.fi.pb138.jizdenky.data.access.dao;

import cz.muni.fi.pb138.jizdenky.data.access.model.Train;
import cz.muni.fi.pb138.jizdenky.data.access.model.TrainType;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
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
public class TrainDAOImpl implements TrainDAO{
    
    private final Document doc;

    private StationDAO stationDao;

    @Autowired
    public TrainDAOImpl(StationDAO stationDao) {
        System.out.println("TrainDAO constructor");
        if (stationDao == null) {
            System.out.println("TrainDAO ---> stationDAO is null");
        }

        this.stationDao = stationDao;

        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("data/trains.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private NodeList getNodeList() {
        return doc.getElementsByTagName("train");
    }

    private Train getTrainFromElement(Element e) {
        final Train train = new Train();
        train.setId(Long.valueOf(e.getAttribute("t_id")));
        train.setType(TrainType.getById(e.getAttribute("type")));
        train.setName(e.getElementsByTagName("name").item(0).getTextContent());
        train.setStartTime(LocalTime.parse(e.getElementsByTagName("start_time").item(0).getTextContent()));
        train.setStartStation(stationDao.getById(Long.valueOf(e.getElementsByTagName("start_station").item(0).getTextContent())));
        return train;
    }

    
    @Nullable
    public Train getById(Long id) {
        Validate.notNull(id);
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("t_id").equals(id.toString())) {
                return getTrainFromElement(eElement);
            }
        }
        return null;
    }

    public List<Train> getAll() {
        List<Train> trains = new ArrayList<Train>();
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            trains.add(getTrainFromElement(eElement));
        }
        return trains;
    }

    public Train getByName(String name) {
        Validate.notNull(name);
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getElementsByTagName("name").item(0).getTextContent().equals(name)) {
                return getTrainFromElement(eElement);
            }
        }
        return null;
    }

    public List<Train> getByType(TrainType type) {
        Validate.notNull(type);
        List<Train> trains = new ArrayList<Train>();
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("type").equals(type.getId())) {
                trains.add(getTrainFromElement(eElement));
            }
        }
        return trains;
    }
    
}
