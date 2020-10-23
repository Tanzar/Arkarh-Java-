/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Containers.UnitAbilityEntities;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.DAO.UnitAbilitiesDAO;
import com.tanzar.Arkarh.DAO.UnitsDAO;
import com.tanzar.Arkarh.Entities.Unit.UnitAbilityEntity;
import com.tanzar.Arkarh.Entities.Unit.UnitEntity;
import com.tanzar.Arkarh.GamePlay.Combat.Battlefield;
import com.tanzar.Arkarh.GamePlay.Combat.Log.CombatReport;
import com.tanzar.Arkarh.GamePlay.Units.Army;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
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
        Army army = new Army();
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
                army.addUnit(unit);
            }
        }
        return army;
    }
}
