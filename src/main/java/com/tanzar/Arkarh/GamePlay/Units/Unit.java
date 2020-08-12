/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.GamePlay.CombatLog.BattleSide;

/**
 *
 * @author spako
 */
public class Unit implements Comparable<Unit>{
    
    private String name;
    private Role role;
    private int position;
    private final int maxArmor = 75;
    private final int maxWard = 75;
    private double spellPowerBonus = 0.05;
    private double atkDefBonus = 0.05;
    private BattleSide side = BattleSide.none;
    
    
    private int attack;
    private int spellPower;
    private DamageType damageType;
    private int damage;
    private AttackType attackType;
    
    private int defense;
    private int armor;
    private int ward;
    
    
    private int baseHealth;
    private int health;
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
        this.damageType = DamageType.physical;
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

    public Unit(String name, Role role, int position, int attack, int spellPower, DamageType damageType, int damage, AttackType attackType, int defense, int armor, int ward, int baseHealth, int upkeep, int speed, int range, int baseMorale) {
        this.name = name;
        this.role = role;
        this.position = position;
        this.attack = attack;
        this.spellPower = spellPower;
        this.damageType = damageType;
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
    
    
    
    public Unit copy(){
        Unit copy = new Unit();
        copy.setUnitName(this.name);
        copy.setRole(this.role);
        copy.setDamageType(this.damageType);
        copy.changeAttack(this.attack);
        copy.changeDefense(this.defense);
        copy.changeArmor(this.armor);
        copy.changeSpellPower(this.spellPower);
        copy.setAttackType(this.attackType);
        copy.changeDamage(this.damage - 1);
        copy.changeWard(this.ward);
        copy.changeBaseHealth(this.baseHealth - 1);
        copy.changeHealth(this.health - 1);
        copy.changeUpkeep(this.upkeep - 1);
        copy.changeSpeed(this.speed - 1);
        copy.changeRange(this.range - 1);
        copy.changeBaseMorale(this.baseMorale - 100);
        copy.changeMorale(this.morale - 100);
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
    
    public void changeAttack(int value){
        this.attack += value;
    }
    
    public int getAttack(){
        if(this.attack < 0){
            return 0;
        }
        else{
            return this.attack;
        }
    }
    
    public void changeDamage(int value){
        this.damage += value;
    }
    
    public int getDamage(){
        if(this.damage < 1){
            return 1;
        }
        else{
            return this.damage;
        }
    }
    
    public void changeDefense(int value){
        this.defense += value;
    }
    
    public int getDefense(){
        if(this.defense < 0){
            return 0;
        }
        else{
            return this.defense;
        }
    }
    
    public void changeArmor(int value){
        this.armor += value;
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
    
    public void changeSpellPower(int value){
        this.spellPower += value;
    }
    
    public int getSpellPower(){
        if(this.spellPower < 0){
            return 0;
        }
        else{
            return this.spellPower;
        }
    }
    
    public void setDamageType(DamageType type){
        this.damageType = type;
    }
    
    public DamageType getDamageType(){
        return this.damageType;
    }
    
    public void setAttackType(AttackType type){
        this.attackType = type;
    }
    
    public AttackType getAttackType(){
        return this.attackType;
    }
    
    public void changeWard(int value){
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
    
    public void changeBaseHealth(int value){
        this.baseHealth += value;
    }
    
    public int getBaseHealth(){
        if(this.baseHealth < 1){
            return 1;
        }
        else{
            return this.baseHealth;
        }
    }

    public int changeHealth(int value){
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
    
    public void changeUpkeep(int value){
        this.upkeep += value;
    }
    
    public int getUpkeep(){
        if(this.upkeep < 1){
            return 1;
        }
        else{
            return upkeep;
        }
    }
    
    public void changeSpeed(int value){
        this.speed += value;
    }
    
    public int getSpeed(){
        if(speed < 1){
            return 1;
        }
        else{
            return this.speed;
        }
    }
    
    public void changeRange(int value){
        this.range += value;
    }
    
    public int getRange(){
        if(this.range < 1){
            return 1;
        }
        else{
            return this.range;
        }
    }
    
    public void changeBaseMorale(int value){
        this.baseMorale += value;
    }
    
    public int getBaseMorale(){
        if(this.baseMorale < 50){
            return 50;
        }
        else{
            return this.baseMorale;
        }
    }
    
    public int changeMorale(int value){
        this.morale += value;
        return this.morale;
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

    @Override
    public int compareTo(Unit u) {
        int compareSpeed = ((Unit) u).getSpeed();
        return compareSpeed - this.speed;
    }
    
    public int calculateDamage(Unit target){
        int damage = 0;
        if(this.damageType == DamageType.physical){
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
