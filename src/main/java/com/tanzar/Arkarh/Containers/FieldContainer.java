/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Containers;

import com.tanzar.Arkarh.Entities.Field;
import java.util.HashSet;

/**
 *
 * @author Tanzar
 */
public class FieldContainer extends HashSetContainer<Field>{

    @Override
    public Field[] toArray(HashSet<Field> hs) {
        Field[] fields = new Field[hs.size()];
        hs.toArray(fields);
        return fields;
    }
    
}
