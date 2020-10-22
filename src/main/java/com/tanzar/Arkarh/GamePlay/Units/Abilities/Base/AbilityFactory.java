/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Attack;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Buff;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Heal;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.PassiveEffect;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.*;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.UnitAbilityGroup;

/**
 *
 * @author Tanzar
 */
public class AbilityFactory {
    
    public static UnitAbility convert(Json json){
        String groupString = json.getString("group");
        UnitAbilityGroup group = UnitAbilityGroup.valueOf(groupString);
        switch(group){
            case attack:
                return new Attack(json);
            case heal:
                return new Heal(json);
            case buff:
                return new Buff(json);
            default:
                return null;
        }
    }
    
    public static UnitAbility convertAbility(UnitAbilityEntity entity){
        String groupString = entity.getEffectGroup();
        UnitAbilityGroup group = UnitAbilityGroup.valueOf(groupString);
        switch(group){
            case attack:
                return new Attack(entity);
            case heal:
                return new Heal(entity);
            case buff:
                return new Buff(entity);
            default:
                return null;
        }
    }
    
    public static UnitAbility newAbility(UnitAbilityGroup group){
        switch(group){
            case attack:
                return null;
            case heal:
                return new Heal();
            case buff:
                return new Buff();
            default:
                return null;
        }
    }
    
}
