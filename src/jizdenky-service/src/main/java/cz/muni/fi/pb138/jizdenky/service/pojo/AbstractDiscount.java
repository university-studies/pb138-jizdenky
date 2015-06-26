package cz.muni.fi.pb138.jizdenky.service.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Oliver Pentek
 */
public abstract class AbstractDiscount implements Discount, Comparable<AbstractDiscount> {

    /**
     * Hodnota zlavy v percentach.
     */
    private final int discountValue;
    
    private String name;

    public AbstractDiscount(int discountValue, String name) {
        this.discountValue = discountValue;
        this.name = name;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc }
     *
     * @param standardPrice
     * @return
     */
    @Override
    public BigDecimal apply(BigDecimal standardPrice) {
        return standardPrice.multiply(BigDecimal.valueOf(1 - (this.getDiscountValue() / 100)));
    }
    

    public int compareTo(AbstractDiscount discount) {
        return this.getDiscountValue() - discount.getDiscountValue();
    }
}
