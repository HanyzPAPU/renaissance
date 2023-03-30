package org.renaissance.jsondb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.renaissance.jsondb.operations.*;

public class OperationGenerator {

    public static DatabaseOperation[] operations(int count, Random rng){
        DatabaseOperation[] operations = new DatabaseOperation[count];

        for(int i = 0; i < count; ++i){
            operations[i] = new FindByIdOperation("ahoj"); // TODO: change
        }

        return operations;
      }
}
