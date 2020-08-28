/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Services;

import com.tanzar.Arkarh.Elements.Asset;
import com.tanzar.Arkarh.Elements.Assets;
import com.tanzar.Arkarh.Elements.AssetsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author spako
 */
@Service
public class AssetsService {
     
    @Autowired
    @Qualifier("assets")
    private Assets assets;
    
    public Asset[] getAssetsByCategory(String category){
        return this.assets.getAssets(category);
    }
    
    public String[] getCategories(){
        return this.assets.getCategories();
    }
    
    public Asset[] getAllUnits(){
        AssetsList list = new AssetsList("units");
        String[] categories = this.assets.getCategories();
        for(String category: categories){
            if(category.contains("units")){
                Asset[] assets = this.assets.getAssets(category);
                for(Asset asset: assets){
                    list.add(asset);
                }
            }
        }
        return list.toArray();
    }
}
