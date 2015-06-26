package cz.muni.fi.pb138.jizdenky.service.discount;

import cz.muni.fi.pb138.jizdenky.service.pojo.FirstMinuteDiscount;
import cz.muni.fi.pb138.jizdenky.service.pojo.GroupDiscount;
import cz.muni.fi.pb138.jizdenky.service.pojo.PassengerType;
import cz.muni.fi.pb138.jizdenky.service.pojo.PassengerTypeDiscount;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Trieda sluziaca na ziskanie moznych zliav.
 *
 * @author Oliver Pentek
 */
public class DiscountProvider {

    /**
     * Vrati mnozinu moznych zliav.
     *
     * @return
     */
    public static SortedSet<PassengerTypeDiscount> getIndividualDiscounts() {
        final SortedSet<PassengerTypeDiscount> discounts = new TreeSet<>();
        
        discounts.add(new PassengerTypeDiscount(PassengerType.NORMAL, 0, "Normal"));
        discounts.add(new PassengerTypeDiscount(PassengerType.STUDENT, 40, "Student"));
        discounts.add(new PassengerTypeDiscount(PassengerType.SENIOR, 35, "Senior")); // hahaha dochodcovia platia viac :D

        return discounts;
    }

    public static SortedSet<GroupDiscount> getGroupDiscounts() {
        final SortedSet<GroupDiscount> discounts = new TreeSet<>();

        discounts.add(new GroupDiscount(3, 40, "Group Discount 3+"));
        discounts.add(new GroupDiscount(7, 50, "Group Discount 7+"));
        discounts.add(new GroupDiscount(10, 55, "Group Discount 10+"));
        
        return discounts;
    }
    
    public static SortedSet<FirstMinuteDiscount> getFirstMinuteDiscounts(){
        final SortedSet<FirstMinuteDiscount> discounts = new TreeSet<>();
        
        discounts.add(new FirstMinuteDiscount(7, 15, "First Minute: Week in Advance"));
        discounts.add(new FirstMinuteDiscount(30, 30, "First Minute: Month in Advance"));
        
        return discounts;
    }

}
