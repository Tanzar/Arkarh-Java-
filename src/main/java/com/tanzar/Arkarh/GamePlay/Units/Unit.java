/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.AbilityFactory;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.Trigger;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
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
    
    public Unit(UnitEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.assetName = entity.getAssetName();
        this.fraction = Fraction.valueOf(entity.getFraction());
        this.role = Role.valueOf(entity.getRole());
        this.tier = Tier.valueOf(entity.getTier());
        this.category = Category.valueOf(entity.getCategory());
        this.offensive = new Offensive(entity.getAttack(), entity.getSpellPower(), entity.getDamage());
        this.defensive = new Defensive(entity.getDefense(), entity.getArmor(), entity.getWard(), entity.getHealth());
        this.special = new Special(entity.getUpkeep(), entity.getSpeed(), entity.getMorale());
        this.status = new Status(-1, this.defensive.getBaseHealth(), this.special.getBaseMorale());
        this.passives = new Passives();
    }
    
    public Unit(UnitEntity entity, UnitAbilityEntity[] abilitiesEntities) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.assetName = entity.getAssetName();
        this.fraction = Fraction.valueOf(entity.getFraction());
        this.role = Role.valueOf(entity.getRole());
        this.tier = Tier.valueOf(entity.getTier());
        this.category = Category.valueOf(entity.getCategory());
        this.offensive = new Offensive(entity.getAttack(), entity.getSpellPower(), entity.getDamage());
        this.defensive = new Defensive(entity.getDefense(), entity.getArmor(), entity.getWard(), entity.getHealth());
        this.special = new Special(entity.getUpkeep(), entity.getSpeed(), entity.getMorale());
        this.status = new Status(-1, this.defensive.getBaseHealth(), this.special.getBaseMorale());
        this.passives = new Passives();
        this.abilities = new UnitAbilities();
        for(UnitAbilityEntity uae: abilitiesEntities){
            UnitAbility ability = AbilityFactory.convertAbility(uae);
            this.abilities.add(ability);
        }
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
    
    public State getState(){
        return this.status.getState();
    }
    
    public void addPassive(Passive passive){
        this.passives.add(passive);
        PassiveEffect effect = passive.getEffect();
        if(effect.equals(PassiveEffect.bonusHealth)){
            this.updateHealth();
        }
        if(effect.equals(PassiveEffect.baseMorale)){
            this.updateMorale();
        }
    }
    
    private void updateHealth(){
        int health = this.status.getHealth();
        int healthBonus = this.passives.summarizeValues(PassiveEffect.bonusHealth);
        health = health + healthBonus;
        this.status.setHealth(health);
    }
    
    private void updateMorale(){
        int morale = this.status.getMorale();
        int moraleBonus = this.passives.summarizeValues(PassiveEffect.baseMorale);
        morale = morale + moraleBonus;
        this.status.setMorale(morale);
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
    
    public void setSide(BattleSide side){
        this.status.setSide(side);
    }
    
    public BattleSide getSide(){
        return this.status.getSide();
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
    
    public int getTotalHealth() {
        int value = this.defensive.getBaseHealth();
        value += this.passives.summarizeValues(PassiveEffect.bonusHealth);
        return value;
    }
    
    public boolean isAlive(){
        return this.status.isAlive();
    }
    
    public boolean isNotAlive(){
        return !this.status.isAlive();
    }
    
    public boolean isNotRisen(){
        if(this.status.getState() != State.risen){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isState(State state){
        if(this.status.getState() == state){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean canBeRessurected(){
        if(this.category.isRessurectable() && this.isNotAlive() && this.isNotRisen()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isHigherTierThan(Unit unit){
        Tier comparable = unit.getTier();
        if(this.tier.isHigherTierThan(comparable)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean isCappableToFight(){
        return this.status.isCappableToFight();
    }
    
    public boolean isNotCappableToFight(){
        return !this.status.isCappableToFight();
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
        int maxHealth = this.defensive.getBaseHealth();
        int healthBonus = this.passives.summarizeValues(PassiveEffect.bonusHealth);
        maxHealth = maxHealth + healthBonus;
        this.status.updateState(maxHealth);
    }
    
    public void heal(int value){
        if(value > 0){
            this.status.heal(value);
        }
    }
    
    public void takeMoraleDamage(){
        if(this.category.takesMoraleDamage()){
            int moraleLoss = this.special.getMoraleLoss();
            int moraleLossModifier = this.passives.summarizeValues(PassiveEffect.moraleLoss);
            moraleLoss = moraleLoss - moraleLossModifier;
            if(moraleLoss > 0){
                this.status.damageMorale(moraleLoss);
            }
            else{
                this.status.damageMorale(1);
            }
        }
    }
    
    public int ressurect(int percentage){
        int restoredHealth = 0;
        if(percentage > 0){
            if(percentage > 100){
                percentage = 100;
            }
            int maxHealth = this.defensive.getBaseHealth() + this.passives.summarizeValues(PassiveEffect.bonusHealth);
            int newHealth = (int) Math.round(((double) maxHealth) * ( ((double)percentage)/100));
            this.status.setHealth(newHealth);
            this.status.setState(State.alive);
            restoredHealth = newHealth;
        }
        return restoredHealth;
    }
    
    public boolean rise(){
        if(this.canBeRessurected()){
            this.status.rise();
            return true;
        }
        else{
            return false;
        }
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
        return result;
    }
    
    public int getPassiveValue(PassiveEffect effect){
        int value = this.passives.summarizeValues(effect);
        return value;
    }

    @Override
    public int compareTo(Unit u) {
        Special specialStats = ((Unit) u).getSpecial();
        int compareSpeed = specialStats.getSpeed();
        return compareSpeed - this.special.getSpeed();
    }

}
