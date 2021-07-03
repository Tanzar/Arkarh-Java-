/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Database.Containers.UnitAbilityEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.Database.DAO.ArtifactsDAO;
import com.tanzar.Arkarh.Database.DAO.UnitAbilitiesDAO;
import com.tanzar.Arkarh.Database.DAO.UnitsDAO;
import com.tanzar.Arkarh.Database.Entities.Leader.ArtifactEntity;
import com.tanzar.Arkarh.Database.Entities.Units.UnitAbilityEntity;
import com.tanzar.Arkarh.Database.Entities.Units.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Equipment.Artifact;
import com.tanzar.Arkarh.GamePlay.Leader.Leader;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanzar
 */
@Service
public class BattleService {
    
    @Autowired
    private UnitsDAO unitsDAO;
    
    @Autowired
    private UnitAbilitiesDAO abilitiesDAO;
    
    @Autowired
    private ArtifactsDAO artifactsDAO;
    
    public String battleSimulation(String simulationString){
        Json json = new Json(simulationString);
        Json attackersJson = json.getJson("attackers");
        Json defendersJson = json.getJson("defenders");
        Army attackers = this.convertJson(attackersJson);
        Army defenders = this.convertJson(defendersJson);
        int width = json.getInt("width");
        Battlefield battlefield = new Battlefield(width, attackers, defenders);
        CombatReport report = battlefield.fight();
        String result = Json.toJson(report);
        return result;
    }
    
    private Army convertJson(Json json){
        Units units = this.convetrUnits(json);
        Leader leader = this.convertLeader(json);
        Army army = new Army(leader);
        army.addUnits(units);
        return army;
    }
    
    private Units convetrUnits(Json json){
        Units units = new Units();
        String[] dataStrings = json.getStringArray("army");
        for(String dataString: dataStrings){
            Json unitJson = new Json(dataString);
            int id = unitJson.getInt("id");
            UnitEntity entity = this.unitsDAO.getById(id);
            UnitAbilityEntities entities = this.abilitiesDAO.getByUnitId(id);
            UnitAbilityEntity[] unitAbilities = entities.toArray();
            int count = unitJson.getInt("count");
            for(int i = 0; i < count; i++){
                Unit unit = new Unit(entity, unitAbilities);
                units.add(unit);
            }
        }
        return units;
    }
    
    private Leader convertLeader(Json json){
        Json leaderJson = json.getJson("leader");
        Leader leader = new Leader(leaderJson);
        String[] equipped = leaderJson.getStringArray("equipment");
        for(String index: equipped){
            int id = Integer.valueOf(index);
            try{
                ArtifactEntity entity = this.artifactsDAO.getById(id);
                Artifact artifact = new Artifact(entity);
                leader.equip(artifact);
            }
            catch(Exception ex){
                
            }
        }//test
        return leader;
    }
}
