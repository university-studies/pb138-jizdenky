package cz.muni.fi.pb138.jizdenky.service.discount;

import cz.muni.fi.pb138.jizdenky.data.access.graph.GraphDB;
import cz.muni.fi.pb138.jizdenky.data.access.model.Journey;
import cz.muni.fi.pb138.jizdenky.service.pojo.Discount;
import cz.muni.fi.pb138.jizdenky.service.pojo.FirstMinuteDiscount;
import cz.muni.fi.pb138.jizdenky.service.pojo.GroupDiscount;
import cz.muni.fi.pb138.jizdenky.service.pojo.Passenger;
import cz.muni.fi.pb138.jizdenky.service.pojo.PassengerType;
import cz.muni.fi.pb138.jizdenky.service.pojo.PassengerTypeDiscount;
import cz.muni.fi.pb138.jizdenky.service.pojo.UserInput;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Trieda implementujuca logiku uplatnovania zliav. TODO vytvorit staticku
 * metodu, ktora uplatni najvyhodnejsie zlavy a vrati objekt, ktory bude
 * nasledne prezentovany uzivatelovi
 *
 * @author Michal Tomco
 */
@Component
public class DiscountResolverImpl implements DiscountResolver {

    @Autowired
    private GraphDB graphDB;

    /**
     * Aplikuje zlavy na dany zoznam tras na zaklade uzivatelskeho vstupu
     *
     * @param input
     * @return
     */
    @Override
    public Result resolve(UserInput input) {
        Result result = new Result();

        List<Journey> journeys = graphDB.getJourneys(input.getFrom(), input.getTo());

        Collections.sort(journeys);

        journeys = journeys.subList(0, 3);

        for (Journey journey : journeys) {
            List<Passenger> passengers = input.getPassengers();
            BigDecimal totalPrice;

            if (passengers.size() < 3) {
                resolveIndividualDiscounts(input, result);
            } else {
                resolveGroupDiscounts(input, result);
            }

            BigDecimal individualPriceBeforeDiscounts = new BigDecimal((journey.getTotalLength() * 2) + 10);

            totalPrice = calculateTotalPriceAfterDiscounts(result, individualPriceBeforeDiscounts);

            journey.setPriceAfterDiscount(totalPrice);

        }
        result.setJourneys(journeys);

        return result;
    }

    private void resolveGroupDiscounts(UserInput input, Result result) {
        Set<Discount> discounts = new HashSet<>();

        GroupDiscount appliedDiscount = null;

        for (GroupDiscount groupDiscount : DiscountProvider.getGroupDiscounts()) {
            if (groupDiscount.isApplicable(input.getPassengers())) {   //aplikuj najvacsiu moznu skup. zlavu, kym sa da
                appliedDiscount = groupDiscount;
            }
        }
        discounts.add(appliedDiscount);

        Discount firstMinute = resolveFirstMinuteDiscount(input);

        if (firstMinute != null) {
            discounts.add(firstMinute);
        }

        result.setDiscounts(discounts);
    }

    private void resolveIndividualDiscounts(UserInput input, Result result) {
        Set<Discount> discounts = new HashSet<>();

        for (Passenger passenger : input.getPassengers()) {
            for (PassengerTypeDiscount individualDiscount : DiscountProvider.getIndividualDiscounts()) {
                if (individualDiscount.isApplicable(passenger)) {
                    discounts.add(individualDiscount);
                }
            }
        }
        Discount firstMinute = resolveFirstMinuteDiscount(input);

        if (firstMinute != null) {
            discounts.add(firstMinute);
        }

        result.setDiscounts(discounts);
    }

    private Discount resolveFirstMinuteDiscount(UserInput input) {
        FirstMinuteDiscount fmd = null;
        for (FirstMinuteDiscount discount : DiscountProvider.getFirstMinuteDiscounts()) {
            if (discount.isApplicable(input.getDayOfDeparture())) {
                fmd = discount;
            }
        }

        return fmd;
    }

    private BigDecimal calculateTotalPriceAfterDiscounts(Result result, BigDecimal individualPrice) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        Discount firstMinute = null;
        Set<Discount> discountsToRemove = new HashSet<>();
        for (Discount discount : result.getDiscounts()) {
            if (discount instanceof PassengerTypeDiscount) {
                totalPrice = totalPrice.add(discount.apply(individualPrice));
                PassengerTypeDiscount passDiscount = (PassengerTypeDiscount) discount;
                if (passDiscount.getPassengerType().equals(PassengerType.NORMAL)) {
                    discountsToRemove.add(discount);
                }
            }
            if (discount instanceof GroupDiscount) {
                for (int i = 0; i < ((GroupDiscount) discount).getPassengerCount(); ++i) {
                    totalPrice = totalPrice.add(discount.apply(individualPrice));
                }
            }
            if (discount instanceof FirstMinuteDiscount) {
                firstMinute = discount;
            }
        }

        result.getDiscounts().removeAll(discountsToRemove);

        if (firstMinute != null) {
            totalPrice = firstMinute.apply(totalPrice);
        }

        return totalPrice;
    }
}
