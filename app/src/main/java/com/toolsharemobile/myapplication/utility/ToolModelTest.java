package com.toolsharemobile.myapplication.utility;

import com.amplifyframework.datastore.generated.model.ToolTypeEnum;

public class ToolModelTest {

    ToolTypeEnum toolType;
    String listedByUser;
    String lat;
    String lon;
    String borrowByUser;
    String S3imageKey;
    Boolean isAvailable;
    Boolean openReturnRequest;
    Boolean openBorrowRequest;
    float distance;


    public ToolModelTest(ToolTypeEnum toolType, String listedByUser, String lat, String lon, String borrowByUser, String s3imageKey, Boolean isAvailable, Boolean openReturnRequest, Boolean openBorrowRequest, float distance) {
        this.toolType = toolType;
        this.listedByUser = listedByUser;
        this.lat = lat;
        this.lon = lon;
        this.borrowByUser = borrowByUser;
        S3imageKey = s3imageKey;
        this.isAvailable = isAvailable;
        this.openReturnRequest = openReturnRequest;
        this.openBorrowRequest = openBorrowRequest;
        this.distance = distance;
    }



}
