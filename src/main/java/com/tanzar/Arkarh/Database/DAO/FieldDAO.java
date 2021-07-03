/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tanzar.Arkarh.Database.DAO;

import com.tanzar.Arkarh.Containers.FieldContainer;
import com.tanzar.Arkarh.Database.DAO.Abstracts.DAO;
import com.tanzar.Arkarh.Database.DAO.Abstracts.FieldDAOInterface;
import com.tanzar.Arkarh.Entities.Field;
import com.tanzar.Arkarh.Exceptions.QueryException;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tanzar
 */
@Repository
public class FieldDAO extends DAO<Field, FieldContainer> implements FieldDAOInterface{

    public FieldDAO() {
        super("Field");
    }

    @Override
    protected FieldContainer convertResults(List<Field> list) {
        FieldContainer result = new FieldContainer();
        for(Field field: list){
            result.add(field);
        }
        return result;
    }

    @Override
    public Field getById(Integer id) {
        try {
            FieldContainer fields = this.get("where id = " + id);
            if(fields.size() != 1){
                throw new QueryException("");
            }
            else{
                return fields.get(0);
            }
        }
        catch (QueryException ex) {
            return new Field();
            
        }
    }

    @Override
    public FieldContainer getByMapId(Integer id) {
        try {
            return this.get("where idMap = " + id);
        }
        catch (QueryException ex) {
            return new FieldContainer();
        }
    }
    
}
