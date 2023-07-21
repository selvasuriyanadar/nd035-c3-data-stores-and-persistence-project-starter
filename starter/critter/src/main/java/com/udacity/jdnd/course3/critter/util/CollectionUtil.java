package com.udacity.jdnd.course3.critter.util;

import java.util.*;

public class CollectionUtil {

    public static <T> boolean containsAny(Set<T> set1, Set<T> set2) {
        Set<T> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return !intersection.isEmpty();
    }

}
