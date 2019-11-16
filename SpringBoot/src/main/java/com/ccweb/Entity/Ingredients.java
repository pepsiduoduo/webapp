package com.ccweb.Entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Ingredients {


    public List<String> removeStringListDupli(List<String> stringList) {
        Set<String> ingredients = new LinkedHashSet<>();
        ingredients.addAll(stringList);

        stringList.clear();

        stringList.addAll(ingredients);
        return stringList;
    }

}



