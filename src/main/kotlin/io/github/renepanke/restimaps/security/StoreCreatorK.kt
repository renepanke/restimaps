package io.github.renepanke.restimaps.security

import jakarta.mail.Session
import jakarta.mail.Store
import java.util.*

fun createStore(host: String, port: Int, username: String, password: String): Store {
    val props = Properties()
    props.put("mail.store.protocol", "imaps")
    props.put("mail.imaps.host", host)
    props.put("mail.imaps.port", port)
    props.put("mail.imaps.ssl.enable", "true")
    props.put("mail.imaps.ssl.require", "true")
    val session = Session.getInstance(props)
    val store = session.getStore("imaps")
    store.connect(username, password)
    return store
}