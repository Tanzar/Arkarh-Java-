/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.Combat.Log.Actions;
import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.Combat.Log.ReportEntry;
import com.tanzar.Arkarh.GamePlay.Modifiers.Active;
import com.tanzar.Arkarh.GamePlay.Modifiers.ActiveEffect;
import com.tanzar.Arkarh.GamePlay.Modifiers.Actives;
import com.tanzar.Arkarh.GamePlay.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;

/**
 *
 * @author spako
 */
public class Unit implements Comparable<Unit>{
    
    //basic
    private String name;
    private String assetName;
    private Fraction fraction;
    private Role role;
    private Tier tier;
    private Category category;
    private int position;
    private BattleSide side = BattleSide.none;
    private Passives passives;
    private Actives actives;
    
    //limits
    private final int maxArmor = 75;
    private final int maxWard = 75;
    
    //bonuses
    private double spellPowerBonus = 0.05;
    private double atkDefBonus = 0.05;
    
    //combat stats
    private int attack;
    private int spellPower;
    private EffectSchool effectType;
    private int damage;
    private int baseHealingValue = 0;
    private AttackStyle attackType;
    
    //defensive stats
    private int defense;
    private int armor;
    private int ward;
    private int baseHealth;
    private int health;
    
    //special
    private int upkeep;
    private int speed;
    private int range;
    private int baseMorale;
    private int moraleLoss = 10;
    private int morale;

    public Unit() {
        this.name = "Unit";
        this.assetName = "none";
        this.fraction = Fraction.none;
        this.role = Role.warrior;
        this.tier = Tier.base;
        this.category = Category.living;
        this.passives = new Passives();
        this.actives = new Actives();
        this.attack = 0;
        this.damage = 1;
        this.defense = 0;
        this.armor = 0;
        this.spellPower = 0;
        this.effectType = EffectSchool.physical;
        this.attackType = AttackStyle.single;
        this.ward = 0;
        this.baseHealth = 1;
        this.health = 1;
        this.upkeep = 1;
        this.speed = 1;
        this.range = 1;
        this.baseMorale = 1000;
        this.moraleLoss = 10;
        this.morale = this.baseMorale;
        this.position = -1;
    }

    public Unit(String name, String assetName, Fraction fraction, Role role, int position, int attack, int spellPower, EffectSchool damageType, int damage, AttackStyle attackType, int defense, int armor, int ward, int baseHealth, int upkeep, int speed, int range, int baseMorale) {
        this.name = name;
        this.assetName = assetName;
        this.fraction = fraction;
        this.role = role;
        this.tier = Tier.base;
        this.category = Category.living;
        this.passives = new Passives();
        this.actives = new Actives();
        this.position = position;
        this.attack = attack;
        this.spellPower = spellPower;
        this.effectType = damageType;
        this.damage = damage;
        this.attackType = attackType;
        this.defense = defense;
        this.armor = armor;
        this.ward = ward;
        this.baseHealth = baseHealth;
        this.health = baseHealth;
        this.upkeep = upkeep;
        this.speed = speed;
        this.range = range;
        this.baseMorale = baseMorale;
        this.moraleLoss = 10;
        this.morale = baseMorale;
    }
    
    public void setBasic(String name, String assetName, Fraction fraction, Role role, Tier tier, Category category, int position, BattleSide side){
        this.name = name;
        this.assetName = assetName;
        this.fraction = fraction;
        this.role = role;
        this.tier = tier;
        this.category = category;
        this.position = position;
        this.side = side;
    }
    
    public void setBonuses(double spellPowerBonus, double atkDefBonus){
        this.spellPowerBonus = spellPowerBonus;
        this.atkDefBonus = atkDefBonus;
    }
    
    public void setCombatStats(int attack, int spellPower, EffectSchool effectType, int damage, int healing, AttackStyle attackType){
        this.attack = attack;
        this.spellPower = spellPower;
        this.effectType = effectType;
        this.damage = damage;
        this.baseHealingValue = healing;
        this.attackType = attackType;
    }
    
