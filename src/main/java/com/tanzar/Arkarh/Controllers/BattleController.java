/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Defensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
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
            Unit unit = this.newUnit(Role.flanker, 10, 0, EffectSchool.physical, 20, TargetsSelection.single, 5, 25, 15, 200, 20, 3, 1);
            unit.setName(unit.getRole().toString());
            army.addUnit(unit);
        }
        for(int i = 0; i < warriors; i++){
            Unit unit = this.newUnit(Role.warrior, 10, 0, EffectSchool.physical, 5, TargetsSelection.single, 20, 50, 50, 400, 10, 2, 1);
            unit.setName(unit.getRole().toString());
            army.addUnit(unit);
        }
        for(int i = 0; i < mages; i++){
            Unit unit = this.newUnit(Role.mage, 5, 10, EffectSchool.arcane, 10, TargetsSelection.piercing, 5, 10, 60, 300, 5, 3, 2);
            unit.setName(unit.getRole().toString());
            unit.setRole(Role.mage);
            
            army.addUnit(unit);
        }
        for(int i = 0; i < shooters; i++){
            Unit unit = this.newUnit(Role.shooter, 10, 0, EffectSchool.physical, 10, TargetsSelection.single, 10, 30, 20, 250, 15, 5, 1);
            unit.setName(unit.getRole().toString());
            army.addUnit(unit);
        }
        return army;
    }
    
    private Unit newUnit(Role role, int attack, int spellPower, EffectSchool damageType, int damage, TargetsSelection attackType, int defense, int armor, int ward, int baseHealth, int speed, int range, int areaSize){
        Offensive offensive = new Offensive(attack, spellPower, damage);
        Defensive defensive = new Defensive(defense, armor, ward, baseHealth);
        Special special = new Special(1, speed, 100);
        Unit unit = new Unit();
        unit.setRole(role);
        unit.setAssetName(role.name() + ".png");
        unit.setOffensive(offensive);
        unit.setDefensive(defensive);
        unit.setSpecial(special);
        UnitEffectEntity entity = new UnitEffectEntity();
        entity.setAssetName("none.png");
        entity.setId(0);
        entity.setUnitId(unit.getId());
        entity.setEffectGroup("attack");
        entity.setCharges(-1);
        entity.setEffectName("attack");
        Json json = new Json();
        json.add("attackStyle", attackType.toString());
        json.add("range", range);
        json.add("areaSize", areaSize);
        json.add("school", damageType.toString());
        entity.setEffect(json.formJson());
        Attack attackAbility = new Attack(entity);
        unit.addAbility(attackAbility);
        return unit;
    }
}
