/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.DAO;

import com.tanzar.Arkarh.Database.Containers.SpellEntities;
import com.tanzar.Arkarh.Database.DAO.Abstracts.DAO;
import com.tanzar.Arkarh.Database.Entities.Leader.SpellEntity;
import java.util.List;

/**
 *
 * @author Tanzar
 */
public class SpellsDAO extends DAO<SpellEntity, SpellEntities> {

    public SpellsDAO() {
        super("SpellEntity");
    }

    @Override
    protected SpellEntities convertResults(List<SpellEntity> list) {
        SpellEntities entities = new SpellEntities();
        for(SpellEntity entity : list){
            entities.add(entity);
        }
        return entities;
    }
    
}
