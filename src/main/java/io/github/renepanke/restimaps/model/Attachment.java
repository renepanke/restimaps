package io.github.renepanke.restimaps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeName("attachment")
public class Attachment {

    @JsonProperty("contentDisposition")
    
    @Nullable
    private String contentDisposition;

    @JsonProperty("id")
    
    @NotNull
    private String id;

    @JsonProperty("mediaType")
    
    @Nullable
    private String mediaType;

    @JsonProperty("name")
    
    @NotBlank
    private String name;

    @JsonProperty("sizeInBytes")
    
    @Nullable
    @Min(0)
    private Long sizeInBytes;

    public Attachment() {
    }

    @Nullable
    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(@Nullable String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(@Nullable String mediaType) {
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(@Nullable Long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }
}
