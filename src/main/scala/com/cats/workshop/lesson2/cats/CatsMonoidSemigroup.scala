package com.cats.workshop.lesson2.cats

import cats.Monoid
import cats.Semigroup
import cats.instances.all._ // implicits of basic types
import cats.syntax.semigroup._ // for |+|

object CatsMonoidSemigroup extends App {
  println(Semigroup[Int].combine(1, 5))
  println(Monoid[Int].combine(1, 5))
  println(1 |+| 3 |+| Monoid[Int].empty)
  println(1 |+| 3)

  println(Option(4) |+| Option(6) |+| None)

  val map1 = Map("ivan" -> List(10, 4, 3), "jerry" -> List(2), "tom" -> List.empty[Int])
  val map2 = Map("ivan" -> List(43, 22), "tom" -> List(3, 5, 6), "garry" -> List(5))

  println(map1 |+| map2)

}
