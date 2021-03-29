/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.GamePlay.Combat.Log;


/**
 *
 * @author spako
 */
public abstract class Entry {
    
    private EntryGroup entryGroup;
    private String entryText;

    public Entry(EntryGroup group) {
        this.entryGroup = group;
        this.entryText = "";
    }
    
    public Entry(EntryGroup action, String entryText) {
        this.entryGroup = action;
        this.entryText = entryText;
    }
    
    protected void setGroup(EntryGroup group){
        this.entryGroup = group;
    }
    
    public EntryGroup getGroup(){
        return this.entryGroup;
    }
    
    protected void setText(String text){
        this.entryText = text;
    }
    
    public String getText(){
        return this.entryText;
    }
    
    @Override
    public String toString(){
        return this.entryText;
    }
}
