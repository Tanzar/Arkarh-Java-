/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Converter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.*;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitEffectGroup;

/**
 *
 * @author Tanzar
 */
public class UnitsConverter {
    
    public static Unit convert(UnitEntity entity){
        Unit unit = new Unit();
        int id = entity.getId();
        unit.setId(id);
        convertBasicFromEntityToUnit(unit, entity);
        Offensive offensiveStats = convertToOffensive(entity);
        unit.setOffensive(offensiveStats);
        Defensive defensiveStats = convertToDefensive(entity);
        unit.setDefensive(defensiveStats);
        Special specialStats = convertToSpecial(entity);
        unit.setSpecial(specialStats);
        return unit;
    }
    
    private static void convertBasicFromEntityToUnit(Unit unit, UnitEntity entity){
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
    
    private static Offensive convertToOffensive(UnitEntity entity){
        int attack = entity.getAttack();
        int spellPower = entity.getSpellPower();
        int damage = entity.getDamage();
        int healing = entity.getHealing();
        Offensive stats = new Offensive(attack, spellPower, damage, healing);
        return stats;
    }
    
    private static Defensive convertToDefensive(UnitEntity entity){
        int defense = entity.getDefense();
        int armor = entity.getArmor();
        int ward = entity.getWard();
        int health = entity.getHealth();
        Defensive stats = new Defensive(defense, armor, ward, health);
        return stats;
    }
    
    private static Special convertToSpecial(UnitEntity entity){
        int upkeep = entity.getUpkeep();
        int speed = entity.getSpeed();
        int morale = entity.getMorale();
        Special stats = new Special(upkeep, speed, morale);
        return stats;
    }
    
    public static UnitEntity convert(Unit unit){
        UnitEntity entity = new UnitEntity();
        entity.setId(unit.getId());
        entity.setName(unit.getName());
        entity.setAssetName(unit.getAssetName());
        entity.setFraction(unit.getFraction().toString());
        entity.setRole(unit.getRole().toString());
        entity.setTier(unit.getTier().toString());
        entity.setCategory(unit.getCategory().toString());
        convertOffensive(unit, entity);
        convertDefensive(unit, entity);
        convertSpecial(unit, entity);
        return entity;
    }
    
    private static void convertOffensive(Unit unit, UnitEntity entity){
        Offensive offensiveStats = unit.getOffensive();
        entity.setAttack(offensiveStats.getAttack());
        entity.setSpellPower(offensiveStats.getSpellPower());
        entity.setDamage(offensiveStats.getDamage());
        entity.setHealing(offensiveStats.getBaseHealingValue());
    }
    
    private static void convertDefensive(Unit unit, UnitEntity entity){
        Defensive defensiveStats = unit.getDefensive();
        entity.setDefense(defensiveStats.getDefense());
        entity.setArmor(defensiveStats.getArmor());
        entity.setWard(defensiveStats.getWard());
        entity.setHealth(defensiveStats.getBaseHealth());
    }
    
    private static void convertSpecial(Unit unit, UnitEntity entity){
        Special specialStats = unit.getSpecial();
        entity.setUpkeep(specialStats.getUpkeep());
        entity.setSpeed(specialStats.getSpeed());
        entity.setMorale(specialStats.getBaseMorale());
    }
    
    public static Passive convert(UnitEffectEntity entity){
        if(entity.getEffectGroup().equals("passive")){
            return convertToPassive(entity);
        }
        else{
            return null;
        }
    }
    
    private static Passive convertToPassive(UnitEffectEntity entity){
        String json = entity.getEffect();
        Gson gson = new Gson();
        JsonObject object = gson.fromJson(json, JsonObject.class);
        int stacks = getInt(object, "stacks");
        int stacksLimit = getInt(object, "stacksLimit");
        boolean isStackable = getBool(object, "isStackable");
        int value = getInt(object, "value");
        PassiveEffect effect = getEffect(object, "passiveEffect");
        EffectSchool school = getSchool(object, "school");
        String name = entity.getEffectName();
        String asset = entity.getAssetName();
        Passive passive = new Passive(name, stacks, stacksLimit, isStackable, value, effect, school);
        passive.setAsset(asset);
        passive.setId(entity.getId());
        return passive;
    }
    
    private static int getInt(JsonObject object, String member){
        JsonElement element = object.get(member);
        int value = element.getAsInt();
        return value;
    }
    
    private static boolean getBool(JsonObject object, String member){
        JsonElement element = object.get(member);
        boolean value = element.getAsBoolean();
        return value;
    }
    
    private static PassiveEffect getEffect(JsonObject object, String member){
        JsonElement element = object.get(member);
        String value = element.getAsString();
        PassiveEffect effect = PassiveEffect.valueOf(value);
        return effect;
    }
    
    private static EffectSchool getSchool(JsonObject object, String member){
        JsonElement element = object.get(member);
        String value = element.getAsString();
        EffectSchool school = EffectSchool.valueOf(value);
        return school;
    }
    
    public static UnitEffectEntity convert(int unitId, Passive passive){
        UnitEffectEntity entity = new UnitEffectEntity();
        entity.setId(passive.getId());
        entity.setUnitId(unitId);
        String name = passive.getName();
        entity.setEffectName(name);
        String asset = passive.getAsset();
        entity.setAssetName(asset);
        entity.setEffectGroup("passive");
        String effectJson = convertToEffectJson(passive);
        entity.setEffect(effectJson);
        return entity;
    }
    
    public static String convertToEffectJson(Passive passive){
        JsonObject object = new JsonObject();
        object.addProperty("stacks", passive.getStacks());
        object.addProperty("stacksLimit", passive.getStacksLimit());
        object.addProperty("isStackable", passive.isStackable());
        object.addProperty("value", passive.getSingleValue());
        PassiveEffect effect = passive.getEffect();
        object.addProperty("passiveEffect", effect.toString());
        EffectSchool school = passive.getSchool();
        object.addProperty("school", school.toString());
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }
    
    public static UnitAbility convertAbility(Unit owner, UnitEffectEntity entity){
        String groupString = entity.getEffectGroup();
        UnitEffectGroup group = UnitEffectGroup.valueOf(groupString);
        switch(group){
            case attack:
                return convertToAttack(owner, entity);
            default:
                return null;
        }
    }
    
    private static Attack convertToAttack(Unit owner, UnitEffectEntity entity){
        return new Attack(entity);
    }
    
}
