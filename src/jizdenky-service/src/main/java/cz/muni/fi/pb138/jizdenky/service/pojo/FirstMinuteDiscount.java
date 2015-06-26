/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb138.jizdenky.service.pojo;

import java.util.Date;
import org.joda.time.LocalDate;

/**
 *
 * @author Mito
 */
public class FirstMinuteDiscount extends AbstractFirstMinuteDiscount {
    
    private final int daysInAdvance;

    public FirstMinuteDiscount(int daysInAdvance, int discountValue, String name) {
        super(discountValue, name);  
        this.daysInAdvance = daysInAdvance;
    }

    @Override
    public boolean isApplicable(LocalDate dayOfOrder) {
        return dayOfOrder.minusDays(daysInAdvance).isAfter(LocalDate.now());
    }
    
}
