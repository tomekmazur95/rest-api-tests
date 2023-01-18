package com.crud.api.service

import com.crud.api.dto.CreateUser
import com.crud.api.dto.ViewUser
import com.crud.api.entity.User
import com.crud.api.repository.UserRepository
import spock.lang.Specification

class UserServiceTest extends Specification {
    UserRepository userRepository = Mock()
    UserService userService = new UserService(userRepository)

    def "testing happy path for create method"() {
        given:
        def mockedUser = new User()
        mockedUser.setId(1)
        mockedUser.setName("krzys")
        mockedUser.setSurname("mazur")

        def createUser = new CreateUser("krzys", "mazur")
        when:
        ViewUser actualResult = userService.create(createUser)

        then:
        1 * userRepository.save(_ as User) >> mockedUser
        actualResult == new ViewUser(1, "krzys", "mazur")
    }

    def "testing negative path for create method"() {
        when:
        userService.create(givenCreateUser)

        then:
        0 * userRepository.save(_ as User)
        def exception = thrown(IllegalArgumentException)
        exception.message == "User fields cannot be null"

        where:
        givenCreateUser               | _
        null                          | _
        new CreateUser(null, "mazur") | _
        new CreateUser("tomek", null) | _

    }

}
