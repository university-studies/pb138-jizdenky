package cz.muni.fi.pb138.jizdenky.service.pojo;

/**
 *
 * @author Oliver Pentek
 */
public abstract class AbstractIndividualDiscount extends AbstractDiscount {

    public AbstractIndividualDiscount(int discountValue, String name) {
        super(discountValue, name);
    }
    
    /**
     * Zisti, ci je tato zlava aplikovatelna na daneho pasaziera.
     *
     * @param input
     * @return
     */
    abstract boolean isApplicable(Passenger passenger);

}
