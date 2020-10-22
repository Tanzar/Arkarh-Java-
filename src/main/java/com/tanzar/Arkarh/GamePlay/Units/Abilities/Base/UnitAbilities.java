/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitAbilityGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spako
 */
public class UnitAbilities {
    
    private List<UnitAbility> abilities;
    
    public UnitAbilities(){
        this.abilities = new ArrayList<UnitAbility>();
    }
    
    public UnitAbilities(String abilitiesJson){
        this.abilities = new ArrayList<UnitAbility>();
        Json json = new Json(abilitiesJson);
        String[] str = json.getStringArray("abilities");
        for(String item: str){
            this.add(item);
        }
    }
    
    private void add(String jsonString){
        Json json = new Json(jsonString);
        UnitAbilityGroup group = UnitAbilityGroup.valueOf(json.getString("group"));
        UnitAbility ability = this.createAbility(json, group);
        this.add(ability);
    }
    
    private UnitAbility createAbility(Json json, UnitAbilityGroup group){
        switch(group){
            case attack:
                return new Attack(json);
            default:
                return null;
        }
    }
    
    public void add(UnitAbility ability){
        if(ability != null){
            this.abilities.add(ability);
        }
    }
    
    public void remove(UnitAbility ability){
        if(!(ability instanceof Attack)){
            this.abilities.remove(ability);
        }
    }
    
    public UnitAbility get(int index){
        return this.abilities.get(index);
    }
    
    public UnitAbilities getByTrigger(Trigger trigger){
        UnitAbilities result = new UnitAbilities();
        for(UnitAbility ability: this.abilities){
            result.add(ability);
        }
        return result;
    }
    
    public void useAbilities(Unit source, Trigger trigger, Battlefield battlefield, CombatReport report){
        for(UnitAbility ability: this.abilities){
            ability.use(source, trigger, battlefield, report);
        }
    }
    
    public int size(){
        return this.abilities.size();
    }
    
    public UnitAbility[] toArray(){
        UnitAbility[] array = new UnitAbility[this.size()];
        this.abilities.toArray(array);
        return array;
    }
}
