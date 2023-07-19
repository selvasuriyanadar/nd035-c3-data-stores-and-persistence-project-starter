package com.udacity.jdnd.course3.critter.pet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import jakarta.persistence.EntityManager;

import java.io.IOException;

public class PetTypeToPetTypeModelDeserializer extends StdDeserializer<PetTypeModel> implements ResolvableDeserializer {

    private EntityManager entityManager;

    private PetTypeRepository petRepository;

    private JsonDeserializer<?> defaultDeserializer;

    public PetTypeToPetTypeModelDeserializer(EntityManager entityManager, PetTypeRepository petRepository, JsonDeserializer<?> defaultDeserializer) {
        super(PetTypeModel.class);
        this.entityManager = entityManager;
        this.petRepository = petRepository;
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public PetTypeModel deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (!p.hasToken(JsonToken.VALUE_STRING)) {
            return (PetTypeModel) defaultDeserializer.deserialize(p, ctxt);
        }

        PetType id;
        try {
            id = PetType.valueOf(p.getValueAsString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Pet Type Not Found.");
        }
        if (!petRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet Type Not Found.");
        }

        return entityManager.getReference(PetTypeModel.class, id);
    }

    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
    }

}
