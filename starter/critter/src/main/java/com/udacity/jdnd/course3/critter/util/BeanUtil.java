package com.udacity.jdnd.course3.critter.util;

import org.springframework.beans.BeanUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.Conditions;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil {

    public static <T> T transfer(Object source, T destination) {
        return transferWithIgnoreFields(source, destination);
    }

    public static <T> T transferWithIgnoreFields(Object source, T destination, String... ignoreProperties) {
        BeanUtils.copyProperties(source, destination, ignoreProperties);
        return destination;
    }

    public static ModelMapper getSimpleMapper() {
        return new ModelMapper();
    }

    public static ModelMapper getIgnoreNullMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    public static <T> T transferIfNotNull(T source, T destination) {
        getIgnoreNullMapper().map(source, destination);
        return destination;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public interface IdGetter<T> {
        public long getId(T obj);
    }

    public static <T> boolean checkEqualsById(T thisObj, Object obj, IdGetter<T> idGetter) {
        if (thisObj == null || obj == null || (obj.getClass() != thisObj.getClass())) {
            return false;
        }
        if (idGetter.getId(thisObj) <= 0) {
            return false;
        }
        if (idGetter.getId(thisObj) == idGetter.getId((T) obj)) {
            return true;
        }
        return false;
    }

    public static int hashById(long id) {
        return Objects.hash(id);
    }

}
