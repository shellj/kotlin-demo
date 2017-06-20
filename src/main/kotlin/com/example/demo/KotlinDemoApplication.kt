package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Stream
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@SpringBootApplication
class KotlinDemoApplication(val userRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        Stream.of("shellj=27", "jacy=26", "baby=0", "girl=18")
                .map { fn -> fn.split("=") }
                .forEach { datas -> userRepository.save(User(datas[0], datas[1].toInt())) }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinDemoApplication::class.java, *args)
}

@RestController
class UserController(val userRepository: UserRepository) {

    @GetMapping("/users/{name}")
    fun get(@PathVariable name: String) = userRepository.findOne(Example.of(User(name)))

    @GetMapping("/users/save")
    fun save(@RequestParam name: String,
             @RequestParam age: Int? = 18): List<User> {
        userRepository.save(User(name, age))
        return userRepository.findAll()
    }

    @GetMapping("/users")
    fun all() = userRepository.findAll()
}


interface UserRepository : JpaRepository<User, Int>

@Entity
data class User(var name: String? = "default",
                var age: Int? = null,
                @Id
                @GeneratedValue
                var id: Int? = null)