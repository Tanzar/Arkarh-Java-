/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.Equipment.Equipment;
import com.tanzar.Arkarh.GamePlay.Equipment.Item;

/**
 *
 * @author spako
 */
public class Leader {
    
    private int level;
    private int experience;
    private int attack;
    private int defense;
    private int spellPower;
    
    private Equipment equipment;
    
    public Leader(){
        this.level = 1;
        this.experience = 0;
        this.attack = 1;
        this.defense = 1;
        this.spellPower = 0;
    }
    
    
}
