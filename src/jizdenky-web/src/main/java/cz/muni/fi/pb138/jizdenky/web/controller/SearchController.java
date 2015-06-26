package cz.muni.fi.pb138.jizdenky.web.controller;

import cz.muni.fi.pb138.jizdenky.data.access.dao.StationDAO;
import cz.muni.fi.pb138.jizdenky.service.discount.DiscountResolver;
import cz.muni.fi.pb138.jizdenky.service.discount.Result;
import cz.muni.fi.pb138.jizdenky.service.pojo.Passenger;
import cz.muni.fi.pb138.jizdenky.service.pojo.PassengerType;
import cz.muni.fi.pb138.jizdenky.service.pojo.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

/**
 * Created by pavol on 7.6.2014.
 */
@Controller
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private DiscountResolver resolver;
    @Autowired
    StationDAO stations;

    @RequestMapping(value = "/*")
    public ModelAndView connection(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("connection");
        modelAndView.addObject("stations", stations.getAll());

        if (StringUtils.isNotBlank(request.getParameter("sbmBtn"))) {
            UserInput input = new UserInput();
            String error = null;
            final String from = request.getParameter("from");
            if(StringUtils.isBlank(from)) {
                error = "From is mandatory.";
            } else {
                input.setFrom(stations.getByName(from));
            }
            final String to = request.getParameter("to");
            if(StringUtils.isBlank(to)) {
                error = "To is mandatory.";
            } else {
                input.setTo(stations.getByName(to));
            }
            final String date = request.getParameter("date");
            if(StringUtils.isBlank(date)) {
                error = "Date is mandatory.";
            }
            LocalDate localDate;
            try {
                localDate = LocalDate.parse(request.getParameter("date"));
                input.setDayOfDeparture(localDate);
            } catch (IllegalArgumentException ex) {
                error = "Wrong date format.";
            }
            Integer passengersNumber = Integer.valueOf(request.getParameter("passengers"));

            List<Passenger> passengers = new ArrayList<>();

            for (int i = 0; i < passengersNumber; i++) {
                passengers.add(new Passenger());
            }

            if (passengers.size() > 0l) {
                passengers.get(0).setType(PassengerType.valueOf(request.getParameter("passenger-type-1").toUpperCase()));
            }

            if (passengers.size() > 1) {
                passengers.get(1).setType(PassengerType.valueOf(request.getParameter("passenger-type-2").toUpperCase()));
            }

            input.setPassengers(passengers);
            
            

            if (error == null) {
                Result result = resolver.resolve(input);
//                modelAndView.addObject("fromResult", input.getFrom());
//                modelAndView.addObject("toResult", input.getTo());
//                modelAndView.addObject("dateResult", input.getDayOfDeparture());
                modelAndView.addObject("input", input);

                modelAndView.addObject("result", result);
            } else {
                modelAndView.addObject("error", error);
            }
        }

        return modelAndView;
    }
}
