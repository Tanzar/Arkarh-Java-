/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.TMP;

import com.tanzar.Arkarh.Converter.Json;

/**
 *
 * @author spako
 */
public enum Fraction {
    none(Alliance.none),
    fallen(Alliance.pactOfTheFallen),
    demons(Alliance.pactOfTheFallen),
    mageguard(Alliance.mageguard),
    forestKingdom(Alliance.councilOfTribes),
    tribes(Alliance.councilOfTribes),
    dwarves(Alliance.union),
    humans(Alliance.union),
    orderOfTheAzureShield(Alliance.union),
    magesCircle(Alliance.union),
    sentinels(Alliance.sentinels),
    wildOrcs(Alliance.none),
    reptilians(Alliance.none),
    
    
    ;
    
    private Alliance alliance;
    
    Fraction(Alliance alliance){
        this.alliance = alliance;
    }
    
    public Alliance getAlliance(){
        return this.alliance;
    }
    
    public static Json[] getRelations(){
        Fraction[] allFractions = Fraction.values();
        int count = allFractions.length;
        Json[] fractions = new Json[count];
        for(int i = 0; i < count; i++){
            Json item = new Json();
            item.add("fraction", allFractions[i]);
            item.add("alliance", allFractions[i].getAlliance());
            fractions[i] = item;
        }
        return fractions;
    }
}
