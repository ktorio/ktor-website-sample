package com.jetbrains.handson.website

import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        static("/static") {
            resources("files")
        }
    }
}
