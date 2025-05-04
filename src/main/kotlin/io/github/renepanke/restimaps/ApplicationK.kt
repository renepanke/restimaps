package io.github.renepanke.restimaps

import io.micronaut.runtime.Micronaut
import io.pyroscope.http.Format
import io.pyroscope.javaagent.EventType
import io.pyroscope.javaagent.PyroscopeAgent
import io.pyroscope.javaagent.config.Config

class ApplicationK {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PyroscopeAgent.start(
                Config.Builder()
                    .setApplicationName("restimaps")
                    .setServerAddress("http://192.168.181.167:4040")
                    .setFormat(Format.JFR)
                    .setProfilingEvent(EventType.ITIMER)
                    .build()
            )
            Micronaut.run(ApplicationK::class.java, *args)
        }
    }
}