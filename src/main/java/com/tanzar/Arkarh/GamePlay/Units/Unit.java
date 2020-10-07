/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Stats.*;

/**
 *
 * @author spako
 */
public class Unit implements Comparable<Unit>{
    
    //basic
    private int id;
    private String name;
    private String assetName;
    private Fraction fraction;
    private Role role;
    private Tier tier;
    private Category category;
    
    private Offensive offensive;
    private Defensive defensive;
    private Special special;
    private Status status;
    private Passives passives;
    private UnitAbilities abilities;
    
    public Unit() {
        this.id = 0;
        this.name = "Unit";
        this.assetName = "none.png";
        this.fraction = Fraction.none;
        this.role = Role.warrior;
        this.tier = Tier.base;
        this.category = Category.living;
        this.offensive = new Offensive();
        this.defensive = new Defensive();
        this.special = new Special();
        this.status = new Status(-1, this.defensive.getBaseHealth(), this.special.getBaseMorale());
        this.passives = new Passives();
        this.abilities = new UnitAbilities();
    }
    
    public Unit(String unitJson){
        Json json = new Json(unitJson);
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.assetName = json.getString("assetName");
        this.fraction = Fraction.valueOf(json.getString("fraction"));
        this.role = Role.valueOf(json.getString("role"));
        this.tier = Tier.valueOf(json.getString("tier"));
        this.category = Category.valueOf(json.getString("category"));
        this.offensive = new Offensive(json.getInnerJson("offensive"));
        this.defensive = new Defensive(json.getInnerJson("defensive"));
        this.special = new Special(json.getInnerJson("special"));
        this.status = new Status(-1, this.defensive.getBaseHealth(), this.special.getBaseMorale());
        this.passives = new Passives();
        this.abilities = new UnitAbilities(json.getInnerJson("abilities"));
    }
    
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Fraction getFraction() {
        return fraction;
    }

    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Offensive getOffensive() {
        return offensive;
    }

    public void setOffensive(Offensive offensive) {
        this.offensive = offensive;
    }

    public Defensive getDefensive() {
        return defensive;
    }

    public void setDefensive(Defensive defensive) {
        this.defensive = defensive;
        this.status = new Status(this.status.getPosition(), this.defensive.getBaseHealth(), this.special.getBaseMorale());
    }

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
        this.status = new Status(this.status.getPosition(), this.defensive.getBaseHealth(), this.special.getBaseMorale());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Passives getPassives() {
        return passives;
    }

    public void setPassives(Passives passives) {
        this.passives = passives;
    }
    
    public void addAbility(UnitAbility ability){
        this.abilities.add(ability);
    }
    
    public void setAbilities(UnitAbilities abilities){
        this.abilities = abilities;
    }
    
    public UnitAbilities getAbilities(){
        return this.abilities;
    }
    
    public UnitAbilities getAbilities(Trigger trigger){
        return this.abilities.getByTrigger(trigger);
    }
    
    public int getPosition(){
        return this.status.getPosition();
    }
    
    public void setPosition(int position){
        this.status.setPosition(position);
    }
    
    public int getTotalAttack(){
        int value = this.offensive.getAttack();
        value += this.passives.summarizeValues(PassiveEffect.attack);
        if(value < 1){
            return 1;
        }
        return value;
    }
    
    public int getTotalSpellPower(){
        int value = this.offensive.getSpellPower();
        value += this.passives.summarizeValues(PassiveEffect.spellPower);
        if(value < 1){
            return 1;
        }
        return value;
    }
    
    public int getTotalBaseDamage(){
        int value = this.offensive.getDamage();
        value += this.passives.summarizeValues(PassiveEffect.damage);
        if(value < 1){
            return 1;
        }
        return value;
    }
    
    public int getTotalDefense(){
        int value = this.defensive.getDefense();
        value += this.passives.summarizeValues(PassiveEffect.defense);
        if(value < 1){
            return 1;
        }
        return value;
    }
    
    public int getTotalArmor(){
        int value = this.defensive.getArmor();
        value += this.passives.summarizeValues(PassiveEffect.armor);
        int maxArmor = this.defensive.getMaxArmor();
        if(value > maxArmor){
            return maxArmor;
        }
        else{
            if(value < 0){
                return 0;
            }
            return value;
        }
    }
    
    public int getTotalWard(){
        int value = this.defensive.getWard();
        value += this.passives.summarizeValues(PassiveEffect.ward);
        int maxWard = this.defensive.getMaxWard();
        if(value > maxWard){
            return maxWard;
        }
        else{
            if(value < 0){
                return 0;
            }
            return value;
        }
    }
    
    public boolean isAlive(){
        return this.status.isAlive();
    }
    
    public boolean isCappableToFight(){
        return this.status.isCappableToFight();
    }
    
    public boolean isFasterThan(Unit unit){
        int thisSpeed = this.special.getSpeed();
        Special comparedSpecial = unit.getSpecial();
        int comparedSpeed = comparedSpecial.getSpeed();
        if(thisSpeed > comparedSpeed){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isRole(Role role){
        if(this.role.equals(role)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isFront(){
        return !this.role.isRanged();
    }
    
    public void takeDamage(int damage){
        this.status.takeDamage(damage);
    }
    
    public void useAbilities(Trigger trigger, Battlefield battlefield, CombatReport report){
        this.abilities.useAbilities(this, trigger, battlefield, report);
    }
    
    public void updateStatus(){
        this.status.updateState();
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
        result += " in position " + this.status.getPosition() + " ";
        if(this.role.isRanged()){
            result += "on backline ";
        }
        else{
            result += "on frontline ";
        }
        return result;
    }

    @Override
    public int compareTo(Unit u) {
        Special specialStats = ((Unit) u).getSpecial();
        int compareSpeed = specialStats.getSpeed();
        return compareSpeed - this.special.getSpeed();
    }
}
