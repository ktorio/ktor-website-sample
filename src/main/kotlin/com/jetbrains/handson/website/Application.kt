package com.jetbrains.handson.website

import freemarker.cache.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.html.respondHtml
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    routing {
        static("/static") {
            resources("files")
        }
        get("/html") {
            call.respondHtml {
                head {
                    title {
                        +"Returning HTML using Kotlinx.HTML"
                    }
                }
                body {
                    h1 {
                        +"Kotlinx.Html"
                    }
                    p {
                        +"We're using a static HTML DSL"
                    }
                    val numbers = 1..10
                    ul {
                        numbers.forEach {
                            li {
                                value = it.toString()
                                +"Item $it"
                            }
                        }
                    }
                }
            }
        }
        get("/freemarker") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }
    }
}
