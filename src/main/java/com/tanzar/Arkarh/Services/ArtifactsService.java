/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Containers.Gameplay.ArtifactEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.DAO.ArtifactsDAO;
import com.tanzar.Arkarh.Entities.Leader.ArtifactEntity;
import com.tanzar.Arkarh.GamePlay.Equipment.Artifact;
import com.tanzar.Arkarh.GamePlay.Equipment.ArtifactEffect;
import com.tanzar.Arkarh.GamePlay.Equipment.Artifacts;
import com.tanzar.Arkarh.GamePlay.Equipment.Slot;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanzar
 */
@Service
public class ArtifactsService {
    
    @Autowired
    private ArtifactsDAO artifactsDAO;
    
    public Artifacts getAll(){
        ArtifactEntities entities = this.artifactsDAO.getAll();
        Artifacts artifacts = new Artifacts();
        for(ArtifactEntity entity: entities.toArray()){
            Artifact artifact = new Artifact(entity);
            artifacts.add(artifact);
        }
        return artifacts;
    }
    
    public Artifact getForm(){
        return new Artifact();
    }
    
    public String getOptions(){
        Slot[] slots = Slot.values();
        ArtifactEffect[] effects = ArtifactEffect.values();
        EffectSchool[] schools = EffectSchool.values();
        Json json = new Json();
        json.add("slots", slots);
        json.add("effects", effects);
        json.add("schools", schools);
        return json.formJson();
    }
    
    public void add(String json){
        Json artifactJson = new Json(json);
        Artifact artifact = new Artifact(artifactJson);
        ArtifactEntity entity = new ArtifactEntity(artifact);
        this.artifactsDAO.add(entity);
    }
    
    public void update(String json){
        Json artifactJson = new Json(json);
        Artifact artifact = new Artifact(artifactJson);
        ArtifactEntity entity = new ArtifactEntity(artifact);
        this.artifactsDAO.update(entity);
    }
    
    public void remove(int id){
        this.artifactsDAO.delete(id);
    }
}
