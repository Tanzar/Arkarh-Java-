/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tanzar
 */
public class Assets {
    private List<AssetsList> assets;
    
    public Assets() throws IOException{
        this.setupAssets();
    }
    
    public void setupAssets() throws IOException{
        this.assets = new ArrayList<AssetsList>();
        File folder = new File(".");
        String path = "/src/main/resources/static/img";
        String systemPath = folder.getCanonicalPath() + path;
        List<File> filesList = new ArrayList<File>();
        this.listFiles(systemPath, filesList);
        for(File assetFile: filesList){
            this.newAsset(assetFile);
        }
    }
    
    public void listFiles(String directoryName, List<File> files) {
    File directory = new File(directoryName);
    File[] fList = directory.listFiles();
    if(fList != null)
        for (File file : fList) {      
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listFiles(file.getAbsolutePath(), files);
            }
        }
    }
    
    private void newAsset(File assetFile){
        String path = this.makeAssetPath(assetFile);
        String name = assetFile.getName();
        Asset asset = new Asset(path, name);
        String category = this.setupCategoryName(path, name);
        boolean found = false;
        name = name.substring(0, name.length() - 4);
        for(AssetsList list: this.assets){
            if(list.isCategory(category)){
                found = true;
                list.add(asset);
                break;
            }
        }
        if(!found){
            AssetsList newList = new AssetsList(category);
            newList.add(asset);
            this.assets.add(newList);
        }
    }
    
    private String makeAssetPath(File assetFile){
        String folderPath = assetFile.getAbsolutePath();
        int index = folderPath.lastIndexOf("\\img");
        String path = folderPath.substring(index);
        return path;
    }
    
    private String setupCategoryName(String path, String filename){
        int endIndex = path.indexOf(filename);
        if(endIndex > -1){
            path = path.substring(0, endIndex);
            if(path.length() > 5){
                return path.substring(5);
            }
            else{
                return "none";
            }
        }
        else{
            return path;
        }
    }
    
    public Asset[] getAssets(String category){
        for(AssetsList assetsList: this.assets){
            if(assetsList.isCategory(category)){
                return assetsList.toArray();
            }
        }
        return new Asset[0];
    }
    
    public String[] getCategories(){
        String[] categories = new String[this.assets.size()];
        for(int i = 0; i < categories.length; i++){
            AssetsList list = this.assets.get(i);
            String category = list.getCategory();
            categories[i] = category;
        }
        return categories;
    }
}
