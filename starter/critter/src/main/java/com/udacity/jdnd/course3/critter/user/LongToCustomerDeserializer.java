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

public class LongToCustomerDeserializer extends StdDeserializer<CustomerModel> implements ResolvableDeserializer {

    private EntityManager entityManager;

    private CustomerRepository customerRepository;

    private JsonDeserializer<?> defaultDeserializer;

    public LongToCustomerDeserializer(EntityManager entityManager, CustomerRepository customerRepository, JsonDeserializer<?> defaultDeserializer) {
        super(CustomerModel.class);
        this.entityManager = entityManager;
        this.customerRepository = customerRepository;
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public CustomerModel deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (!p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return (CustomerModel) defaultDeserializer.deserialize(p, ctxt);
        }

        long id = p.getValueAsLong();
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer Not Found.");
        }

        return entityManager.getReference(CustomerModel.class, id);
    }

    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
    }

}
