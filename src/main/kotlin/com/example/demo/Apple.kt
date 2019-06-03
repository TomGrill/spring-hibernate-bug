package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class ContainerEntity<TData : DataEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = false)
    lateinit var data: TData
}


@MappedSuperclass
abstract class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    val created = Date()
}

@Entity
class Car : ContainerEntity<CarData>()

@Entity
class CarData : DataEntity() {
    var color = "red"
}

@Entity
class Apple : ContainerEntity<AppleData>()

@Entity
class AppleData : DataEntity() {
    var age = 32
}

/**
 * WORKS
 */

interface CarRepository : CrudRepository<Car, Int> {
    fun findByDataColor(color: String): Car?
}


/**
 * FAILS
 */

//interface AppleRepository : CrudRepository<Apple, Int> {
//    fun findByDataAge(age: Int): Apple?
//}