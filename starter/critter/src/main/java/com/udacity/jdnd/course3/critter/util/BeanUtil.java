package com.udacity.jdnd.course3.critter.util;

import org.springframework.beans.BeanUtils;

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

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
