package com.example.demo

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTest extends Specification {

    def "date format"() {
        given:
        def date = "2007-12-12 18:30:52.000"
        def formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

        when:
        def result = LocalDateTime.parse(date, formatter)

        then:
        println(result)
    }
}
