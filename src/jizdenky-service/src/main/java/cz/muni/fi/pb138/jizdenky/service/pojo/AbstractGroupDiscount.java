package cz.muni.fi.pb138.jizdenky.service.pojo;

import java.util.List;

/**
 *
 * @author Oliver Pentek
 */
public abstract class AbstractGroupDiscount extends AbstractDiscount {

    public AbstractGroupDiscount(int discountValue, String name) {
        super(discountValue, name);  
    }
    /**
     * Zisti, ci je tato zlava aplikovatelna na pozadovane vstupne data.
     *
     * @param passengers
     * @return
     */
    abstract boolean isApplicable(List<Passenger> passengers);

}
