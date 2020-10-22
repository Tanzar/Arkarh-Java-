/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Containers.UnitAbilityEntities;
import com.tanzar.Arkarh.Containers.UnitEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.AbilityFactory;
import com.tanzar.Arkarh.DAO.UnitAbilitiesDAO;
import com.tanzar.Arkarh.DAO.UnitsDAO;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.TargetsGroup;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitAbilityGroup;
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
    private UnitAbilitiesDAO abilitiesDAO;
    
    public UnitEntities getAllUnitEntities(){
        return this.unitsDAO.getAll();
    }
    
    public Units getAll(){
        UnitEntities entities = this.unitsDAO.getAll();
        Units units = new Units();
        for(int i = 0; i < entities.size(); i++){
            UnitEntity entity = entities.get(i);
            Unit unit = new Unit(entity);
            UnitAbilities abilities = this.getUnitAbilities(unit);
            unit.setAbilities(abilities);
            units.add(unit);
        }
        return units;
    }
    
    public UnitAbilities getUnitAbilities(Unit unit){
        int id = unit.getId();
        UnitAbilityEntities effects = this.abilitiesDAO.getByUnitId(id);
        UnitAbilities abilities = new UnitAbilities();
        for(UnitAbilityEntity entity: effects.toArray()){
            UnitAbility ability = AbilityFactory.convertAbility(entity);
            abilities.add(ability);
        }
        return abilities;
    }
    
    public UnitAbilities getUnitAbilities(int id){
        UnitAbilityEntities effects = this.abilitiesDAO.getByUnitId(id);
        UnitAbilities abilities = new UnitAbilities();
        for(UnitAbilityEntity entity: effects.toArray()){
            UnitAbility ability = AbilityFactory.convertAbility(entity);
            abilities.add(ability);
        }
        return abilities;
    }
    
    public UnitAbilityEntities getUnitAbilitiesAsEntities(int id){
        UnitAbilityEntities abilities = this.abilitiesDAO.getByUnitId(id);
        return abilities;
    }
    
    public void add(String unitJson){
        Unit newUnit = new Unit(unitJson);
        this.add(newUnit);
    }
    
    public void add(Unit unit){
        UnitEntity entity = new UnitEntity(unit);
        int id = this.unitsDAO.add(entity);
        unit.setId(id);
        UnitAbilities abilities = unit.getAbilities();
        for(UnitAbility ability: abilities.toArray()){
            UnitAbilityEntity effect = ability.convert(unit);
            this.abilitiesDAO.add(effect);
        }
    }
    
    public void addAbility(String abilityJson){
        Json json = new Json(abilityJson);
        UnitAbility ability = AbilityFactory.convert(json);
        UnitAbilityEntity entity = ability.convert(json.getInt("unitId"));
        this.abilitiesDAO.add(entity);
    }
    
    public void update(String unitJson){
        Unit unit = new Unit(unitJson);
        this.update(unit);
    }
    
    public void update(Unit unit){
        UnitEntity entity = new UnitEntity(unit);
        this.unitsDAO.update(entity);
        UnitAbilities abilities = unit.getAbilities();
        for(UnitAbility ability: abilities.toArray()){
            UnitAbilityEntity effect = ability.convert(unit);
            this.abilitiesDAO.update(effect);
        }
    }
    
    public void updateAbility(String abilityJson){
        Json json = new Json(abilityJson);
        UnitAbility ability = AbilityFactory.convert(json);
        UnitAbilityEntity entity = ability.convert(json.getInt("unitId"));
        this.abilitiesDAO.update(entity);
    }
    
    public void remove(Unit unit){
        int id = unit.getId();
        this.unitsDAO.delete(id);
    }
    
    public void removeUnit(int id){
        this.unitsDAO.delete(id);
    }
    
    public void removeAbility(int id){
        this.abilitiesDAO.delete(id);
    }
    
    public void remove(UnitAbility ability){
        int id = ability.getId();
        this.abilitiesDAO.delete(id);
    }
    
    public String newUnitAsJson(){
        Unit unit = new Unit();
        UnitAbilityEntity entity = new UnitAbilityEntity();
        entity.setEffectName("Attack");
        entity.setEffectGroup(UnitAbilityGroup.attack.toString());
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
    
    public String getAbilitiesPatterns(){
        UnitAbilityGroup[] groups = UnitAbilityGroup.values();
        Json json = new Json();
        for(UnitAbilityGroup group: groups){
            UnitAbility ability = AbilityFactory.newAbility(group);
            if(ability != null){
                Json jsonFormat = ability.toJson();
                json.add(group.toString(), jsonFormat);
            }
        }
        String result = json.formJson();
        return result;
    }
    
    public String getPassiveForm(){
        Passive passive = new Passive();
        String json = Json.toJson(passive);
        return json;
    }
    
    public String getOptions(){
        Json json = new Json();
        Fraction[] fractions = Fraction.values();
        json.add("fractions", fractions);
        Role[] roles = Role.values();
        json.add("roles", roles);
        Tier[] tiers = Tier.values();
        json.add("tiers", tiers);
        Category[] categories = Category.values();
        json.add("categories", categories);
        EffectSchool[] schools = EffectSchool.values();
        json.add("schools", schools);
        TargetsSelection[] attacks = TargetsSelection.values();
        json.add("attacks", attacks);
        UnitAbilityGroup[] group = UnitAbilityGroup.values();
        json.add("group", group);
        TargetsGroup[] targetsGroup = TargetsGroup.values();
        json.add("targetsGroup", targetsGroup);
        PassiveEffect[] passiveEffects = PassiveEffect.values();
        json.add("passiveEffects", passiveEffects);
        return json.formJson();
    }
}
