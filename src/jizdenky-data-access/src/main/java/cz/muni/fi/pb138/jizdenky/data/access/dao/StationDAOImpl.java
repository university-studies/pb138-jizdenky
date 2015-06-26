package cz.muni.fi.pb138.jizdenky.data.access.dao;

import cz.muni.fi.pb138.jizdenky.data.access.model.Station;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nonnull;
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
public final class StationDAOImpl implements StationDAO {

    private final Document doc;

    public StationDAOImpl() {
        System.out.println("StationDAO constructor");

        try {
          //  File fXmlFile = new File("data/stations.xml");                toto bude aktualne zrejme az ked to bude .war

            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("data/stations.xml");

//            File fXmlFile = new File("src/main/resources/data/stations.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            doc = dBuilder.parse(fXmlFile);
            doc = dBuilder.parse(in);

            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private NodeList getNodeList() {
        return doc.getElementsByTagName("station");
    }

    private Station getStationFromElement(Element e) {
        final Station station = new Station();
        station.setId(Long.valueOf(e.getAttribute("s_id")));
        station.setZone(Integer.valueOf(e.getAttribute("zone")));
        station.setName(e.getTextContent());
        return station;
    }

    @Nullable
    public Station getById(Long id) {
        Validate.notNull(id);
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("s_id").equals(id.toString())) {
                return getStationFromElement(eElement);
            }
        }
        return null;
    }

    @Nonnull
    public List<Station> getAll() {
        List<Station> stations = new ArrayList<Station>();
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            stations.add(getStationFromElement(eElement));
        }
        return stations;
    }

    @Nullable
    public Station getByName(String name) {
        Validate.notNull(name);
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getTextContent().equals(name)) {
                return getStationFromElement(eElement);
            }
        }
        return null;
    }

    @Nonnull
    public List<Station> getByZone(Integer zone) {
        Validate.notNull(zone);
        List<Station> stations = new ArrayList<Station>();
        NodeList nList = getNodeList();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (eElement.getAttribute("zone").equals(zone.toString())) {
                stations.add(getStationFromElement(eElement));
            }
        }
        return stations;
    }
}
