/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb138.jizdenky.service.pojo;

import java.util.List;
import javax.annotation.Nonnull;

/**
 *
 * @author Mito
 */
public class GroupDiscount extends AbstractGroupDiscount {
    @Nonnull
    private final int passengerCount;

    
    public GroupDiscount(int passengerCount, int discountValue, String name) {
        super(discountValue, name);  
        this.passengerCount = passengerCount;
    }

    public int getPassengerCount() {
        return passengerCount;
    }    
    
    @Override
    public boolean isApplicable(List<Passenger> passengers) {
        return passengers.size() > this.passengerCount;
    }    
}
