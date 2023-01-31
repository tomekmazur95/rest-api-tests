package com.crud.api.service

import com.crud.api.dto.CreateUser
import com.crud.api.dto.UpdateUser
import com.crud.api.dto.ViewUser
import com.crud.api.entity.User
import com.crud.api.error.UserNotFoundException
import com.crud.api.repository.UserRepository
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserRepository userRepository = Mock()
    UserService userService = new UserService(userRepository)

    def "happy path for create method"() {
        given:
        def mockedUser = new User()
        mockedUser.setId(1)
        mockedUser.setName("Krzysztof")
        mockedUser.setSurname("Kowalski")

        def createUser = new CreateUser("Krzysztof", "Kowalski")
        when:
        ViewUser actualResult = userService.create(createUser)

        then:
        1 * userRepository.save(_ as User) >> mockedUser
        actualResult == new ViewUser(1, "Krzysztof", "Kowalski")
    }

    def "negative path for create method"() {
        when:
        userService.create(givenCreateUser)

        then:
        0 * userRepository.save(_ as User)
        def exception = thrown(IllegalArgumentException)
        exception.message == "User fields cannot be null"

        where:
        givenCreateUser                  | _
        null                             | _
        new CreateUser(null, "Kowalska") | _
        new CreateUser("Anna", null)     | _

    }

    def "happy path for update method"() {
        given:
        def updateUser = new UpdateUser("Krzysztof", "Kononowicz")
        def id = 2

        def mockedUser = new User()
        mockedUser.setId(id)
        mockedUser.setName("Jan")
        mockedUser.setSurname("Kowalski")
        when:

        def actualResult = userService.updateUser(id, updateUser)

        then:
        1 * userRepository.findById(id) >> Optional.of(mockedUser)
        1 * userRepository.save(_ as User) >> mockedUser
        actualResult == Optional.of(new ViewUser(id, "Krzysztof", "Kononowicz"))
    }


    def "happy path for findById method"() {
        given:
        def id = 3

        def mockedUser = new User()
        mockedUser.setId(id)
        mockedUser.setName("Marcin")
        mockedUser.setSurname("Kowalski")

        when:
        def actualResult = userService.findById(id)
        then:
        1 * userRepository.findById(id) >> Optional.of(mockedUser)
        actualResult == Optional.of(new ViewUser(id, "Marcin", "Kowalski"))
    }

    def "happy path for findAll method"() {
        given:
        def mockedUser = new User()
        mockedUser.setId(1)
        mockedUser.setName("Marek")
        mockedUser.setSurname("Kowalski")
        def mockedUser2 = new User()
        mockedUser2.setId(2)
        mockedUser2.setName("Tomasz")
        mockedUser2.setSurname("Mazur")

        when:
        def actualResult = userService.findAll()
        then:
        1 * userRepository.findAll() >> List.of(mockedUser, mockedUser2)
        actualResult == List.of(new ViewUser(1, "Marek", "Kowalski"),
                new ViewUser(2, "Tomasz", "Mazur"))
    }

    def "negative path for deleteById method"() {
        when:
        userService.deleteById(givenId)

        then:
        1 * userRepository.existsById(givenId) >> false
        def exception = thrown(UserNotFoundException)
        exception.message == "User with id: " + givenId + " not found"
        0 * userRepository.deleteById(givenId)

        where:
        givenId | _
        1       | _
        2       | _
        3       | _
    }

    def "positive path for deleteById method"() {
        given:

        def mockedUser = new User()
        mockedUser.setId(5)
        mockedUser.setName("Tomasz")
        mockedUser.setSurname("Mazur")

        def id = mockedUser.getId()

        when:
        def actualResult = userService.deleteById(id)
        then:
        1 * userRepository.existsById(id) >> true
        actualResult == userRepository.deleteById(id)
    }
}
