package org.renaissance.jsondb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.renaissance.jsondb.operations.*;

import io.jsondb.JsonDBTemplate;

public class OperationGenerator {

    private int insertWeight;
    private int removeWeight;
    private int updateWeight;
    private int findByIdWeight;
    private int findOneWeight;
    private int findWeight;

    public void setInsertWeight(int insertWeight) {
        this.insertWeight = insertWeight;
    }
    public void setRemoveWeight(int removeWeight) {
        this.removeWeight = removeWeight;
    }
    public void setUpdateWeight(int updateWeight) {
        this.updateWeight = updateWeight;
    }
    public void setFindByIdWeight(int findByIdWeight) {
        this.findByIdWeight = findByIdWeight;
    }
    public void setFindOneWeight(int findOneWeight) {
        this.findOneWeight = findOneWeight;
    }
    public void setFindWeight(int findWeight) {
        this.findWeight = findWeight;
    }

    public DatabaseOperation[] operations(int count, Random rng, JsonDBTemplate jsondb){
        DatabaseOperation[] operations = new DatabaseOperation[count];

        for(int i = 0; i < count; ++i){
            operations[i] = new FindByIdOperation("ahoj"); // TODO: change
        }

        return operations;
      }
}
