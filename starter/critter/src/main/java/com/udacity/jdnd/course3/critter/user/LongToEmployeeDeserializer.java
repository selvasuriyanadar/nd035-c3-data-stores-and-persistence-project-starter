package com.udacity.jdnd.course3.critter.user;

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

public class LongToEmployeeDeserializer extends StdDeserializer<EmployeeModel> implements ResolvableDeserializer {

    private EntityManager entityManager;

    private EmployeeRepository employeeRepository;

    private JsonDeserializer<?> defaultDeserializer;

    public LongToEmployeeDeserializer(EntityManager entityManager, EmployeeRepository employeeRepository, JsonDeserializer<?> defaultDeserializer) {
        super(EmployeeModel.class);
        this.entityManager = entityManager;
        this.employeeRepository = employeeRepository;
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public EmployeeModel deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (!p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return (EmployeeModel) defaultDeserializer.deserialize(p, ctxt);
        }

        long id = p.getValueAsLong();
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee Not Found.");
        }

        return entityManager.getReference(EmployeeModel.class, id);
    }

    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
    }

}
