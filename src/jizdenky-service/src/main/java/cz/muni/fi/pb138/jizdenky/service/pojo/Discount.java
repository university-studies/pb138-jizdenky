package cz.muni.fi.pb138.jizdenky.service.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Oliver Pentek
 */
public interface Discount {
    /**
     * Aplikuje zlavu na povodnu cenu.
     * @param standardPrice povodna cena
     * @return cena so zlavou
     */
    BigDecimal apply(BigDecimal standardPrice);
    String getName();
    
}