    public void setDefensiveStats(int defense, int armor, int ward, int baseHealth){
        this.defense = defense;
        this.armor = armor;
        this.ward = ward;
        this.baseHealth = baseHealth;
        this.health = baseHealth;
    }
    
    public void setSpecialStats(int upkeep, int speed, int range, int baseMorale){
        this.upkeep = upkeep;
        this.speed = speed;
        this.range = range;
        this.baseMorale = baseMorale;
        this.moraleLoss = 10;
        this.morale = baseMorale;
    }
    /*
    public Unit copy(){
        Unit copy = new Unit();
        copy.setBasic(name, role, position, side);
        copy.setCombatStats(attack, spellPower, effectType, damage, baseHealingValue, attackType);
        copy.setDefensiveStats(defense, armor, ward, baseHealth);
        copy.setSpecialStats(upkeep, speed, range, baseMorale, moraleLoss);
        copy.setHealth(this.health);
        copy.setMorale(this.morale);
        return copy;
    }*/
    
    public void setUnitName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setAssetName(String name){
        this.assetName = name;
    }
    
    public String getAssetName(){
        return this.assetName;
    }
    
    public void setFraction(Fraction fraction){
        this.fraction = fraction;
    }
    
    public Fraction getFraction(){
        return this.fraction;
    }
    
    public void setRole(Role role){
        this.role = role;
    }
    
    public Role getRole(){
        return this.role;
    }
    
