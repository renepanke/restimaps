package io.github.renepanke.restimaps.repository

import jakarta.activation.DataSource
import java.io.InputStream
import java.io.OutputStream

class InputStreamDataSourceK : DataSource {

    private val contentType: String
    private val inputStream: InputStream
    private val name: String

    constructor(contentType: String, inputStream: InputStream) : this(contentType, inputStream, "unnamed")

    constructor(contentType: String, inputStream: InputStream, name: String) {
        this.contentType = normalizeMediaType(contentType)
        this.inputStream = inputStream
        this.name = name
    }

    override fun getInputStream(): InputStream? {
        return inputStream
    }

    override fun getOutputStream(): OutputStream? {
        throw UnsupportedOperationException("DataSource is read-only")
    }

    override fun getContentType(): String? {
        return contentType
    }

    override fun getName(): String? {
        return name
    }
}