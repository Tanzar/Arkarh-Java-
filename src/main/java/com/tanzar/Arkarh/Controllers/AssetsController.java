/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Controllers;

import com.google.gson.Gson;
import com.tanzar.Arkarh.Elements.Asset;
import com.tanzar.Arkarh.Services.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author spako
 */
@RestController
public class AssetsController {
    
    @Autowired
    private AssetsService service;
    
    
    @RequestMapping(value = "/getAssetsCategories", method = RequestMethod.GET)
    public String getAllAssets(){
        Gson gson = new Gson();
        String[] categories = service.getCategories();
        return gson.toJson(categories);
    }
    
    @RequestMapping(value = "/getUnitsAssets", method = RequestMethod.GET)
    public String getAllUnits(){
        Gson gson = new Gson();
        Asset[] assets = service.getAllUnits();
        return gson.toJson(assets);
    }
    
    @RequestMapping(value="/asset/category={category}", method=RequestMethod.GET)
    public String getByCategory(@PathVariable(name="category") String assetCategory){
        Gson gson = new Gson();
        Asset[] assets = this.service.getAssetsByCategory(assetCategory);
        return gson.toJson(assets);
    }
}
