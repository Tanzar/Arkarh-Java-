/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers.Gameplay;

import com.tanzar.Arkarh.Containers.HashSetContainer;
import com.tanzar.Arkarh.Entities.Leader.ArtifactEntity;
import java.util.HashSet;

/**
 *
 * @author Tanzar
 */
public class ArtifactEntities extends HashSetContainer<ArtifactEntity>{

    @Override
    protected ArtifactEntity[] toArray(HashSet<ArtifactEntity> data) {
        ArtifactEntity[] array = new ArtifactEntity[data.size()];
        data.toArray(array);
        return array;
    }
    
}
