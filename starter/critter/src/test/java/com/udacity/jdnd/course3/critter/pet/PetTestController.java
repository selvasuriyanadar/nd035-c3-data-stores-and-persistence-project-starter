package com.udacity.jdnd.course3.critter.pet;

import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.DayOfWeek;
import java.util.*;

public class PetTestController {

    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    public PetTestController(int port, TestRestTemplate testRestTemplate) {
        this.baseUrl = "http://localhost:" + port + "/pet";
        this.testRestTemplate = testRestTemplate;
    }

    public PetDTO savePet(PetDTO petDTO) {
        return testRestTemplate.postForObject(baseUrl, petDTO, PetDTO.class);
    }

    public PetDTO getPet(long petId) {
        return testRestTemplate.getForObject(baseUrl + "/" + petId, PetDTO.class);
    }

    public List<PetDTO> getPets(){
        return Arrays.stream(testRestTemplate.getForObject(baseUrl, PetDTO[].class)).toList();
    }

    public List<PetDTO> getPetsByOwner(long ownerId) {
        return Arrays.stream(testRestTemplate.getForObject(baseUrl + "/owner/" + ownerId, PetDTO[].class)).toList();
    }

    public void deletePet(long petId) {
        testRestTemplate.delete(baseUrl + "/" + petId);
    }

}
