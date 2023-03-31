package org.renaissance.jsondb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.renaissance.jsondb.model.Artist;
import org.renaissance.jsondb.operations.*;

import io.jsondb.JsonDBTemplate;
import org.apache.commons.collections4.set.ListOrderedSet;

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


    DatabaseOperation generateRandomOperation(Random rng, DataGenerator dataGenerator, ListOrderedSet<String> usedNames){

        final int weightSum = 
            insertWeight +
            removeWeight +
            updateWeight +
            findByIdWeight +
            findOneWeight +
            findWeight;

        int choice = rng.nextInt(weightSum);
        
        String randomId = usedNames.asList().get(rng.nextInt(usedNames.size()));

        if (choice < insertWeight){
            return new InsertOperation(dataGenerator.artist(rng));
        }

        choice -= insertWeight;

        if (choice < removeWeight){
            usedNames.remove(randomId);
            return new RemoveOperation(randomId);
        }

        choice -= removeWeight;

        if (choice < updateWeight){
            Artist artist = dataGenerator.artist(rng);
            // Remove the ID because we will change it, but it is registered as used
            usedNames.remove(artist.getRealName());
            artist.setRealName(randomId);
            return new UpdateOperation(artist);
        }

        choice -= updateWeight;

        if (choice < findByIdWeight){
            // TODO: find by present ID / non-present?
        }

        choice -= findByIdWeight;

        if (choice < findOneWeight) {
            // TODO: generate findOne request with random jxQuery
        }

        // TODO: generate find request with random jxQuery
        return null;
    }

    public DatabaseOperation[][] operations(int cpuCount, int opPerCpu, Random rng, JsonDBTemplate jsondb){

        ListOrderedSet<String> usedNames = new ListOrderedSet();

        usedNames.addAll(
            jsondb.findAll(Artist.class).stream()
            .map(artist->artist.getRealName())
            .collect(Collectors.toList())
        );

        DataGenerator dataGenerator = new DataGenerator(usedNames);

        DatabaseOperation[][] operations = new DatabaseOperation[cpuCount][opPerCpu];

        for (int opId = 0; opId < opPerCpu; ++opId) {
            for(int cpu = 0; cpu < cpuCount; ++cpu) {
                operations[cpu][opId] = generateRandomOperation(rng, dataGenerator, usedNames);
            }
        }

        return operations;
      }
}
