package com.cats.workshop.lesson1

case class Cat(name: String, age: Int)

object TypeClassExample extends App {

  //Type class
  trait Validator[A] {
    type Result[B] = Either[List[String], B]

    def validate(a: A): Result[A]
  }

  // Type class instances
  object ValidatorInstances {
    implicit val validatorCat: Validator[Cat] = c => Either.cond(c.age > 0, c, List("Age cannot be less than 0"))
    implicit val validatorInt: Validator[Int] = i => Either.cond(i > 0, i, List("Int cannot be less than 0"))
  }

}
