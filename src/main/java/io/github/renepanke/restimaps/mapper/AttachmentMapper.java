package io.github.renepanke.restimaps.mapper;

import io.github.renepanke.restimaps.exceptions.checked.AttachmentDataSourceInputStreamNotRetrievable;
import io.github.renepanke.restimaps.model.Attachment;
import io.micronaut.context.annotation.Bean;
import jakarta.activation.DataSource;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.io.InputStream;

@Bean
@Singleton
public class AttachmentMapper {

    public Attachment map(String id, DataSource dataSource) throws AttachmentDataSourceInputStreamNotRetrievable {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        attachment.setName(dataSource.getName());
        attachment.setMediaType(dataSource.getContentType());
        try {
            try (InputStream inputStream = dataSource.getInputStream()) {
                attachment.setSizeInBytes((long) inputStream.readAllBytes().length);
            }
        } catch (IOException e) {
            throw new AttachmentDataSourceInputStreamNotRetrievable(e);
        }
        return attachment;
    }
}
