/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Containers.UnitEffects;
import com.tanzar.Arkarh.Containers.UnitEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Converter.UnitsConverter;
import com.tanzar.Arkarh.DAO.UnitEffectsDAO;
import com.tanzar.Arkarh.DAO.UnitsDAO;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Defensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Status;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitEffectGroup;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author spako
 */
@Service
public class UnitsService {
    
    @Autowired
    private UnitsDAO unitsDAO;
    
    @Autowired
    private UnitEffectsDAO effectsDAO;
    
    public UnitEntities getAllUnitEntities(){
        return this.unitsDAO.getAll();
    }
    
    public Units getAll(){
        UnitEntities entities = this.unitsDAO.getAll();
        Units units = new Units();
        for(int i = 0; i < entities.size(); i++){
            UnitEntity entity = entities.get(i);
            Unit unit = UnitsConverter.convert(entity);
            UnitAbilities abilities = this.getUnitAbilities(unit);
            unit.setAbilities(abilities);
            units.add(unit);
        }
        return units;
    }
    
    public UnitAbilities getUnitAbilities(Unit unit){
        int id = unit.getId();
        UnitEffects effects = this.effectsDAO.getByUnitId(id);
        UnitAbilities abilities = new UnitAbilities();
        for(UnitEffectEntity entity: effects.toArray()){
            UnitAbility ability = UnitsConverter.convertAbility(unit, entity);
            abilities.add(ability);
        }
        return abilities;
    }
    
    public void add(String unitJson){
        Unit newUnit = new Unit(unitJson);
        this.add(newUnit);
    }
    
    public void add(Unit unit){
        UnitEntity entity = UnitsConverter.convert(unit);
        int id = this.unitsDAO.add(entity);
        unit.setId(id);
        UnitAbilities abilities = unit.getAbilities();
        for(UnitAbility ability: abilities.toArray()){
            UnitEffectEntity effect = ability.convert(unit);
            this.effectsDAO.add(effect);
        }
    }
    
    public void update(String unitJson){
        Unit unit = new Unit(unitJson);
        this.update(unit);
    }
    
    public void update(Unit unit){
        UnitEntity entity = UnitsConverter.convert(unit);
        this.unitsDAO.update(entity);
        UnitAbilities abilities = unit.getAbilities();
        for(UnitAbility ability: abilities.toArray()){
            UnitEffectEntity effect = ability.convert(unit);
            this.effectsDAO.update(effect);
        }
    }
    
    public void remove(Unit unit){
        int id = unit.getId();
        this.unitsDAO.delete(id);
    }
    
    public void removeUnit(int id){
        this.unitsDAO.delete(id);
    }
    
    public void remove(UnitAbility ability){
        int id = ability.getId();
        this.effectsDAO.delete(id);
    }
    
    public String newUnitAsJson(){
        Unit unit = new Unit();
        UnitEffectEntity entity = new UnitEffectEntity();
        entity.setEffectName("Attack");
        entity.setEffectGroup(UnitEffectGroup.attack.toString());
        Json json = new Json();
        json.add("attackStyle", TargetsSelection.single.toString());
        json.add("range", 1);
        json.add("areaSize", 1);
        json.add("school", EffectSchool.physical.toString());
        String attackJson = json.formJson();
        entity.setEffect(attackJson);
        entity.setAssetName("none.png");
        Attack attack = new Attack(entity);
        unit.addAbility(attack);
        String result = Json.toJson(unit);
        return result;
    }
}
