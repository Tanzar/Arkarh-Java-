/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

/**
 *
 * @author spako
 */
public enum UnitAbilityGroup {
    none,
    attack,//target, za ile
    heal,//target, za ile
    buff,
    ressurect,//target, ile % hp
    reincarnate,
    demoralize,
    necromancy,//tagret, jako który unit
    summon,//return unit, zwracaj target w parametrze(?), kogo przywołuje
    
}
