/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.DAO;

import com.tanzar.Arkarh.Database.Containers.ArtifactEntities;
import com.tanzar.Arkarh.Database.DAO.Abstracts.DAO;
import com.tanzar.Arkarh.Database.Entities.Leader.ArtifactEntity;
import com.tanzar.Arkarh.Exceptions.QueryException;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tanzar
 */
@Repository
@Transactional
public class ArtifactsDAO extends DAO<ArtifactEntity, ArtifactEntities>{

    public ArtifactsDAO() {
        super("ArtifactEntity");
    }

    @Override
    protected ArtifactEntities convertResults(List<ArtifactEntity> list) {
        ArtifactEntities entities = new ArtifactEntities();
        for(ArtifactEntity entity: list){
            entities.add(entity);
        }
        return entities;
    }
    
    public ArtifactEntity getById(int id){
        try {
            ArtifactEntities entities = this.get("where id =" + id);
            ArtifactEntity entity = entities.get(0);
            return entity;
        } catch (QueryException ex) {
            return null;
        }
    }
    
    public void delete(int id){
        try {
            ArtifactEntities entities = this.get("where id =" + id);
            ArtifactEntity entity = entities.get(0);
            this.remove(entity);
        } catch (QueryException ex) {
            
        }
    }
}
