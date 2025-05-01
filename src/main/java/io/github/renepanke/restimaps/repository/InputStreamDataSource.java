package io.github.renepanke.restimaps.repository;

import io.micronaut.http.MediaType;
import jakarta.activation.DataSource;

import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamDataSource implements DataSource {
    // https://github.com/apache/commons-email/blob/master/commons-email2-jakarta/src/main/java/org/apache/commons/mail2/jakarta/activation/InputStreamDataSource.java#L34

    private final String contentType;
    private final InputStream inputStream;
    private final String name;

    public InputStreamDataSource(InputStream inputStream, String contentType) {
        this(inputStream, contentType, null);
    }

    public InputStreamDataSource(InputStream inputStream, String contentType, String dataSourceName) {
        this.inputStream = inputStream;
        this.contentType = contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM;
        this.name = dataSourceName;
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
