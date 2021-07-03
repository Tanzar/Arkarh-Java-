/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.Containers;

import com.tanzar.Arkarh.Containers.HashSetContainer;
import com.tanzar.Arkarh.Database.Entities.Leader.SpellEntity;
import java.util.HashSet;

/**
 *
 * @author Tanzar
 */
public class SpellEntities extends HashSetContainer<SpellEntity>{

    @Override
    protected SpellEntity[] toArray(HashSet<SpellEntity> data) {
        SpellEntity[] spells = new SpellEntity[data.size()];
        data.toArray(spells);
        return spells;
    }
    
}
