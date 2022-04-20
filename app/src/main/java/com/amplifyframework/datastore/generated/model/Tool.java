package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Tool type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tools")
public final class Tool implements Model {
  public static final QueryField ID = field("Tool", "id");
  public static final QueryField TOOL_TYPE = field("Tool", "toolType");
  public static final QueryField LISTED_BY_USER = field("Tool", "listedByUser");
  public static final QueryField LOCATION = field("Tool", "location");
  public static final QueryField BORROW_BY_USER = field("Tool", "borrowByUser");
  public static final QueryField S3IMAGE_KEY = field("Tool", "S3imageKey");
  public static final QueryField IS_AVAILABLE = field("Tool", "isAvailable");
  public static final QueryField OPEN_RETURN_REQUEST = field("Tool", "openReturnRequest");
  public static final QueryField OPEN_BORROW_REQUEST = field("Tool", "openBorrowRequest");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ToolTypeEnum", isRequired = true) ToolTypeEnum toolType;
  private final @ModelField(targetType="String", isRequired = true) String listedByUser;
  private final @ModelField(targetType="String", isRequired = true) String location;
  private final @ModelField(targetType="String") String borrowByUser;
  private final @ModelField(targetType="String") String S3imageKey;
  private final @ModelField(targetType="Boolean") Boolean isAvailable;
  private final @ModelField(targetType="Boolean") Boolean openReturnRequest;
  private final @ModelField(targetType="Boolean") Boolean openBorrowRequest;
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
  
  public String getLocation() {
      return location;
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
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Tool(String id, ToolTypeEnum toolType, String listedByUser, String location, String borrowByUser, String S3imageKey, Boolean isAvailable, Boolean openReturnRequest, Boolean openBorrowRequest) {
    this.id = id;
    this.toolType = toolType;
    this.listedByUser = listedByUser;
    this.location = location;
    this.borrowByUser = borrowByUser;
    this.S3imageKey = S3imageKey;
    this.isAvailable = isAvailable;
    this.openReturnRequest = openReturnRequest;
    this.openBorrowRequest = openBorrowRequest;
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
              ObjectsCompat.equals(getLocation(), tool.getLocation()) &&
              ObjectsCompat.equals(getBorrowByUser(), tool.getBorrowByUser()) &&
              ObjectsCompat.equals(getS3imageKey(), tool.getS3imageKey()) &&
              ObjectsCompat.equals(getIsAvailable(), tool.getIsAvailable()) &&
              ObjectsCompat.equals(getOpenReturnRequest(), tool.getOpenReturnRequest()) &&
              ObjectsCompat.equals(getOpenBorrowRequest(), tool.getOpenBorrowRequest()) &&
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
      .append(getLocation())
      .append(getBorrowByUser())
      .append(getS3imageKey())
      .append(getIsAvailable())
      .append(getOpenReturnRequest())
      .append(getOpenBorrowRequest())
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
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("borrowByUser=" + String.valueOf(getBorrowByUser()) + ", ")
      .append("S3imageKey=" + String.valueOf(getS3imageKey()) + ", ")
      .append("isAvailable=" + String.valueOf(getIsAvailable()) + ", ")
      .append("openReturnRequest=" + String.valueOf(getOpenReturnRequest()) + ", ")
      .append("openBorrowRequest=" + String.valueOf(getOpenBorrowRequest()) + ", ")
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      toolType,
      listedByUser,
      location,
      borrowByUser,
      S3imageKey,
      isAvailable,
      openReturnRequest,
      openBorrowRequest);
  }
  public interface ToolTypeStep {
    ListedByUserStep toolType(ToolTypeEnum toolType);
  }
  

  public interface ListedByUserStep {
    LocationStep listedByUser(String listedByUser);
  }
  

  public interface LocationStep {
    BuildStep location(String location);
  }
  

  public interface BuildStep {
    Tool build();
    BuildStep id(String id);
    BuildStep borrowByUser(String borrowByUser);
    BuildStep s3imageKey(String s3imageKey);
    BuildStep isAvailable(Boolean isAvailable);
    BuildStep openReturnRequest(Boolean openReturnRequest);
    BuildStep openBorrowRequest(Boolean openBorrowRequest);
  }
  

  public static class Builder implements ToolTypeStep, ListedByUserStep, LocationStep, BuildStep {
    private String id;
    private ToolTypeEnum toolType;
    private String listedByUser;
    private String location;
    private String borrowByUser;
    private String S3imageKey;
    private Boolean isAvailable;
    private Boolean openReturnRequest;
    private Boolean openBorrowRequest;
    @Override
     public Tool build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Tool(
          id,
          toolType,
          listedByUser,
          location,
          borrowByUser,
          S3imageKey,
          isAvailable,
          openReturnRequest,
          openBorrowRequest);
    }
    
    @Override
     public ListedByUserStep toolType(ToolTypeEnum toolType) {
        Objects.requireNonNull(toolType);
        this.toolType = toolType;
        return this;
    }
    
    @Override
     public LocationStep listedByUser(String listedByUser) {
        Objects.requireNonNull(listedByUser);
        this.listedByUser = listedByUser;
        return this;
    }
    
    @Override
     public BuildStep location(String location) {
        Objects.requireNonNull(location);
        this.location = location;
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
    private CopyOfBuilder(String id, ToolTypeEnum toolType, String listedByUser, String location, String borrowByUser, String s3imageKey, Boolean isAvailable, Boolean openReturnRequest, Boolean openBorrowRequest) {
      super.id(id);
      super.toolType(toolType)
        .listedByUser(listedByUser)
        .location(location)
        .borrowByUser(borrowByUser)
        .s3imageKey(s3imageKey)
        .isAvailable(isAvailable)
        .openReturnRequest(openReturnRequest)
        .openBorrowRequest(openBorrowRequest);
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
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
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
  }
  
}