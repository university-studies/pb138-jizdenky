package cz.muni.fi.pb138.jizdenky.web.listener;

import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAO;
import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAOImpl;
import cz.muni.fi.pb138.jizdenky.data.access.dao.TrackDAOImpl;
import cz.muni.fi.pb138.jizdenky.data.access.dao.TrainDAOImpl;
import cz.muni.fi.pb138.jizdenky.data.access.graph.GraphDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by pavol on 25.5.2014.
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    @Autowired
    private GraphDB graphDB;

    @Override
    public void contextInitialized(ServletContextEvent ev) {
        log.info("Web initialized");

        WebApplicationContextUtils
                .getRequiredWebApplicationContext(ev.getServletContext())
                .getAutowireCapableBeanFactory()
                .autowireBean(this);
    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        log.info("Web end");

        graphDB.shutDownAndRemowe();
    }
}
