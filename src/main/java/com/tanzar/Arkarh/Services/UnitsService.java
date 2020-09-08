/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Containers.UnitEntities;
import com.tanzar.Arkarh.DAO.UnitEffectsDAO;
import com.tanzar.Arkarh.DAO.UnitsDAO;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.Units.AttackStyle;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
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
    
    public Units getAll(){
        UnitEntities entities = this.unitsDAO.getAll();
        Units units = new Units();
        for(int i = 0; i < entities.size(); i++){
            UnitEntity entity = entities.get(i);
            Unit unit = this.convert(entity);
            units.add(unit);
        }
        return units;
    }
    
    public void add(Unit unit){
        UnitEntity entity = this.convert(unit);
        this.unitsDAO.add(entity);
    }
    
    public void update(Unit unit){
        UnitEntity entity = this.convert(unit);
        this.unitsDAO.updateUnit(entity);
    }
    
    public void remove(Unit unit){
        UnitEntity entity = this.convert(unit);
        int id = entity.getId();
        this.unitsDAO.delete(id);
    }
    
    private Unit convert(UnitEntity entity){
        Unit unit = new Unit();
        this.setupBasic(unit, entity);
        this.setupCombatStats(unit, entity);
        this.setupDefensiveStats(unit, entity);
        this.setupSpecialStats(unit, entity);
        unit.setPosition(entity.getId());
        return unit;
    }
    
    private void setupBasic(Unit unit, UnitEntity entity){
        String name = entity.getName();
        String assetName = entity.getAssetName();
        String fractionString = entity.getFraction();
        Fraction fraction = Fraction.valueOf(fractionString);
        String roleString = entity.getRole();
        Role role = Role.valueOf(roleString);
        String tierString = entity.getTier();
        Tier tier = Tier.valueOf(tierString);
        String categoryString = entity.getCategory();
        Category category = Category.valueOf(categoryString);
        unit.setBasic(name, assetName, fraction, role, tier, category, -1, BattleSide.none);
    }
    
    private void setupCombatStats(Unit unit, UnitEntity entity){
        int attack = entity.getAttack();
        int spellPower = entity.getSpellPower();
        String effectString = entity.getEffectType();
        EffectSchool effect = EffectSchool.valueOf(effectString);
        int damage = entity.getDamage();
        int healing = entity.getHealing();
        String attackString = entity.getAttackType();
        AttackStyle attackType = AttackStyle.valueOf(attackString);
        unit.setCombatStats(attack, spellPower, effect, damage, healing, attackType);
    }
    
    private void setupDefensiveStats(Unit unit, UnitEntity entity){
        int defense = entity.getDefense();
        int armor = entity.getArmor();
        int ward = entity.getWard();
        int health = entity.getHealth();
        unit.setDefensiveStats(defense, armor, ward, health);
    }
    
    private void setupSpecialStats(Unit unit, UnitEntity entity){
        int upkeep = entity.getUpkeep();
        int speed = entity.getSpeed();
        int range = entity.getRange();
        int morale = entity.getMorale();
        unit.setSpecialStats(upkeep, speed, range, morale);
    }
    
    private UnitEntity convert(Unit unit){
        UnitEntity entity = new UnitEntity();
        entity.setId(unit.getPosition());
        entity.setName(unit.getName());
        entity.setAssetName(unit.getAssetName());
        entity.setFraction(unit.getFraction().toString());
        entity.setRole(unit.getRole().toString());
        entity.setTier(unit.getTier().toString());
        entity.setCategory(unit.getCategory().toString());
        entity.setAttack(unit.getAttack());
        entity.setSpellPower(unit.getSpellPower());
        entity.setEffectType(unit.getEffectType().toString());
        entity.setDamage(unit.getDamage());
        entity.setHealing(unit.getBaseHealingValue());
        entity.setAttackType(unit.getAttackType().toString());
        entity.setDefense(unit.getDefense());
        entity.setArmor(unit.getArmor());
        entity.setWard(unit.getWard());
        entity.setHealth(unit.getBaseHealth());
        entity.setUpkeep(unit.getUpkeep());
        entity.setSpeed(unit.getSpeed());
        entity.setRange(unit.getRange());
        entity.setMorale(unit.getBaseMorale());
        return entity;
    }
}
