/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tanzar.Arkarh.Containers.UnitEntities;
import com.tanzar.Arkarh.GamePlay.TMP.*;
import com.tanzar.Arkarh.GamePlay.Units.TargetsSelection;
import com.tanzar.Arkarh.GamePlay.Units.EffectSchool;
import com.tanzar.Arkarh.GamePlay.Units.Role;
import com.tanzar.Arkarh.GamePlay.Units.Unit;
import com.tanzar.Arkarh.GamePlay.Units.Units;
import com.tanzar.Arkarh.Services.UnitsService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Gson gson = new Gson();
        UnitEntities units = this.unitsService.getAllUnitEntities();
        String result = gson.toJson(units.toArray());
        return result;
    }
    
    @RequestMapping(value = "/getOptions", method = RequestMethod.GET)
    public String getOptions(){
        Gson gson = new Gson();
        EnumsCombined enums = new EnumsCombined();
        return gson.toJson(enums);
    }
    
    @RequestMapping(value = "/getFractions", method = RequestMethod.GET)
    public String getFractions(){
        Fraction fractions[] = Fraction.values();
        Gson gson = new Gson();
        return gson.toJson(fractions);
    }
    
    @RequestMapping(value = "/getRoles", method = RequestMethod.GET)
    public String getRoles(){
        Role roles[] = Role.values();
        Gson gson = new Gson();
        return gson.toJson(roles);
    }
    
    @RequestMapping(value = "/getTiers", method = RequestMethod.GET)
    public String getTiers(){
        Tier tiers[] = Tier.values();
        Gson gson = new Gson();
        return gson.toJson(tiers);
    }
    
    @RequestMapping(value = "/getUnitsCategories", method = RequestMethod.GET)
    public String getUnitCategories(){
        Category categories[] = Category.values();
        Gson gson = new Gson();
        return gson.toJson(categories);
    }
    
    @RequestMapping(value = "/getEffectTypes", method = RequestMethod.GET)
    public String getEffectTypes(){
        EffectSchool effects[] = EffectSchool.values();
        Gson gson = new Gson();
        return gson.toJson(effects);
    }
    
    @RequestMapping(value = "/getAttackTypes", method = RequestMethod.GET)
    public String getAttackTypes(){
        TargetsSelection attacks[] = TargetsSelection.values();
        Gson gson = new Gson();
        return gson.toJson(attacks);
    }
    
    @RequestMapping(value = "/getUnitForm", method = RequestMethod.GET)
    public String getUnitForm(){
        Unit unit = new Unit();
        Gson gson = new Gson();
        return gson.toJson(unit);
    }
    
    @RequestMapping(value="/addUnit", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String add(@RequestBody String form){
        String response = "Unit added.";
        Gson gson = new Gson();
        Unit newUnit = gson.fromJson(form, Unit.class);
        try{
            this.unitsService.add(newUnit);
        }
        catch(Exception ex){
            response = "Could not add Unit. Possible reason: unit with that name already exists";
        }
        return response;
    }
    
    @RequestMapping(value="/updateUnit", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String update(@RequestBody String form){
        String response = "Unit updated.";
        Gson gson = new Gson();
        Unit newUnit = gson.fromJson(form, Unit.class);
        try{
            this.unitsService.update(newUnit);
        }
        catch(Exception ex){
            response = "Could not update Unit.";
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
