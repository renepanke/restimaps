package io.github.renepanke.restimaps

import io.micronaut.runtime.Micronaut

class ApplicationK {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Micronaut.run(ApplicationK::class.java, *args)
        }
    }
}