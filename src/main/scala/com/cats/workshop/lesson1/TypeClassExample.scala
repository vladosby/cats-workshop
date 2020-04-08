package com.cats.workshop.lesson1

object TypeClassExample extends App {
  //Type class
  trait Validator[A] {
    type Result[B] = Either[List[String], B]

    def validate(a: A): Result[A]
  }
}
