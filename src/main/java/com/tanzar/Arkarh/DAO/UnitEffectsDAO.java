/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO;

import com.tanzar.Arkarh.Containers.UnitEffects;
import com.tanzar.Arkarh.DAO.abstracts.DAO;
import com.tanzar.Arkarh.Entities.Unit.UnitEffectEntity;
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
public class UnitEffectsDAO extends DAO<UnitEffectEntity, UnitEffects> {

    public UnitEffectsDAO() {
        super("UnitEffectEntity");
    }

    @Override
    protected UnitEffects convertResults(List<UnitEffectEntity> list) {
        UnitEffects effects = new UnitEffects();
        for(UnitEffectEntity effect: list){
            effects.add(effect);
        }
        return effects;
    }
    
    public UnitEffects getByUnitId(int id){
        try {
            UnitEffects effects = this.get("where units_id =" + id);
            return effects;
        } catch (QueryException ex) {
            
        }
        return null;
    }
    
    public void delete(int id){
        try {
            UnitEffects effects = this.get("where id =" + id);
            UnitEffectEntity effect = effects.get(0);
            this.remove(effect);
        } catch (QueryException ex) {
            
        }
    }
}
