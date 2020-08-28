/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.TMP;

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
    orderOfTheBlueShield(Alliance.union),
    magesCircle(Alliance.union),
    sentinels(Alliance.sentinels),
    wildOrcs(Alliance.none),
    reptilians(Alliance.none),
    
    
    ;
    
    private Alliance alliance;
    
    Fraction(Alliance alliance){
        this.alliance = alliance;
    }
}
