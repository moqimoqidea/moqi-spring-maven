package com.moqi.run

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * MoqiAppTest
 *
 * @author moqi
 * On 2021/8/15 23:21
 */
@SpringBootTest
class MoqiAppTest extends Specification {

    def "test"() {
        expect:
        1 == 1
    }

}
