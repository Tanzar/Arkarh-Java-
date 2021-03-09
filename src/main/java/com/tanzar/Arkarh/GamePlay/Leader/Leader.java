/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Leader;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.Equipment.Equipment;
import com.tanzar.Arkarh.GamePlay.Equipment.Artifact;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveSource;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;

/**
 *
 * @author spako
 */
public class Leader {
    
    private int id;
    private String asset;
    private String name;
    private String story;
    private int level;
    private int experience;
    private int attack;
    private int defense;
    private int spellPower;
    private int upkeep;
    private Spell[] spellSlots;
    
    private LeaderSkills skills;
    private Equipment equipment;
    
    public Leader(){
        this.id = 0;
        this.asset = "none.png";
        this.name = "noname";
        this.story = "";
        this.level = 1;
        this.experience = 1;
        this.attack = 1;
        this.defense = 1;
        this.spellPower = 1;
        this.upkeep = 300;
        this.equipment = new Equipment();
    }
    
    public Leader(Json json){
        this.id = 0;
        this.asset = "none.png";
        this.name = "noname";
        this.story = "";
        this.level = json.getInt("level");
        this.experience = json.getInt("experience");
        this.attack = json.getInt("attack");
        this.defense = json.getInt("defense");
        this.spellPower = json.getInt("spellPower");
        this.upkeep = 300;
        this.equipment = new Equipment();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    public void changeAttack(int value){
        this.attack += value;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    public void changeDefense(int value){
        this.defense += value;
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void setSpellPower(int spellPower) {
        this.spellPower = spellPower;
    }
    
    public void changeSpellPower(int value){
        this.spellPower += value;
    }
    
    public int getUpkeep(){
        return this.upkeep;
    }
    
    public void setUpkeep(int upkeep){
        this.upkeep = upkeep;
    }
    
    public void changeUpkeep(int value){
        this.upkeep += value;
    }
    
    public void equip(Artifact artifact){
        this.equipment.equip(artifact);
        artifact.applyBonus(this);
    }
    
    public void addArtifactToInventory(Artifact artifact){
        this.equipment.addArtifact(artifact);
    }
    
    public void applyBonuses(Units units){
        for(Unit unit: units.toArray()){
            this.applyBonuses(unit);
        }
    }
    
    public void applyBonuses(Unit unit){
        this.applyBaseStats(unit);
        this.applyOtherBonuses(unit);
    }
    
    private void applyBaseStats(Unit unit){
        int totalAttack = this.getTotalAttack();
        int totalDefense = this.getTotalDefense();
        int totalSpellPower = this.getTotalSpellPower();
        Passive attackBonus = new Passive(this.id, 1, 1, totalAttack, PassiveEffect.attack, EffectSchool.physical, PassiveSource.leader);
        Passive defenseBonus = new Passive(this.id, 1, 1, totalDefense, PassiveEffect.defense, EffectSchool.physical, PassiveSource.leader);
        Passive spellPowerBonus = new Passive(this.id, 1, 1, totalSpellPower, PassiveEffect.spellPower, EffectSchool.physical, PassiveSource.leader);
        unit.addPassive(attackBonus);
        unit.addPassive(defenseBonus);
        unit.addPassive(spellPowerBonus);
    }
    
    private void applyOtherBonuses(Unit unit){
        this.equipment.applyBonuses(unit);
    }
    
    public int getTotalAttack(){
        return this.attack;
    }
    
    public int getTotalDefense(){
        return this.defense;
    }
    
    public int getTotalSpellPower(){
        return this.spellPower;
    }
    
}
