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
import com.tanzar.Arkarh.GamePlay.Units.Stats.Defensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Status;
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
        unit.getStatus().setPosition(entity.getId());
        return unit;
    }
    
    private void setupBasic(Unit unit, UnitEntity entity){
        String name = entity.getName();
        unit.setName(name);
        String assetName = entity.getAssetName();
        unit.setAssetName(assetName);
        String fractionString = entity.getFraction();
        Fraction fraction = Fraction.valueOf(fractionString);
        unit.setFraction(fraction);
        String roleString = entity.getRole();
        Role role = Role.valueOf(roleString);
        unit.setRole(role);
        String tierString = entity.getTier();
        Tier tier = Tier.valueOf(tierString);
        unit.setTier(tier);
        String categoryString = entity.getCategory();
        Category category = Category.valueOf(categoryString);
        unit.setCategory(category);
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
        Offensive stats = new Offensive(attack, spellPower, effect, damage, healing, attackType);
        unit.setOffensive(stats);
    }
    
    private void setupDefensiveStats(Unit unit, UnitEntity entity){
        int defense = entity.getDefense();
        int armor = entity.getArmor();
        int ward = entity.getWard();
        int health = entity.getHealth();
        Defensive stats = new Defensive(defense, armor, ward, health);
        unit.setDefensive(stats);
    }
    
    private void setupSpecialStats(Unit unit, UnitEntity entity){
        int upkeep = entity.getUpkeep();
        int speed = entity.getSpeed();
        int range = entity.getRange();
        int morale = entity.getMorale();
        Special stats = new Special(upkeep, speed, range, morale);
        unit.setSpecial(stats);
    }
    
    private UnitEntity convert(Unit unit){
        UnitEntity entity = new UnitEntity();
        Status status = unit.getStatus();
        entity.setId(status.getPosition());
        entity.setName(unit.getName());
        entity.setAssetName(unit.getAssetName());
        entity.setFraction(unit.getFraction().toString());
        entity.setRole(unit.getRole().toString());
        entity.setTier(unit.getTier().toString());
        entity.setCategory(unit.getCategory().toString());
        this.convertOffensive(unit, entity);
        this.convertDefensive(unit, entity);
        this.convertSpecial(unit, entity);
        return entity;
    }
    
    private void convertOffensive(Unit unit, UnitEntity entity){
        Offensive offensiveStats = unit.getOffensive();
        entity.setAttack(offensiveStats.getAttack());
        entity.setSpellPower(offensiveStats.getSpellPower());
        entity.setEffectType(offensiveStats.getSchool().toString());
        entity.setDamage(offensiveStats.getDamage());
        entity.setHealing(offensiveStats.getBaseHealingValue());
        entity.setAttackType(offensiveStats.getAttackType().toString());
    }
    
    private void convertDefensive(Unit unit, UnitEntity entity){
        Defensive defensiveStats = unit.getDefensive();
        entity.setDefense(defensiveStats.getDefense());
        entity.setArmor(defensiveStats.getArmor());
        entity.setWard(defensiveStats.getWard());
        entity.setHealth(defensiveStats.getBaseHealth());
    }
    
    private void convertSpecial(Unit unit, UnitEntity entity){
        Special specialStats = unit.getSpecial();
        entity.setUpkeep(specialStats.getUpkeep());
        entity.setSpeed(specialStats.getSpeed());
        entity.setRange(specialStats.getRange());
        entity.setMorale(specialStats.getBaseMorale());
    }
}
