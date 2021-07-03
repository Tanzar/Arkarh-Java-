/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.DAO;

import com.tanzar.Arkarh.Database.Containers.UnitAbilityEntities;
import com.tanzar.Arkarh.Database.DAO.Abstracts.DAO;
import com.tanzar.Arkarh.Database.Entities.Units.UnitAbilityEntity;
import com.tanzar.Arkarh.Exceptions.QueryException;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author spako
 */
@Repository
@Transactional
public class UnitAbilitiesDAO extends DAO<UnitAbilityEntity, UnitAbilityEntities> {

    public UnitAbilitiesDAO() {
        super("UnitAbilityEntity");
    }

    @Override
    protected UnitAbilityEntities convertResults(List<UnitAbilityEntity> list) {
        UnitAbilityEntities effects = new UnitAbilityEntities();
        for(UnitAbilityEntity effect: list){
            effects.add(effect);
        }
        return effects;
    }
    
    public UnitAbilityEntities getByUnitId(int id){
        try {
            UnitAbilityEntities effects = this.get("where unit_id =" + id);
            return effects;
        } catch (QueryException ex) {
            
        }
        return new UnitAbilityEntities();
    }
    
    public UnitAbilityEntities getByUnitIdAndEffectGroup(int id, String group){
        try {
            UnitAbilityEntities effects = this.get("where unit_id =" + id + " and effect_group='" + group + "'");
            return effects;
        } catch (QueryException ex) {
            
        }
        return new UnitAbilityEntities();
    }
    
    public void delete(int id){
        try {
            UnitAbilityEntities effects = this.get("where id =" + id);
            UnitAbilityEntity effect = effects.get(0);
            this.remove(effect);
        } catch (QueryException ex) {
            
        }
    }
}
