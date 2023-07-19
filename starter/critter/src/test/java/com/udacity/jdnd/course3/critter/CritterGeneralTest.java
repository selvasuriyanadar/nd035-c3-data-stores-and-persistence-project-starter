package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.user.*;
import com.udacity.jdnd.course3.critter.pet.PetModel;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.*;

@SpringBootTest
public class CritterGeneralTest {

    /**
     * Verifying proper hashcode generation for Customer model
     */
    @Test
    public void testCustomerHash(){
        Set<CustomerModel> models = new HashSet<>();
        CustomerModel tempModel = new CustomerModel();
        tempModel.setId(1);
        models.add(tempModel);
        tempModel = new CustomerModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new CustomerModel();
        tempModel.setId(3);
        models.add(tempModel);

        Assertions.assertTrue(models.size() == 3);

        models = new HashSet<>();
        tempModel = new CustomerModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new CustomerModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new CustomerModel();
        tempModel.setId(5);
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 2);

        models = new HashSet<>();
        tempModel = new CustomerModel();
        models.add(tempModel);
        tempModel = new CustomerModel();
        models.add(tempModel);
        tempModel = new CustomerModel();
        models.add(tempModel);
        tempModel = new CustomerModel();
        models.add(tempModel);
        tempModel = new CustomerModel();
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 1);

        models = new HashSet<>();
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 1);
    }

    /**
     * Verifying proper hashcode generation for Employee model
     */
    @Test
    public void testEmployeeHash(){
        Set<EmployeeModel> models = new HashSet<>();
        EmployeeModel tempModel = new EmployeeModel();
        tempModel.setId(1);
        models.add(tempModel);
        tempModel = new EmployeeModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new EmployeeModel();
        tempModel.setId(3);
        models.add(tempModel);

        Assertions.assertTrue(models.size() == 3);

        models = new HashSet<>();
        tempModel = new EmployeeModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new EmployeeModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new EmployeeModel();
        tempModel.setId(5);
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 2);

        models = new HashSet<>();
        tempModel = new EmployeeModel();
        models.add(tempModel);
        tempModel = new EmployeeModel();
        models.add(tempModel);
        tempModel = new EmployeeModel();
        models.add(tempModel);
        tempModel = new EmployeeModel();
        models.add(tempModel);
        tempModel = new EmployeeModel();
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 1);

        models = new HashSet<>();
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 1);
    }

    /**
     * Verifying proper hashcode generation for Pet model
     */
    @Test
    public void testPetHash(){
        Set<PetModel> models = new HashSet<>();
        PetModel tempModel = new PetModel();
        tempModel.setId(1);
        models.add(tempModel);
        tempModel = new PetModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new PetModel();
        tempModel.setId(3);
        models.add(tempModel);

        Assertions.assertTrue(models.size() == 3);

        models = new HashSet<>();
        tempModel = new PetModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new PetModel();
        tempModel.setId(2);
        models.add(tempModel);
        tempModel = new PetModel();
        tempModel.setId(5);
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 2);

        models = new HashSet<>();
        tempModel = new PetModel();
        models.add(tempModel);
        tempModel = new PetModel();
        models.add(tempModel);
        tempModel = new PetModel();
        models.add(tempModel);
        tempModel = new PetModel();
        models.add(tempModel);
        tempModel = new PetModel();
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 1);

        models = new HashSet<>();
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        tempModel = null;
        models.add(tempModel);
        Assertions.assertTrue(models.size() == 1);
    }

}
