/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

/**
 *
 * @author spako
 */
public class Unit implements Comparable<Unit>{
    
    private String name;
    private Role role;
    private int position;
    
    
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
            if(this.armor > 75){
                return 75;
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
            if(this.ward > 75){
                return 75;
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
    
}
