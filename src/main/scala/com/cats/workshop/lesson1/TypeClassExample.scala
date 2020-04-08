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

  // Type class interface
  object ValidatorInterface {

    implicit final class ValidatorWrapper[A: Validator](v: A) {
      def validate: Either[List[String], A] = implicitly[Validator[A]].validate(v)
    }

  }

  // implicit resolution
  object RecursiveResolution {
    implicit def optionValidator[A: Validator]: Validator[Option[A]] = {
      case Some(v) => implicitly[Validator[A]].validate(v).map(Some(_))
      case None => Left(List("Cannot validate None"))
    }
  }

  //examples
  import ValidatorInstances._
  import ValidatorInterface._
  import RecursiveResolution._

  println(2.validate)
  println((-2).validate)
  println(Cat("2", -2).validate)
  println(Cat("2", 2).validate)


  println(Option(Cat("2", 2)).validate)
  println(Option(Cat("2", -2)).validate)
  println(Option(2).validate)
  println(Option(-2).validate)
}
