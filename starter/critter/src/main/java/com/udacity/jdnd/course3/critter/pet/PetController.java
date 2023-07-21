package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.util.BeanUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    private PetModel complete(PetModel petModel) {
        petModel.setOwnerId(petRepository.fetchOwnerIdByPetId(petModel.getId()));
        petModel.setReleventActivities(petModel.getType().getReleventActivities());
        petModel.setTypeEnum(petModel.getType().getType());
        return petModel;
    }

    @JsonView(PetViews.Public.class)
    @PostMapping
    public PetModel createPet(@RequestBody PetModel petModel) {
        return complete(petService.savePet(petModel));
    }

    @JsonView(PetViews.Public.class)
    @GetMapping("/{petId}")
    public PetModel getPet(@PathVariable("petId") PetModel petModel) {
        return complete(petModel);
    }

    @JsonView(PetViews.Public.class)
    @GetMapping
    public List<PetModel> getPets(){
        return petRepository.findAll().stream().map(model -> complete(model)).toList();
    }

    @JsonView(PetViews.Public.class)
    @GetMapping("/owner/{ownerId}")
    public List<PetModel> getPetsByOwner(@PathVariable long ownerId) {
        return petRepository.fetchByOwnerId(ownerId).stream().map(model -> complete(model)).toList();
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable("petId") PetModel petModel) {
        petService.deletePet(petModel);
    }

    @PostMapping("/petType")
    public PetTypeModel configurePetType(@RequestBody PetTypeModel petTypeModel) {
        return petService.configurePetType(petTypeModel);
    }

    @GetMapping("/petType")
    public List<PetTypeModel> getAllPetTypeConfigs() {
        return petTypeRepository.findAll();
    }

    @GetMapping("/petType/{petType}")
    public PetTypeModel getAllPetTypeConfigs(@PathVariable("petType") PetTypeModel petTypeModel) {
        return petTypeModel;
    }
}
