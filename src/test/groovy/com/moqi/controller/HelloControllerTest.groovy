package com.moqi.controller

import com.moqi.service.UserService
import spock.lang.Specification

/**
 *
 * @author moqi
 * @date 1/9/22 01:29
 */
class HelloControllerTest extends Specification {

    UserService userService = Mock()
    HelloController helloController = new HelloController(userService)

    def "Person"() {
        given:
        userService.getRandomNumber(*_) >> 666

        when:
        def personResult = helloController.person(10)
        println("personResult = $personResult")

        then:
        personResult.length() > 0
    }

}
