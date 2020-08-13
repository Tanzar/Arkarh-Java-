/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.CombatLog.Actions;
import com.tanzar.Arkarh.GamePlay.CombatLog.BattleSide;
import com.tanzar.Arkarh.GamePlay.CombatLog.ReportEntry;

/**
 *
 * @author spako
 */
public class Unit implements Comparable<Unit>{
    
    //basic
    private String name;
    private Role role;
    private int position;
    private BattleSide side = BattleSide.none;
    
    //limits
    private final int maxArmor = 75;
    private final int maxWard = 75;
    
    //bonuses
    private double spellPowerBonus = 0.05;
    private double atkDefBonus = 0.05;
    private boolean healer = false;
    
    //combat stats
    private int attack;
    private int spellPower;
    private EffectType effectType;
    private int damage;
    private int baseHealingValue = 0;
    private AttackType attackType;
    
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
    private int morale;

    public Unit() {
        this.name = "Unit";
        this.role = Role.warrior;
        this.attack = 0;
        this.damage = 1;
        this.defense = 0;
        this.armor = 0;
        this.spellPower = 0;
        this.effectType = EffectType.physical;
        this.attackType = AttackType.single;
        this.ward = 0;
        this.baseHealth = 1;
        this.health = 1;
        this.upkeep = 1;
        this.speed = 1;
        this.range = 1;
        this.baseMorale = 100;
        this.morale = 100;
        this.position = -1;
    }

    public Unit(String name, Role role, int position, int attack, int spellPower, EffectType damageType, int damage, AttackType attackType, int defense, int armor, int ward, int baseHealth, int upkeep, int speed, int range, int baseMorale) {
        this.name = name;
        this.role = role;
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
        this.morale = baseMorale;
    }
    
    public void setBasic(String name, Role role, int position, BattleSide side){
        this.name = name;
        this.role = role;
        this.position = position;
        this.side = side;
    }
    
    public void setBonuses(double spellPowerBonus, double atkDefBonus, boolean isHealer){
        this.spellPowerBonus = spellPowerBonus;
        this.atkDefBonus = atkDefBonus;
        this.healer = isHealer;
    }
    
    public void setCombatStats(int attack, int spellPower, EffectType effectType, int damage, int healing, AttackType attackType){
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
        this.morale = baseMorale;
    }
    
    public Unit copy(){
        Unit copy = new Unit();
        copy.setBasic(name, role, position, side);
        copy.setCombatStats(attack, spellPower, effectType, damage, baseHealingValue, attackType);
        copy.setDefensiveStats(defense, armor, ward, baseHealth);
        copy.setSpecialStats(upkeep, speed, range, baseMorale);
        copy.setHealth(this.health - 1);
        copy.setMorale(this.morale - 100);
        return copy;
    }
    
    public void setUnitName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
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
    
    public void setHealer(){
        this.healer = true;
    }
    
    public void disableHealer(){
        this.healer = false;
    }
    
    public boolean isHealer(){
        return this.healer;
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
    
    public void setEffectType(EffectType type){
        this.effectType = type;
    }
    
    public EffectType getEffectType(){
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
    
    public void setAttackType(AttackType type){
        this.attackType = type;
    }
    
    public AttackType getAttackType(){
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

    @Override
    public int compareTo(Unit u) {
        int compareSpeed = ((Unit) u).getSpeed();
        return compareSpeed - this.speed;
    }
    
    public void damageMorale(){
        this.morale--;
    }
    
    public ReportEntry action(Unit target){
        ReportEntry entry = null;
        if(this.isFriendly(target)){
            if(this.healer){
                int heal = this.heal(target);
                entry = new ReportEntry(Actions.heal, this, target, String.valueOf(heal));
            }
        }
        else{
            int damage = this.attack(target);
            entry = new ReportEntry(Actions.attack, this, target, String.valueOf(damage));
        }
        return entry;
    }
    
    public int attack(Unit target){
        int damage = this.calculateDamage(target);
        target.takeDamage(damage);
        target.damageMorale();
        return damage;
    }
    
    public int calculateDamage(Unit target){
        int damage = 0;
        if(this.effectType == EffectType.physical){
            damage = this.physicalAttack(target);
        }
        else{
            damage = this.magicalAttack(target);
        }
        return damage;
    }
    
    private int physicalAttack(Unit target){
        int damage = 1;
        int differenceAtkDef = this.getAttack() - target.getDefense();
        double atkDefMultiplier = 1 + differenceAtkDef * this.atkDefBonus;
        if(atkDefMultiplier < 0.5){
            atkDefMultiplier = 0.5;
        }
        double armor = (double) target.getArmor();
        double armorReductionMultiplier = (100 - armor) / 100;
        damage = (int) Math.round((this.getDamage() * atkDefMultiplier) * armorReductionMultiplier);
        return damage;
    }
    
    private int magicalAttack(Unit target){
        int damage = 1;
        double spellPowerMultiplier = 1 + (this.getSpellPower() * this.spellPowerBonus);
        double ward = (double) target.getWard();
        double wardReductionMultiplier = (100 - ward) / 100;
        damage = (int) Math.round(this.getDamage() * spellPowerMultiplier * wardReductionMultiplier);
        return damage;
    }
    
    public void takeDamage(int damage){
        if(damage > 0){
            int healthChange = -1 * damage;
            this.setHealth(healthChange);
        }
    }
    
    public int heal(Unit target){
        int healValue = (int) Math.round(this.baseHealingValue * (1 + this.getSpellPower() * this.spellPowerBonus));
        return target.restoreHealth(healValue);
    }
    
    private int restoreHealth(int value){
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
