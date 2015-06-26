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
public abstract class AbstractFirstMinuteDiscount extends AbstractDiscount{

    public AbstractFirstMinuteDiscount(int discountValue, String name) {
        super(discountValue, name);      
    }
    
        /**
     * Zisti, ci je tato zlava aplikovatelna na pozadovane vstupne data.
     *
     * @param dayOfOrder
     * @return
     */
    abstract boolean isApplicable(LocalDate dayOfOrder);   
}
