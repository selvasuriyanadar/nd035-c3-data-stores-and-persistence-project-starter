package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerModel;

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

    private PetModel complete(PetModel petModel) {
        petModel.setOwnerId(petRepository.fetchOwnerIdByPetId(petModel.getId()));
        return petModel;
    }

    @PostMapping
    public PetModel savePet(@RequestBody PetModel petModel) {
        return complete(petService.savePet(petModel));
    }

    @GetMapping("/{petId}")
    public PetModel getPet(@PathVariable("petId") PetModel petModel) {
        return complete(petModel);
    }

    @GetMapping
    public List<PetModel> getPets(){
        return petRepository.findAll().stream().map(model -> complete(model)).toList();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetModel> getPetsByOwner(@PathVariable long ownerId) {
        return petRepository.fetchByOwnerId(ownerId).stream().map(model -> complete(model)).toList();
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable("petId") PetModel petModel) {
        petService.deletePet(petModel);
    }
}