    public boolean isRole(Role role){
        if(this.role.equals(role)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setTier(Tier tier){
        this.tier = tier;
    }
    
    public Tier getTier(){
        return this.tier;
    }
    
    public void setCategory(Category category){
        this.category = category;
    }
    
    public Category getCategory(){
        return this.category;
    }
    
    public void setPosition(int position){
        this.position = position;
    }
    
    public int getPosition(){
        return this.position;
    }
    
    public void setSide(BattleSide side){
        this.side = side;
    }
    
    public BattleSide getSide(){
        return this.side;
    }
    
    public void addPassive(Passive passive){
        this.passives.add(passive);
    }
    
    public Passives getPassives(){
        return this.passives;
    }
    
    public void addActive(Active active){
        this.actives.add(active);
    }
    
    public double getActiveValue(ActiveEffect effect){
        return this.actives.getValueOfEffect(effect);
    }
    
    public void setSpellPowerBonus(double bonus){
        this.spellPowerBonus = bonus;
    }
    
    public double getSpellPowerBonus(){
        return this.spellPowerBonus;
    }
    
    public void setAttackDefenseBonus(double bonus){
        this.atkDefBonus = bonus;
    }
    
    public double getAttackDefenseBonus(){
        return this.atkDefBonus;
    }
    
    public void setAttack(int value){
        this.attack = value;
    }
    
    public int getAttack(){
        if(this.attack < 0){
            return 0;
        }
        else{
            return this.attack;
        }
    }
    
    public void setSpellPower(int value){
        this.spellPower = value;
    }
    
    public int getSpellPower(){
        if(this.spellPower < 0){
            return 0;
        }
        else{
            return this.spellPower;
        }
    }
    
    public void setEffectType(EffectSchool type){
        this.effectType = type;
    }
    
    public EffectSchool getEffectType(){
        return this.effectType;
    }
    
    public void setDamage(int value){
        this.damage = value;
    }
    
    public int getDamage(){
        if(this.damage < 1){
            return 1;
        }
        else{
            return this.damage;
        }
    }
    
    public void setBaseHealing(int value){
        this.baseHealingValue = value;
    }
    
    public int getBaseHealingValue(){
        if(this.baseHealingValue < 0){
            return 0;
        }
        else{
            return this.baseHealingValue;
        }
    }
    
    public void setAttackType(AttackStyle type){
        this.attackType = type;
    }
    
    public AttackStyle getAttackType(){
        return this.attackType;
    }
    
    public void setDefense(int value){
        this.defense = value;
    }
    
    public int getDefense(){
        if(this.defense < 0){
            return 0;
        }
        else{
            return this.defense;
        }
    }
    
    public void setArmor(int value){
        this.armor = value;
    }
    
    public int getArmor(){
        if(this.armor < 0){
            return 0;
        }
        else{
            if(this.armor > this.maxArmor){
                return this.maxArmor;
            }
            else{
                return this.armor;
            }
        }
    }
    
    public void setWard(int value){
        this.ward += value;
    }
    
    public int getWard(){
        if(this.ward < 0){
            return 0;
        }
        else{
            if(this.ward > this.maxWard){
                return this.maxWard;
            }
            else{
                return this.ward;
            }
        }
    }
    
    public void setBaseHealth(int value){
        this.baseHealth = value;
    }
    
    public int getBaseHealth(){
        if(this.baseHealth < 1){
            return 1;
        }
        else{
            return this.baseHealth;
        }
    }

    public int setHealth(int value){
        this.health += value;
        return this.health;
    }
    
    public int getHealth(){
        if(this.health < 0){
            return 0;
        }
        else{
            return this.health;
        }
    }
    
    public void setUpkeep(int value){
        this.upkeep = value;
    }
    
    public int getUpkeep(){
        if(this.upkeep < 1){
            return 1;
        }
        else{
            return upkeep;
        }
    }
    
    public void setSpeed(int value){
        this.speed = value;
    }
    
    public int getSpeed(){
        if(speed < 1){
            return 1;
        }
        else{
            return this.speed;
        }
    }
    
    public void setRange(int value){
        this.range = value;
    }
    
    public int getRange(){
        if(this.range < 1){
            return 1;
        }
        else{
            return this.range;
        }
    }
    
    public void setBaseMorale(int value){
        this.baseMorale = value;
    }
    
    public int getBaseMorale(){
        if(this.baseMorale < 50){
            return 50;
        }
        else{
            return this.baseMorale;
        }
    }
    
    public void setMoraleLoss(int value){
        if(value >= 0){
            this.moraleLoss = value;
        }
    }
    
    public int getMoraleLoss(){
        return this.moraleLoss;
    }
    
    public void setMorale(int value){
        this.morale = value;
    }
    
    public int getMorale(){
        if(this.morale < 0){
            return 0;
        }
        else{
            return this.morale;
        }
    }
    
    public boolean isAlive(){
        if(this.health > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isCapableToFight(){
        if(this.isAlive() && this.morale > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isBetterToFightThan(Unit compared){
        if(this.morale > compared.getMorale() && this.health > compared.getHealth()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isFriendly(Unit target){
        if(this.side == target.getSide()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isHealer(){
        if(this.effectType != EffectSchool.physical && this.effectType != EffectSchool.none){
            if(this.baseHealingValue > 0){
                return true;
            }
        }
        return false;
    }
    
    public double weaknessValue(EffectSchool damageType){
        return this.actives.getWeaknessValue(damageType);
    }

    @Override
    public int compareTo(Unit u) {
        int compareSpeed = ((Unit) u).getSpeed();
        return compareSpeed - this.speed;
    }
    
    public void takeMoraleDamage(){
        this.morale -= this.getMoraleLoss();
    }
    
    public void takeDamage(int damage){
        if(damage > 0){
            int healthChange = -1 * damage;
            this.setHealth(healthChange);
        }
    }
    
    public int restoreHealth(int value){
        int realvalue = 0;
        if(this.health < this.baseHealth){
            int missingHealth = this.baseHealth - this.health;
            if(value > missingHealth){
                realvalue = missingHealth;
            }
            else{
                realvalue = value;
            }
            this.health += realvalue;
        }
        return realvalue;
    }
    
    @Override
    public String toString(){
        String result = "";
        if(this.name.equals("")){
            result += this.role;
        }
        else{
            result += this.name;
        }
        result += " in position " + this.position + " ";
        if(this.role.isRanged()){
            result += "on backline ";
        }
        else{
            result += "on frontline ";
        }
        return result;
    }
}
