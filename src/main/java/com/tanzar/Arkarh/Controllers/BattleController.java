/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.tanzar.Arkarh.GamePlay.Battlefield.Battlefield;
import com.tanzar.Arkarh.GamePlay.CombatLog.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.AttackType;
import com.tanzar.Arkarh.GamePlay.Units.DamageType;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author spako
 */
@RestController
public class BattleController {
    
    @RequestMapping(value = "/testBattle", method = RequestMethod.GET)
    public String getAllTerrains(){
        int width = 10;
        Army attack = this.newArmy(4, 8, 6, 8);
        Army def = this.newArmy(2, 10, 6, 8);
        
        Battlefield battlefield = new Battlefield(width, attack, def);
        String initialSetup = battlefield.toString();
        CombatReport report = battlefield.fight();
        
        //String result = "" + initialSetup + report.toString();
        String result = "";
        Gson gson = new Gson();
        result = gson.toJson(report);
        return result;
    }
    
    private CombatReport test(){
        int width = 10;
        Army attack = this.newArmy(4, 8, 6, 8);
        Army def = this.newArmy(2, 10, 6, 8);
        
        Battlefield battlefield = new Battlefield(width, attack, def);
        
        CombatReport report = battlefield.fight();
        
        return report;
    }
    
    private Army newArmy(int flankers, int warriors, int mages, int shooters){
        Army army = new Army();
        for(int i = 0; i < flankers; i++){
            Unit unit = this.newUnit(Role.flanker, 10, 0, DamageType.physical, 20, AttackType.single, 5, 25, 15, 200, 20, 3);
            unit.setUnitName(unit.getRole().toString());
            army.addUnit(unit);
        }
        for(int i = 0; i < warriors; i++){
            Unit unit = this.newUnit(Role.warrior, 10, 0, DamageType.physical, 5, AttackType.single, 20, 50, 50, 400, 10, 2);
            unit.setUnitName(unit.getRole().toString());
            army.addUnit(unit);
        }
        for(int i = 0; i < mages; i++){
            Unit unit = this.newUnit(Role.mage, 5, 10, DamageType.arcane, 10, AttackType.splash, 5, 10, 60, 300, 5, 1);
            unit.setUnitName(unit.getRole().toString());
            unit.setRole(Role.mage);
            
            army.addUnit(unit);
        }
        for(int i = 0; i < shooters; i++){
            Unit unit = this.newUnit(Role.shooter, 10, 0, DamageType.physical, 10, AttackType.single, 10, 30, 20, 250, 15, 5);
            unit.setUnitName(unit.getRole().toString());
            army.addUnit(unit);
        }
        return army;
    }
    
    private Unit newUnit(Role role, int attack, int spellPower, DamageType damageType, int damage, AttackType attackType, int defense, int armor, int ward, int baseHealth, int speed, int range){
        Unit unit = new Unit("", role, -1, attack, spellPower, damageType, damage, attackType, defense, armor, ward, baseHealth, 1, speed, range, 100);
        return unit;
    }
}
