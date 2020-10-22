/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.TMP.*;
import com.tanzar.Arkarh.GamePlay.Units.Abilities.Base.UnitAbilities;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import com.tanzar.Arkarh.Services.UnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author spako
 */
@RestController
@RequestMapping(value = "/unitEditor")
public class UnitEditorController {
    
    @Autowired
    private UnitsService unitsService;
    
    @RequestMapping(value = "/getAllUnits", method = RequestMethod.GET)
    public String getAllUnits(){
        Units units = this.unitsService.getAll();
        String result = Json.toJson(units.toArray());
        return result;
    }
    
    @RequestMapping(value = "/getUnitAbilities={unitId}", method = RequestMethod.GET)
    public String getUnitAbilities(@PathVariable(name="unitId") String id){
        int index = Integer.parseInt(id);
        String result = "[]";
        try{
            UnitAbilities abilities = this.unitsService.getUnitAbilities(index);
            result = Json.toJson(abilities.toArray());
        }
        catch(Exception ex){
            
        }
        return result;
    }
    
    @RequestMapping(value = "/getOptions", method = RequestMethod.GET)
    public String getOptions(){
        return this.unitsService.getOptions();
    }
    
    @RequestMapping(value = "/getAbilitiesForms", method = RequestMethod.GET)
    public String getAbilitiesForms(){
        return this.unitsService.getAbilitiesPatterns();
    }
    
    @RequestMapping(value = "/getPassiveForm", method = RequestMethod.GET)
    public String getPassiveForm(){
        return this.unitsService.getPassiveForm();
    }
    
    @RequestMapping(value = "/getUnitForm", method = RequestMethod.GET)
    public String getUnitForm(){
        return this.unitsService.newUnitAsJson();
    }
    
    @RequestMapping(value="/addUnit", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String add(@RequestBody String form){
        String response = "Unit added.";
        try{
            this.unitsService.add(form);
        }
        catch(Exception ex){
            response = "Could not add Unit. Possible reason: unit with that name already exists";
        }
        return response;
    }
    
    @RequestMapping(value="/addAbility", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String addAbility(@RequestBody String form){
        String response = "Ability added.";
        try{
            this.unitsService.addAbility(form);
        }
        catch(Exception ex){
            response = "Could not add Ability.";
        }
        return response;
    }
    
    @RequestMapping(value = "/removeUnit={id}", method = RequestMethod.GET)
    public void removeUnit(@PathVariable(name="id") String id){
        int index = Integer.parseInt(id);
        this.unitsService.removeUnit(index);
    }
    
    @RequestMapping(value = "/removeAbility={id}", method = RequestMethod.GET)
    public void removeAbility(@PathVariable(name="id") String id){
        int index = Integer.parseInt(id);
        this.unitsService.removeAbility(index);
    }
    
    @RequestMapping(value="/updateUnit", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String update(@RequestBody String form){
        String response = "Unit updated.";
        try{
            this.unitsService.update(form);
        }
        catch(Exception ex){
            response = "Could not update Unit.";
        }
        return response;
    }
    
    @RequestMapping(value="/updateAbility", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String updateAbility(@RequestBody String form){
        String response = "Ability updated.";
        try{
            this.unitsService.updateAbility(form);
        }
        catch(Exception ex){
            response = "Could not update Ability.";
        }
        return response;
    }
    
    @RequestMapping(value="/deleteUnit", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String delete(@RequestBody String form){
        String response = "Unit removed.";
        Gson gson = new Gson();
        Unit unit = gson.fromJson(form, Unit.class);
        try{
            this.unitsService.remove(unit);
        }
        catch(Exception ex){
            response = "Could not remove Unit.";
        }
        return response;
    }
}
