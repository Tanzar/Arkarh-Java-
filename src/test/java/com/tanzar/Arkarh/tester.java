/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh;

import com.tanzar.Arkarh.Elements.Assets;
import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.Combat.BattleState;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Position;
import com.tanzar.Arkarh.GamePlay.Combat.Side;
import com.tanzar.Arkarh.GamePlay.Combat.Log.Entry;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Combat.Log.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Equipment.Equipment;
import com.tanzar.Arkarh.GamePlay.Equipment.Slot;
import com.tanzar.Arkarh.GamePlay.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.AttackStyle;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Defensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import com.tanzar.Arkarh.Map.Biomes.BiomeGenerator;
import com.tanzar.Arkarh.Map.Exceptions.GenerationException;
import com.tanzar.Arkarh.Map.MapGenerator;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Tanzar
 */
public class tester {
    
    @Test
    public void test() throws IOException{
        int width = 20;
        Army attack = this.newArmy(4, 8, 6, 8);
        Army def = this.newArmy(2, 10, 6, 8);
        
        Battlefield battlefield = new Battlefield(width, attack, def);
        //CombatReport report = battlefield.fight();
        Side side = new Side(attack, width, BattleSide.attacker);
        int k = 0;
        //side.reorganizeLinesNew();
        //System.out.println(report);
    }
    
    private Army newArmy(int flankers, int warriors, int mages, int shooters){
        Army army = new Army();
        for(int i = 0; i < flankers; i++){
            Unit unit = this.newUnit(Role.flanker, 10, 0, EffectSchool.physical, 20, AttackStyle.single, 5, 25, 15, 200, 20, 3);
            army.addUnit(unit);
        }
        for(int i = 0; i < warriors; i++){
            Unit unit = this.newUnit(Role.warrior, 10, 0, EffectSchool.physical, 5, AttackStyle.single, 20, 50, 50, 400, 10, 2);
            army.addUnit(unit);
        }
        for(int i = 0; i < mages; i++){
            Unit unit = this.newUnit(Role.mage, 5, 10, EffectSchool.arcane, 10, AttackStyle.splash, 5, 10, 60, 300, 5, 1);
            unit.setRole(Role.mage);
            
            army.addUnit(unit);
        }
        for(int i = 0; i < shooters; i++){
            Unit unit = this.newUnit(Role.shooter, 0, 0, EffectSchool.physical, 10, AttackStyle.single, 10, 30, 20, 250, 15, 5);
            army.addUnit(unit);
        }
        return army;
    }
    
    private Unit newUnit(Role role, int attack, int spellPower, EffectSchool damageType, int damage, AttackStyle attackType, int defense, int armor, int ward, int baseHealth, int speed, int range){
        Offensive offensive = new Offensive(attack, spellPower, damageType, damage, 0, attackType);
        Defensive defensive = new Defensive(defense, armor, ward, baseHealth);
        Special special = new Special(1, speed, range, 100);
        Unit unit = new Unit();
        unit.setRole(role);
        unit.setOffensive(offensive);
        unit.setDefensive(defensive);
        unit.setSpecial(special);
        return unit;
    }
}
