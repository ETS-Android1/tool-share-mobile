package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Tool type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tools", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Tool implements Model {
  public static final QueryField ID = field("Tool", "id");
  public static final QueryField TOOL_TYPE = field("Tool", "toolType");
  public static final QueryField LISTED_BY_USER = field("Tool", "listedByUser");
  public static final QueryField LAT = field("Tool", "lat");
  public static final QueryField LON = field("Tool", "lon");
  public static final QueryField BORROW_BY_USER = field("Tool", "borrowByUser");
  public static final QueryField S3IMAGE_KEY = field("Tool", "S3imageKey");
  public static final QueryField IS_AVAILABLE = field("Tool", "isAvailable");
  public static final QueryField OPEN_RETURN_REQUEST = field("Tool", "openReturnRequest");
  public static final QueryField OPEN_BORROW_REQUEST = field("Tool", "openBorrowRequest");
  public static final QueryField DISTANCE = field("Tool", "distance");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ToolTypeEnum", isRequired = true) ToolTypeEnum toolType;
  private final @ModelField(targetType="String", isRequired = true) String listedByUser;
  private final @ModelField(targetType="String") String lat;
  private final @ModelField(targetType="String") String lon;
  private final @ModelField(targetType="String") String borrowByUser;
  private final @ModelField(targetType="String") String S3imageKey;
  private final @ModelField(targetType="Boolean") Boolean isAvailable;
  private final @ModelField(targetType="Boolean") Boolean openReturnRequest;
  private final @ModelField(targetType="Boolean") Boolean openBorrowRequest;
  private final @ModelField(targetType="Float") Double distance;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public ToolTypeEnum getToolType() {
      return toolType;
  }
  
  public String getListedByUser() {
      return listedByUser;
  }
  
  public String getLat() {
      return lat;
  }
  
  public String getLon() {
      return lon;
  }
  
  public String getBorrowByUser() {
      return borrowByUser;
  }
  
  public String getS3imageKey() {
      return S3imageKey;
  }
  
  public Boolean getIsAvailable() {
      return isAvailable;
  }
  
  public Boolean getOpenReturnRequest() {
      return openReturnRequest;
  }
  
  public Boolean getOpenBorrowRequest() {
      return openBorrowRequest;
  }
  
  public Double getDistance() {
      return distance;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Tool(String id, ToolTypeEnum toolType, String listedByUser, String lat, String lon, String borrowByUser, String S3imageKey, Boolean isAvailable, Boolean openReturnRequest, Boolean openBorrowRequest, Double distance) {
    this.id = id;
    this.toolType = toolType;
    this.listedByUser = listedByUser;
    this.lat = lat;
    this.lon = lon;
    this.borrowByUser = borrowByUser;
    this.S3imageKey = S3imageKey;
    this.isAvailable = isAvailable;
    this.openReturnRequest = openReturnRequest;
    this.openBorrowRequest = openBorrowRequest;
    this.distance = distance;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Tool tool = (Tool) obj;
      return ObjectsCompat.equals(getId(), tool.getId()) &&
              ObjectsCompat.equals(getToolType(), tool.getToolType()) &&
              ObjectsCompat.equals(getListedByUser(), tool.getListedByUser()) &&
              ObjectsCompat.equals(getLat(), tool.getLat()) &&
              ObjectsCompat.equals(getLon(), tool.getLon()) &&
              ObjectsCompat.equals(getBorrowByUser(), tool.getBorrowByUser()) &&
              ObjectsCompat.equals(getS3imageKey(), tool.getS3imageKey()) &&
              ObjectsCompat.equals(getIsAvailable(), tool.getIsAvailable()) &&
              ObjectsCompat.equals(getOpenReturnRequest(), tool.getOpenReturnRequest()) &&
              ObjectsCompat.equals(getOpenBorrowRequest(), tool.getOpenBorrowRequest()) &&
              ObjectsCompat.equals(getDistance(), tool.getDistance()) &&
              ObjectsCompat.equals(getCreatedAt(), tool.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), tool.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getToolType())
      .append(getListedByUser())
      .append(getLat())
      .append(getLon())
      .append(getBorrowByUser())
      .append(getS3imageKey())
      .append(getIsAvailable())
      .append(getOpenReturnRequest())
      .append(getOpenBorrowRequest())
      .append(getDistance())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Tool {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("toolType=" + String.valueOf(getToolType()) + ", ")
      .append("listedByUser=" + String.valueOf(getListedByUser()) + ", ")
      .append("lat=" + String.valueOf(getLat()) + ", ")
      .append("lon=" + String.valueOf(getLon()) + ", ")
      .append("borrowByUser=" + String.valueOf(getBorrowByUser()) + ", ")
      .append("S3imageKey=" + String.valueOf(getS3imageKey()) + ", ")
      .append("isAvailable=" + String.valueOf(getIsAvailable()) + ", ")
      .append("openReturnRequest=" + String.valueOf(getOpenReturnRequest()) + ", ")
      .append("openBorrowRequest=" + String.valueOf(getOpenBorrowRequest()) + ", ")
      .append("distance=" + String.valueOf(getDistance()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ToolTypeStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Tool justId(String id) {
    return new Tool(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      toolType,
      listedByUser,
      lat,
      lon,
      borrowByUser,
      S3imageKey,
      isAvailable,
      openReturnRequest,
      openBorrowRequest,
      distance);
  }
  public interface ToolTypeStep {
    ListedByUserStep toolType(ToolTypeEnum toolType);
  }
  

  public interface ListedByUserStep {
    BuildStep listedByUser(String listedByUser);
  }
  

  public interface BuildStep {
    Tool build();
    BuildStep id(String id);
    BuildStep lat(String lat);
    BuildStep lon(String lon);
    BuildStep borrowByUser(String borrowByUser);
    BuildStep s3imageKey(String s3imageKey);
    BuildStep isAvailable(Boolean isAvailable);
    BuildStep openReturnRequest(Boolean openReturnRequest);
    BuildStep openBorrowRequest(Boolean openBorrowRequest);
    BuildStep distance(Double distance);
  }
  

  public static class Builder implements ToolTypeStep, ListedByUserStep, BuildStep {
    private String id;
    private ToolTypeEnum toolType;
    private String listedByUser;
    private String lat;
    private String lon;
    private String borrowByUser;
    private String S3imageKey;
    private Boolean isAvailable;
    private Boolean openReturnRequest;
    private Boolean openBorrowRequest;
    private Double distance;
    @Override
     public Tool build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Tool(
          id,
          toolType,
          listedByUser,
          lat,
          lon,
          borrowByUser,
          S3imageKey,
          isAvailable,
          openReturnRequest,
          openBorrowRequest,
          distance);
    }
    
    @Override
     public ListedByUserStep toolType(ToolTypeEnum toolType) {
        Objects.requireNonNull(toolType);
        this.toolType = toolType;
        return this;
    }
    
    @Override
     public BuildStep listedByUser(String listedByUser) {
        Objects.requireNonNull(listedByUser);
        this.listedByUser = listedByUser;
        return this;
    }
    
    @Override
     public BuildStep lat(String lat) {
        this.lat = lat;
        return this;
    }
    
    @Override
     public BuildStep lon(String lon) {
        this.lon = lon;
        return this;
    }
    
    @Override
     public BuildStep borrowByUser(String borrowByUser) {
        this.borrowByUser = borrowByUser;
        return this;
    }
    
    @Override
     public BuildStep s3imageKey(String s3imageKey) {
        this.S3imageKey = s3imageKey;
        return this;
    }
    
    @Override
     public BuildStep isAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }
    
    @Override
     public BuildStep openReturnRequest(Boolean openReturnRequest) {
        this.openReturnRequest = openReturnRequest;
        return this;
    }
    
    @Override
     public BuildStep openBorrowRequest(Boolean openBorrowRequest) {
        this.openBorrowRequest = openBorrowRequest;
        return this;
    }
    
    @Override
     public BuildStep distance(Double distance) {
        this.distance = distance;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, ToolTypeEnum toolType, String listedByUser, String lat, String lon, String borrowByUser, String s3imageKey, Boolean isAvailable, Boolean openReturnRequest, Boolean openBorrowRequest, Double distance) {
      super.id(id);
      super.toolType(toolType)
        .listedByUser(listedByUser)
        .lat(lat)
        .lon(lon)
        .borrowByUser(borrowByUser)
        .s3imageKey(s3imageKey)
        .isAvailable(isAvailable)
        .openReturnRequest(openReturnRequest)
        .openBorrowRequest(openBorrowRequest)
        .distance(distance);
    }
    
    @Override
     public CopyOfBuilder toolType(ToolTypeEnum toolType) {
      return (CopyOfBuilder) super.toolType(toolType);
    }
    
    @Override
     public CopyOfBuilder listedByUser(String listedByUser) {
      return (CopyOfBuilder) super.listedByUser(listedByUser);
    }
    
    @Override
     public CopyOfBuilder lat(String lat) {
      return (CopyOfBuilder) super.lat(lat);
    }
    
    @Override
     public CopyOfBuilder lon(String lon) {
      return (CopyOfBuilder) super.lon(lon);
    }
    
    @Override
     public CopyOfBuilder borrowByUser(String borrowByUser) {
      return (CopyOfBuilder) super.borrowByUser(borrowByUser);
    }
    
    @Override
     public CopyOfBuilder s3imageKey(String s3imageKey) {
      return (CopyOfBuilder) super.s3imageKey(s3imageKey);
    }
    
    @Override
     public CopyOfBuilder isAvailable(Boolean isAvailable) {
      return (CopyOfBuilder) super.isAvailable(isAvailable);
    }
    
    @Override
     public CopyOfBuilder openReturnRequest(Boolean openReturnRequest) {
      return (CopyOfBuilder) super.openReturnRequest(openReturnRequest);
    }
    
    @Override
     public CopyOfBuilder openBorrowRequest(Boolean openBorrowRequest) {
      return (CopyOfBuilder) super.openBorrowRequest(openBorrowRequest);
    }
    
    @Override
     public CopyOfBuilder distance(Double distance) {
      return (CopyOfBuilder) super.distance(distance);
    }
  }
  
}
