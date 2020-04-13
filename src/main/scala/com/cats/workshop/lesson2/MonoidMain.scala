package com.cats.workshop.lesson2

import Instance._
import Syntax._

case class Gold(startValue: Int, speed: Int, hours: Int)

case class Money(amount: BigDecimal, currency: String) // semigroup???


object MonoidMain extends App {

  def associativity[A: Monoid](a: A, b: A, c: A): Boolean = {
    implicitly[Monoid[A]].combine(implicitly[Monoid[A]].combine(a, b), c) == implicitly[Monoid[A]]
      .combine(implicitly[Monoid[A]].combine(b, c), a)
  }

  def leftIdentity[A: Monoid](value: A): Boolean = {
    implicitly[Monoid[A]].combine(Monoid[A].empty, value) == value
  }

  def rightIdentity[A: Monoid](value: A): Boolean = {
    implicitly[Monoid[A]].combine(value, Monoid[A].empty) == value
  }


  println(Option(1) +++ Option(2) +++ None +++ Option(5))
  println(List(Option(1), Option(2), Option.empty[Int], Option(5)).fold(Monoid[Option[Int]].empty)(_ +++ _))
  println(List.empty[Option[Int]].fold(Monoid[Option[Int]].empty)(_ +++ _))
  println(Option.empty[Int] +++ Option.empty[Int])

  println("Law's are enforced for option of Ints: " +
    (associativity[Option[Int]](Option(1), Option(2), Option(3)) &&
      leftIdentity[Option[Int]](Option(5)) && rightIdentity[Option[Int]](Option(-4))))

  val tuple1 = (1, 4)
  val tuple2 = (5, 3)
  println(tuple1 +++ tuple2)

  println("Law's are enforced for tuple of Ints: " +
    (associativity[(Int, Int)]((1, 1), (-2, 2), (3, -3)) &&
      leftIdentity[(Int, Int)]((1, 4)) && rightIdentity[(Int, Int)]((1, 4))))
}

trait Monoid[A] {
  def empty: A

  def combine(a: A, b: A): A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid

  implicit def optionMonoid[A](implicit monoid: Monoid[A]): Monoid[Option[A]] = new Monoid[Option[A]] {
    override def empty: Option[A] = Option.empty[A]

    override def combine(a: Option[A], b: Option[A]): Option[A] =
      Option(monoid.combine(a.getOrElse(monoid.empty), b.getOrElse(monoid.empty)))
  }
}

object Instance {
  implicit val optionSum: Monoid[Int] = new Monoid[Int] {
    override def empty: Int = 0

    override def combine(a: Int, b: Int): Int = a + b
  }

  implicit val tupleIntSum: Monoid[(Int, Int)] = new Monoid[(Int, Int)] {
    override def empty: (Int, Int) = (0, 0)

    override def combine(a: (Int, Int), b: (Int, Int)): (Int, Int) = (a._1 + b._1, a._2 + b._2)
  }
}

object Syntax {

  implicit class MonoidOps[A](a: A) {
    def +++(b: A)(implicit monoid: Monoid[A]): A = {
      monoid.combine(a, b)
    }
  }

}