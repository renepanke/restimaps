package io.github.renepanke.restimaps.repository

import io.github.renepanke.restimaps.extensions.openIfClosed
import jakarta.inject.Singleton
import jakarta.mail.FetchProfile
import jakarta.mail.Folder
import jakarta.mail.Message
import java.util.*

private const val MESSAGE_ID_HEADER = "Message-ID"

@Singleton
class EmailRepositoryK {

    fun getEmails(folder: Folder): List<Message> {
        folder.openIfClosed()
        val msgs = folder.messages
        folder.fetch(msgs, FETCH_PROFILE_ALL)
        return msgs.toList()
    }

    fun getEmailIds(folder: Folder): List<String> {
        folder.openIfClosed()
        val msgs = folder.messages
        folder.fetch(msgs, FETCH_PROFILE_ENVELOPE)
        return msgs.map {
            it.getHeader(MESSAGE_ID_HEADER)[0]
        }.toList()
    }

    fun getEmail(folder: Folder, internetMessageId: String): Optional<Message> {
        folder.openIfClosed()
        val msgs = folder.messages
        folder.fetch(msgs, FETCH_PROFILE_ALL)
        return Optional.ofNullable(msgs.find {
            it.getHeader(MESSAGE_ID_HEADER)[0] == internetMessageId
        })
    }
}

private val FETCH_PROFILE_ALL: FetchProfile = FetchProfile().apply {
    add(FetchProfile.Item.CONTENT_INFO)
    add(FetchProfile.Item.ENVELOPE)
    add(FetchProfile.Item.FLAGS)
    add(FetchProfile.Item.SIZE)
}
private val FETCH_PROFILE_ENVELOPE: FetchProfile = FetchProfile().apply { add(FetchProfile.Item.ENVELOPE) }

