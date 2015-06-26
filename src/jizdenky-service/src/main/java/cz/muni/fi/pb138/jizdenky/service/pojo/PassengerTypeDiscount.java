package cz.muni.fi.pb138.jizdenky.service.pojo;

import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Oliver Pentek
 */
public class PassengerTypeDiscount extends AbstractIndividualDiscount {
    @Nonnull
    private final PassengerType passengerType;

    public PassengerTypeDiscount(@Nonnull PassengerType passengerType, int discountValue, String name) {
        super(discountValue, name);
        Validate.notNull(passengerType);
        this.passengerType = passengerType;
    }
    
    @Nonnull
    public PassengerType getPassengerType() {
        return passengerType;
    }
    
    /**
     * {@inheritDoc }
     * @param passenger
     * @return 
     */
    @Override
    public boolean isApplicable(Passenger passenger) {
        return this.passengerType.equals(passenger.getType());
    }

}
