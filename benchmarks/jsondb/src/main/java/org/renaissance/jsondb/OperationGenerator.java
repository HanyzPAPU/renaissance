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

    String generateRandomJXQuery(Random rng){
        String[] queries = {
            "/.[pseudonym.length > 0]",
            "/albums/songs[length < 180.0]",
            "/recordLabels[netWorth > 1000000]"
        };
        return queries[rng.nextInt(queries.length)];
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
            return new FindByIdOperation(randomId);
        }

        choice -= findByIdWeight;

        String jxQuery = generateRandomJXQuery(rng);

        if (choice < findOneWeight) {
            return new FindOneOperation(jxQuery);
        }

        return new FindOperation(jxQuery);
    }

    public DatabaseOperation[][] operations(int cpuCount, int opPerCpu, Random rng, JsonDBTemplate jsondb){

        ListOrderedSet<String> usedNames = new ListOrderedSet();

        List<String> ids = jsondb.findAll(Artist.class).stream()
            .map(artist->artist.getRealName())
            .collect(Collectors.toList());

        usedNames.addAll(ids);

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
