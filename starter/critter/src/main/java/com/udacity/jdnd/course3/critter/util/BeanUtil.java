package com.udacity.jdnd.course3.critter.util;

import org.springframework.beans.BeanUtils;

public class BeanUtil {

    public static <T> T transfer(Object source, T destination) {
        BeanUtils.copyProperties(source, destination);
        return destination;
    }

}
