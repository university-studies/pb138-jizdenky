/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb138.jizdenky.service.discount;

import cz.muni.fi.pb138.jizdenky.service.pojo.UserInput;

/**
 *
 * @author Mito
 */
public interface DiscountResolver {
    Result resolve(UserInput input);    
}
