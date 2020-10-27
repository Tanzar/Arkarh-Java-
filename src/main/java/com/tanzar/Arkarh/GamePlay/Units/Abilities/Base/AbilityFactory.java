/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Units.Abilities.Base;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.*;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Buff;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Heal;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Reincarnate;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Ressurect;
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
            case reincarnate:
                return new Reincarnate(json);
            case ressurect:
                return new Ressurect(json);
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
            case reincarnate:
                return new Reincarnate(entity);
            case ressurect:
                return new Ressurect(entity);
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
            case reincarnate:
                return new Reincarnate();
            case ressurect:
                return new Ressurect();
            default:
                return null;
        }
    }
    
}
