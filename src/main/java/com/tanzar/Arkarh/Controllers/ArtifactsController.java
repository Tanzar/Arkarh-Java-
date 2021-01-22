/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.tanzar.Arkarh.Converter.Json;
import com.tanzar.Arkarh.GamePlay.Equipment.Artifact;
import com.tanzar.Arkarh.GamePlay.Equipment.ArtifactEffect;
import com.tanzar.Arkarh.GamePlay.Equipment.Artifacts;
import com.tanzar.Arkarh.GamePlay.Equipment.Slot;
import com.tanzar.Arkarh.Services.ArtifactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tanzar
 */
@RestController
@RequestMapping(value = "/artifacts")
public class ArtifactsController {
    
    @Autowired
    private ArtifactsService service;
    
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAll(){
        Artifacts all = this.service.getAll();
        String json = Json.toJson(all.toArray());
        return json;
    }
    
    @RequestMapping(value = "/getOptions", method = RequestMethod.GET)
    public String getOptions(){
        return this.service.getOptions();
    }
    
    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String getForm(){
        Artifact form = this.service.getForm();
        return Json.toJson(form);
    }
    
    @RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String add(@RequestBody String form){
        String response = "Artifact added.";
        try{
            this.service.add(form);
        }
        catch(Exception ex){
            response = "Could not add Artifact.";
        }
        return response;
    }
    
    @RequestMapping(value = "/remove={id}", method = RequestMethod.GET)
    public void removeUnit(@PathVariable(name="id") String id){
        int index = Integer.parseInt(id);
        this.service.remove(index);
    }
    
    @RequestMapping(value="/update", method=RequestMethod.POST, consumes="application/json; charset=UTF-8")
    public String update(@RequestBody String form){
        String response = "Artifact updated.";
        try{
            this.service.update(form);
        }
        catch(Exception ex){
            response = "Could not update Artifact.";
        }
        return response;
    }
}
