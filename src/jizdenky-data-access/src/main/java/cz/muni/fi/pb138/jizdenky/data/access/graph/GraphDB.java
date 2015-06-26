package cz.muni.fi.pb138.jizdenky.data.access.graph;

import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAO;
import cz.muni.fi.pb138.jizdenky.data.access.dao.TrackDAO;
import cz.muni.fi.pb138.jizdenky.data.access.model.Journey;
import cz.muni.fi.pb138.jizdenky.data.access.model.Station;
import cz.muni.fi.pb138.jizdenky.data.access.model.Track;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
@Repository
public class GraphDB {
    private static final String STATION_ID_PROPERTY = "station_id";
    private static final String DB_PATH = "target/graph-db";
    private static final String RELATIONSHIP_ID_PROPERTY = "track_id";
    private static final int MAX_NODE_COUNT = 30;

    private GraphDatabaseService graphDb;
    private Index<Node> stations;

    private TrackDAO trackDAO;
    private StationDAO stationDAO;

    @Autowired
    public GraphDB(TrackDAO trackDAO, StationDAO stationDAO) {
        this.trackDAO = trackDAO;
        this.stationDAO = stationDAO;

        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(DB_PATH)
                .setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size, "10M")
                .setConfig(GraphDatabaseSettings.string_block_size, "60")
                .setConfig(GraphDatabaseSettings.array_block_size, "300")
                .newGraphDatabase();
        // shutDown databaze je robeny manualne - toto sposobovalo potom vynimku
//        registerShutdownHook(graphDb);

        try (Transaction tx = graphDb.beginTx()) {
            IndexManager index = graphDb.index();
            stations = index.forNodes("stations");
            tx.success();
        }

        createNodesFromStations(this.stationDAO.getAll());
        createRelationShipsFromTracks(this.trackDAO.getAll());
    }

    public void initGraphDB() {
        create();
        createNodesFromStations(stationDAO.getAll());
        createRelationShipsFromTracks(trackDAO.getAll());
    }

    private void create() {
        graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(DB_PATH)
                .setConfig(GraphDatabaseSettings.nodestore_mapped_memory_size, "10M")
                .setConfig(GraphDatabaseSettings.string_block_size, "60")
                .setConfig(GraphDatabaseSettings.array_block_size, "300")
                .newGraphDatabase();
        // shutDown databaze je robeny manualne - toto sposobovalo potom vynimku
//        registerShutdownHook(graphDb);

        try (Transaction tx = graphDb.beginTx()) {
            IndexManager index = graphDb.index();
            stations = index.forNodes("stations");
            tx.success();
        }
    }


    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
                try {
                    FileUtils.deleteDirectory(new File(DB_PATH));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void createNodesFromStations(List<Station> stationList) {
        try (Transaction tx = graphDb.beginTx()) {
            for (Station station : stationList) {
                final Node node = graphDb.createNode();
                final Long id = station.getId();
                node.setProperty(STATION_ID_PROPERTY, id);
                stations.add(node, STATION_ID_PROPERTY, id);
            }
            tx.success();
        }
    }

    private Node getNodeFromStation(Station station) {
        IndexHits<Node> hits = stations.get(STATION_ID_PROPERTY, station.getId());
        return hits.getSingle();
    }

    private void createRelationShipsFromTracks(List<Track> tracks) {
        try (Transaction tx = graphDb.beginTx()) {
            for (Track track : tracks) {
                List<Station> stationList = track.getStations();
                Validate.isTrue(stationList.size() == 2, "There are not 2 stations in the track");
                Node station1 = getNodeFromStation(stationList.get(0));
                Node station2 = getNodeFromStation(stationList.get(1));
                Relationship relationship = station1.createRelationshipTo(station2, RelTypes.PATH);
                relationship.setProperty(RELATIONSHIP_ID_PROPERTY, track.getId());
            }
            tx.success();
        }
    }

    private Iterable<Path> getPaths(Station startStation, Station endStation) {
        Validate.notNull(startStation);
        Validate.notNull(endStation);

        Iterable<Path> paths;
        try (Transaction tx = graphDb.beginTx()) {
            Node startNode = getNodeFromStation(startStation);
            Node endNode = getNodeFromStation(endStation);
            PathFinder<Path> finder = GraphAlgoFactory.allSimplePaths(
                    PathExpanders.forTypeAndDirection(RelTypes.PATH, Direction.BOTH), MAX_NODE_COUNT);
            paths = finder.findAllPaths(startNode, endNode);
            tx.success();
        }
        return paths;
    }

    public List<Journey> getJourneys(Station station1, Station station2) {
        Iterable<Path> paths = getPaths(station1, station2);
        List<Journey> journeys = new ArrayList<>();
        try (Transaction tx = graphDb.beginTx()) {
            for (Path path : paths) {
                Station joinStation = station1;
                List<Track> tracks = new LinkedList<>();
                for (Relationship rel : path.relationships()) {
                    Track track = trackDAO.getById((Long) rel.getProperty(GraphDB.RELATIONSHIP_ID_PROPERTY));
                    track.orderStations(joinStation);
                    tracks.add(track);
                    joinStation = track.getStations().get(1);
                }

                Journey journey = new Journey();
                journey.setTracks(tracks);
                journeys.add(journey);
            }
            tx.success();
        }
        return journeys;
    }

    public void shutDownAndRemowe() {
        graphDb.shutdown();
        try {
            FileUtils.deleteDirectory(new File(DB_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static enum RelTypes implements RelationshipType {
        PATH
    }
}
