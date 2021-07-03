/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Entities.Unit;

import com.tanzar.Arkarh.GamePlay.Units.Stats.Defensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author spako
 */
@Entity
@Table(name = "units")
public class UnitEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    
    @Column(name = "unit_name")
    private String unitName;
    
    @Column(name = "asset_name")
    private String assetName;
    
    @Column(name = "fraction")
    private String fraction;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "tier")
    private String tier;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "attack")
    private int attack;
    
    @Column(name = "spell_power")
    private int spellPower;
    
    @Column(name = "damage")
    private int damage;
    
    @Column(name = "defense")
    private int defense;
    
    @Column(name = "armor")
    private int armor;
    
    @Column(name = "ward")
    private int ward;
    
    @Column(name = "health")
    private int health;
    
    @Column(name = "upkeep")
    private int upkeep;
    
    @Column(name = "speed")
    private int speed;
    
    @Column(name = "morale")
    private int morale;

    public UnitEntity() {
    }

    public UnitEntity(Unit unit){
        this.id = unit.getId();
        this.unitName = unit.getName();
        this.assetName = unit.getAssetName();
        this.fraction = unit.getFraction().toString();
        this.role = unit.getRole().toString();
        this.tier = unit.getTier().toString();
        this.category = unit.getCategory().toString();
        Offensive off = unit.getOffensive();
        this.attack = off.getAttack();
        this.spellPower = off.getSpellPower();
        this.damage = off.getDamage();
        Defensive def = unit.getDefensive();
        this.defense = def.getDefense();
        this.armor = def.getArmor();
        this.ward = def.getWard();
        this.health = def.getBaseHealth();
        Special spec = unit.getSpecial();
        this.upkeep = spec.getUpkeep();
        this.speed = spec.getSpeed();
        this.morale = spec.getBaseMorale();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return unitName;
    }

    public void setName(String name) {
        this.unitName = name;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    
    public String getFraction(){
        return this.fraction;
    }
    
    public void setFraction(String fraction){
        this.fraction = fraction;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getWard() {
        return ward;
    }

    public void setWard(int ward) {
        this.ward = ward;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getUpkeep() {
        return upkeep;
    }

    public void setUpkeep(int upkeep) {
        this.upkeep = upkeep;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }
}
