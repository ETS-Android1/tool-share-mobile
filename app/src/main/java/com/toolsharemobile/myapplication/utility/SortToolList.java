package com.toolsharemobile.myapplication.utility;

import com.amplifyframework.datastore.generated.model.Tool;

import java.util.Comparator;
import java.util.List;

public class SortToolList {

    public static void sortToolList(List<Tool> toolList){
        Comparator<Tool> toolComparator = Comparator.comparing(Tool::getDistance);
        toolList.sort(toolComparator);
    }
}
