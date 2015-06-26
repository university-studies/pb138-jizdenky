package cz.muni.fi.pb138.jizdenky.data.access;

import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAO;
import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAOImpl;
import cz.muni.fi.pb138.jizdenky.data.access.graph.GraphDB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        StationDAO stationDao = new StationDAOImpl();
//        List<Station> stations = stationDao.getAll();
//        
//        for(Station station : stations) {
//            System.out.println(station);
//        }
//        
//        TrainDAO trainDao = new TrainDAOImpl();
//        List<Train> trains = trainDao.getAll();
//        for(Train train : trains) {
//            System.out.println(train);
//        }
//        
//        TrackDAO trackDao = new TrackDAOImpl();
//        List<Track> tracks = trackDao.getAll();
//        for(Track track : tracks) {
//            System.out.println(track);
//        }
        
//        GraphDB graph = new GraphDB();
//        graph.initGraphDB();
//        System.out.println(graph.getJourneys(stationDao.getByName("Brno hlavní nádraží"), stationDao.getByName("Praha hlavní nádraží")));
//        graph.shutDownAndRemowe();

         ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("Spring bean: " + name);
        }

        if ((GraphDB) context.getBean(GraphDB.class) == null) {
            System.out.println("GraphBD is null");
        } else {
            GraphDB graphDB = (GraphDB) context.getBean(GraphDB.class);
            System.out.println(graphDB.getJourneys(stationDao.getByName("Brno hlavní nádraží"), stationDao.getByName("Praha hlavní nádraží")));
            graphDB.shutDownAndRemowe();
        }
    }
}
