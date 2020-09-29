/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Containers.UnitEffects;
import com.tanzar.Arkarh.Containers.UnitEntities;
import com.tanzar.Arkarh.Converter.UnitsConverter;
import com.tanzar.Arkarh.DAO.UnitEffectsDAO;
import com.tanzar.Arkarh.DAO.UnitsDAO;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.BattleSide;
import com.tanzar.Arkarh.GamePlay.TMP.Category;
import com.tanzar.Arkarh.GamePlay.TMP.Fraction;
import com.tanzar.Arkarh.GamePlay.TMP.Tier;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbility;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passive;
import com.tanzar.Arkarh.GamePlay.Units.Modifiers.Passives;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Defensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Offensive;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Special;
import com.tanzar.Arkarh.GamePlay.Units.Stats.Status;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author spako
 */
@Service
public class UnitsService {
    
    @Autowired
    private UnitsDAO unitsDAO;
    
    @Autowired
    private UnitEffectsDAO effectsDAO;
    
    public UnitEntities getAllUnitEntities(){
        return this.unitsDAO.getAll();
    }
    
    public Units getAll(){
        UnitEntities entities = this.unitsDAO.getAll();
        Units units = new Units();
        for(int i = 0; i < entities.size(); i++){
            UnitEntity entity = entities.get(i);
            Unit unit = UnitsConverter.convert(entity);
            int id = unit.getId();
            Passives passives = this.getPassivesByUnitId(id);
            unit.setPassives(passives);
            UnitAbilities abilities = this.getUnitAbilities(unit);
            unit.setAbilities(abilities);
            units.add(unit);
        }
        return units;
    }
    
    public Passives getPassivesByUnitId(int id){
        UnitEffects passiveEffects = this.effectsDAO.getByUnitIdAndEffectGroup(id, "passive");
        Passives passives = new Passives();
        for(UnitEffectEntity entity: passiveEffects.toArray()){
            Passive passive = UnitsConverter.convert(entity);
            passives.add(passive);
        }
        return passives;
    }
    
    public UnitAbilities getUnitAbilities(Unit unit){
        int id = unit.getId();
        UnitEffects effects = this.effectsDAO.getByUnitId(id);
        UnitAbilities abilities = new UnitAbilities();
        for(UnitEffectEntity entity: effects.toArray()){
            UnitAbility ability = UnitsConverter.convertAbility(unit, entity);
            abilities.add(ability);
        }
        return abilities;
    }
    
    public void add(Unit unit){
        UnitEntity entity = UnitsConverter.convert(unit);
        int id = this.unitsDAO.add(entity);
        unit.setId(id);
        Passives passives = unit.getPassives();
        for(Passive passive: passives.toArray()){
            UnitEffectEntity effect = UnitsConverter.convert(id, passive);
            this.effectsDAO.add(effect);
        }
        UnitAbilities abilities = unit.getAbilities();
        for(UnitAbility ability: abilities.toArray()){
            UnitEffectEntity effect = ability.convert(unit);
            this.effectsDAO.add(effect);
        }
    }
    
    public void update(Unit unit){
        UnitEntity entity = UnitsConverter.convert(unit);
        this.unitsDAO.update(entity);
        int id = unit.getId();
        Passives passives = unit.getPassives();
        for(Passive passive: passives.toArray()){
            UnitEffectEntity effect = UnitsConverter.convert(id, passive);
            this.effectsDAO.update(effect);
        }
        UnitAbilities abilities = unit.getAbilities();
        for(UnitAbility ability: abilities.toArray()){
            UnitEffectEntity effect = ability.convert(unit);
            this.effectsDAO.update(effect);
        }
    }
    
    public void remove(Unit unit){
        int id = unit.getId();
        this.unitsDAO.delete(id);
    }
    
    public void remove(UnitAbility ability){
        int id = ability.getId();
        this.effectsDAO.delete(id);
    }
    
}
