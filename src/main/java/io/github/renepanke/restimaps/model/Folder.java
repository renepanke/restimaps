package io.github.renepanke.restimaps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonTypeName("folder")
public class Folder {

    @JsonProperty("childFolderCount")
    
    @NotNull
    @Min(0)
    private Integer childFolderCount;

    @JsonProperty("childFolderIds")
    
    @NotNull
    private List<String> childFolderIds;

    @JsonProperty("displayName")
    
    @Nullable
    private String displayName;

    @JsonProperty("id")
    
    @NotNull
    private String id;

    @JsonProperty("parentFolder")
    
    @Nullable
    private String parentFolder;

    @JsonProperty("itemCount")
    
    @NotNull
    @Min(0)
    private Integer itemCount;

    @JsonProperty("emailIds")
    
    @NotNull
    private List<String> emailIds;

    public Folder() {
    }

    public Integer getChildFolderCount() {
        return childFolderCount;
    }

    public void setChildFolderCount(Integer childFolderCount) {
        this.childFolderCount = childFolderCount;
    }

    public List<String> getChildFolderIds() {
        return childFolderIds;
    }

    public void setChildFolderIds(List<String> childFolderIds) {
        this.childFolderIds = childFolderIds;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(@Nullable String parentFolder) {
        this.parentFolder = parentFolder;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public List<String> getEmailIds() {
        return emailIds;
    }

    public void setEmailIds(List<String> emailIds) {
        this.emailIds = emailIds;
    }
}
