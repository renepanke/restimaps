package io.github.renepanke.restimaps.extensions

import jakarta.mail.Folder

fun Folder.openIfClosed() {
    if (!this.isOpen) {
        this.open(Folder.READ_WRITE)
    }
}

fun Folder.closeIfOpen() {
    if (this.isOpen) {
        this.close(false)
    }
}
