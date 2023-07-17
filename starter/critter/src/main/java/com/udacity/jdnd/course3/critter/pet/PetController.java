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

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetModel model = BeanUtil.transferWithIgnoreFields(petDTO, new PetModel(), "id");
        model.setOwner(new CustomerModel());
        model.getOwner().setId(petDTO.getOwnerId());
        PetDTO petDTOResponse = BeanUtil.transfer(petService.savePet(model), new PetDTO());
        petDTOResponse.setOwnerId(model.getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<PetModel> modelOpt = petRepository.findById(petId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Pet Not Found.");
        }
        PetDTO petDTOResponse = BeanUtil.transfer(modelOpt.get(), new PetDTO());
        petDTOResponse.setOwnerId(modelOpt.get().getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petRepository.findAll().stream().map(model -> {
            PetDTO petDTOResponse = BeanUtil.transfer(model, new PetDTO());
            petDTOResponse.setOwnerId(model.getOwner().getId());
            return petDTOResponse;
        }).toList();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petRepository.fetchByOwnerId(ownerId).stream().map(model -> {
            PetDTO petDTOResponse = BeanUtil.transfer(model, new PetDTO());
            petDTOResponse.setOwnerId(model.getOwner().getId());
            return petDTOResponse;
        }).toList();
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable long petId) {
        Optional<PetModel> modelOpt = petRepository.findById(petId);
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Pet Not Found.");
        }
        petService.deletePet(modelOpt.get());
    }
}
