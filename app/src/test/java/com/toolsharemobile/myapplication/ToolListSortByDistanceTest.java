package com.toolsharemobile.myapplication;

import static com.toolsharemobile.myapplication.utility.SortToolList.sortToolList;

import com.amplifyframework.datastore.generated.model.Tool;
import com.amplifyframework.datastore.generated.model.ToolTypeEnum;
import com.toolsharemobile.myapplication.utility.ToolModelTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ToolListSortByDistanceTest {

    @Test
    public void toolListSortByDistance() {

        List<Tool> toolList = new ArrayList<>();

        Tool tool1 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(520.0000)
                .build();

        Tool tool2 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(20.0000)
                .build();

        Tool tool3 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(100.0000)
                .build();


        toolList.add(tool1);
        toolList.add(tool2);
        toolList.add(tool3);

        sortToolList(toolList);

        List<Double> distanceSorted = new ArrayList<>();
        for (Tool tool : toolList) {
            distanceSorted.add(tool.getDistance());
        }

        assert (distanceSorted.toString().equals("[20.0, 100.0, 520.0]"));


    }


    @Test
    public void toolListSortByDistanceHigherAccuracy() {

        List<Tool> toolList = new ArrayList<>();

        Tool tool1 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(520.0000)
                .build();

        Tool tool2 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(20.0000)
                .build();

        Tool tool3 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(100.0000)
                .build();


        Tool tool4 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(1.6700)
                .build();


        Tool tool5 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(10.0000)
                .build();

        Tool tool6 = Tool.builder()
                .toolType(ToolTypeEnum.CIRCULARSAW)
                .listedByUser("Jim")
                .lat(null)
                .lon(null)
                .id(null)
                .borrowByUser(null)
                .openBorrowRequest(null)
                .openReturnRequest(null)
                .isAvailable(null)
                .distance(1.0000)
                .build();


        toolList.add(tool1);
        toolList.add(tool2);
        toolList.add(tool3);
        toolList.add(tool4);
        toolList.add(tool5);
        toolList.add(tool6);

        sortToolList(toolList);

        List<Double> distanceSorted = new ArrayList<>();
        for (Tool tool : toolList) {
            distanceSorted.add(tool.getDistance());
        }

        assert (distanceSorted.toString().equals("[1.0, 1.67, 10.0, 20.0, 100.0, 520.0]"));
    }

}
