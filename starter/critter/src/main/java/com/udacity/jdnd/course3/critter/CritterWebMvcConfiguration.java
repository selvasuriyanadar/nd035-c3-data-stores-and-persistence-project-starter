package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.LongToPetDeserializer;
import com.udacity.jdnd.course3.critter.pet.PetModel;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetTypeToPetTypeModelDeserializer;
import com.udacity.jdnd.course3.critter.pet.PetTypeModel;
import com.udacity.jdnd.course3.critter.pet.PetTypeRepository;
import com.udacity.jdnd.course3.critter.user.LongToCustomerDeserializer;
import com.udacity.jdnd.course3.critter.user.CustomerModel;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.LongToEmployeeDeserializer;
import com.udacity.jdnd.course3.critter.user.EmployeeModel;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.schedule.LongToScheduleDeserializer;
import com.udacity.jdnd.course3.critter.schedule.ScheduleModel;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;

import jakarta.persistence.EntityManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

@Configuration
public class CritterWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Bean
    public Module customerModule() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier()
        {
            @Override public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer)
            {
                if (beanDesc.getBeanClass() == CustomerModel.class)
                    return new LongToCustomerDeserializer(entityManager, customerRepository, deserializer);
                return deserializer;
            }
        });
        return module;
    }

    @Bean
    public Module employeeModule() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier()
        {
            @Override public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer)
            {
                if (beanDesc.getBeanClass() == EmployeeModel.class)
                    return new LongToEmployeeDeserializer(entityManager, employeeRepository, deserializer);
                return deserializer;
            }
        });
        return module;
    }

    @Bean
    public Module petModule() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier()
        {
            @Override public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer)
            {
                if (beanDesc.getBeanClass() == PetModel.class)
                    return new LongToPetDeserializer(entityManager, petRepository, deserializer);
                return deserializer;
            }
        });
        return module;
    }

    @Bean
    public Module petTypeModule() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier()
        {
            @Override public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer)
            {
                if (beanDesc.getBeanClass() == PetTypeModel.class)
                    return new PetTypeToPetTypeModelDeserializer(entityManager, petTypeRepository, deserializer);
                return deserializer;
            }
        });
        return module;
    }

    @Bean
    public Module scheduleModule() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier()
        {
            @Override public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer)
            {
                if (beanDesc.getBeanClass() == ScheduleModel.class)
                    return new LongToScheduleDeserializer(entityManager, scheduleRepository, deserializer);
                return deserializer;
            }
        });
        return module;
    }

}
