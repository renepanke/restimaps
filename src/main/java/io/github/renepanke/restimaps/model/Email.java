package io.github.renepanke.restimaps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonTypeName("email")
public class Email {

    @JsonProperty("attachmentIds")
    
    @NotNull
    private List<String> attachmentIds;

    @JsonProperty("bcc")
    
    @NotNull
    private List<String> bcc;

    @JsonProperty("cc")
    
    @NotNull
    private List<String> cc;

    @JsonProperty("creationTimestamp")
    
    @Nullable
    private String creationTimestamp;

    @JsonProperty("folderId")
    
    @NotNull
    private String folderId;

    @JsonProperty("from")
    
    @NotNull
    private String from;

    @JsonProperty("internetMessageId")
    
    @NotNull
    private String internetMessagId;

    @JsonProperty("isRead")
    
    @NotNull
    private Boolean isRead;

    @JsonProperty("isReadReceiptRequested")
    
    @NotNull
    private Boolean isReadReceiptRequested;

    @JsonProperty("lastModifiedTimestamp")
    
    @Nullable
    private String lastModifiedTimestamp;

    @JsonProperty("plainContent")
    
    @Nullable
    private String plainContent;

    @JsonProperty("htmlContent")
    
    @Nullable
    private String htmlContent;

    @JsonProperty("receivedTimestamp")
    
    @Nullable
    private String receivedTimestamp;

    @JsonProperty("replyTo")
    
    @NotNull
    private List<String> replyTo;

    @JsonProperty("sender")
    
    @Nullable
    private String sender;

    @JsonProperty("sentTimestamp")
    
    @Nullable
    private String sentTimestamp;

    @JsonProperty("subject")
    
    @NotNull
    private String subject;

    @JsonProperty("to")
    
    @NotNull
    private List<String> to;

    @JsonProperty("hasAttachments")
    
    @NotNull
    private Boolean hasAttachments;

    @JsonProperty("hasHtmlContent")
    
    @NotNull
    private Boolean hasHtmlContent;

    @JsonProperty("hasPlainContent")
    
    @NotNull
    private Boolean hasPlainContent;


    public Email() {
    }

    public List<String> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<String> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    @Nullable
    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(@Nullable String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getInternetMessagId() {
        return internetMessagId;
    }

    public void setInternetMessagId(String internetMessagId) {
        this.internetMessagId = internetMessagId;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Boolean getReadReceiptRequested() {
        return isReadReceiptRequested;
    }

    public void setReadReceiptRequested(Boolean readReceiptRequested) {
        isReadReceiptRequested = readReceiptRequested;
    }

    @Nullable
    public String getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(@Nullable String lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    @Nullable
    public String getPlainContent() {
        return plainContent;
    }

    public void setPlainContent(@Nullable String plainContent) {
        this.plainContent = plainContent;
    }

    @Nullable
    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(@Nullable String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Nullable
    public String getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public void setReceivedTimestamp(@Nullable String receivedTimestamp) {
        this.receivedTimestamp = receivedTimestamp;
    }

    public List<String> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(List<String> replyTo) {
        this.replyTo = replyTo;
    }

    @Nullable
    public String getSender() {
        return sender;
    }

    public void setSender(@Nullable String sender) {
        this.sender = sender;
    }

    @Nullable
    public String getSentTimestamp() {
        return sentTimestamp;
    }

    public void setSentTimestamp(@Nullable String sentTimestamp) {
        this.sentTimestamp = sentTimestamp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public Boolean getHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(Boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public Boolean getHasHtmlContent() {
        return hasHtmlContent;
    }

    public void setHasHtmlContent(Boolean hasHtmlContent) {
        this.hasHtmlContent = hasHtmlContent;
    }

    public Boolean getHasPlainContent() {
        return hasPlainContent;
    }

    public void setHasPlainContent(Boolean hasPlainContent) {
        this.hasPlainContent = hasPlainContent;
    }
}
