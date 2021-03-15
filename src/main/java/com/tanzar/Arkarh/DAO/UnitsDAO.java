/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.DAO;

import com.tanzar.Arkarh.Containers.Gameplay.UnitEntities;
import com.tanzar.Arkarh.DAO.abstracts.DAO;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
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
public class UnitsDAO extends DAO<UnitEntity, UnitEntities> {

    public UnitsDAO() {
        super("UnitEntity");
    }

    @Override
    protected UnitEntities convertResults(List<UnitEntity> list) {
        UnitEntities units = new UnitEntities();
        for(UnitEntity item: list){
            units.add(item);
        }
        return units;
    }
    
    public UnitEntity getByName(String name){
        try{
            UnitEntities units = this.get("where unit_name ='" + name + "'");
            return units.get(0);
        } catch (QueryException ex) {
            return null;
        }
    }
    
    public UnitEntity getById(int id){
        try{
            UnitEntities units = this.get("where id ='" + id + "'");
            return units.get(0);
        } catch (QueryException ex) {
            return null;
        }
    }
    
    public void delete(int id){
        try {
            UnitEntities units = this.get("where id =" + id);
            UnitEntity unit = units.get(0);
            this.remove(unit);
        } catch (QueryException ex) {
            
        }
    }
}
