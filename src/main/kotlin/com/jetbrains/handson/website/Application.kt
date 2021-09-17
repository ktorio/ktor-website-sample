package com.jetbrains.handson.website

import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.server.http.content.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries), ""))
        }
        post("/submit") {
            val params = call.receiveParameters()
            val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val body = params["body"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val newEntry = BlogEntry(headline, body)
            blogEntries.add(0, newEntry)
            call.respondHtml {
                body {
                    h1 {
                        +"Thanks for submitting your entry!"
                    }
                    p {
                        +"We've submitted your new entry titled "
                        b {
                            +newEntry.headline
                        }
                    }
                    p {
                        +"You have submitted a total of ${blogEntries.count()} articles!"
                    }
                    a("/") {
                        +"Go back"
                    }
                }
            }
        }
    }
}
