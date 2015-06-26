/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb138.jizdenky.service.discount;

import cz.muni.fi.pb138.jizdenky.data.access.model.Journey;
import cz.muni.fi.pb138.jizdenky.service.pojo.Discount;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 * Trieda sluziaca ako vystup uzivatelovi po aplikacii zliav
 * 
 * @author Michal Tomco
 */
public class Result {
    private Set<Discount> discounts;
    private List<Journey> journeys;

    public List<Journey> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }
    
}
