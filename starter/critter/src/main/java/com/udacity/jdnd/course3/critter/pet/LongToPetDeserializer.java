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

public class LongToPetDeserializer extends StdDeserializer<PetModel> implements ResolvableDeserializer {

    private EntityManager entityManager;

    private PetRepository petRepository;

    private JsonDeserializer<?> defaultDeserializer;

    public LongToPetDeserializer(EntityManager entityManager, PetRepository petRepository, JsonDeserializer<?> defaultDeserializer) {
        super(PetModel.class);
        this.entityManager = entityManager;
        this.petRepository = petRepository;
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public PetModel deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (!p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return (PetModel) defaultDeserializer.deserialize(p, ctxt);
        }

        long id = p.getValueAsLong();
        if (!petRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet Not Found.");
        }

        return entityManager.getReference(PetModel.class, id);
    }

    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
    }

}
