package com.udacity.jdnd.course3.critter.schedule;

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

public class LongToScheduleDeserializer extends StdDeserializer<ScheduleModel> implements ResolvableDeserializer {

    private EntityManager entityManager;

    private ScheduleRepository scheduleRepository;

    private JsonDeserializer<?> defaultDeserializer;

    public LongToScheduleDeserializer(EntityManager entityManager, ScheduleRepository scheduleRepository, JsonDeserializer<?> defaultDeserializer) {
        super(ScheduleModel.class);
        this.entityManager = entityManager;
        this.scheduleRepository = scheduleRepository;
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public ScheduleModel deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (!p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return (ScheduleModel) defaultDeserializer.deserialize(p, ctxt);
        }

        long id = p.getValueAsLong();
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("Schedule Not Found.");
        }

        return entityManager.getReference(ScheduleModel.class, id);
    }

    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
    }

}
