package common

import DSL._

import scala.util.Random

object CommonDSL {

  def newWall(): Wall = {
    val x = Random.nextInt(20)
    val y = Random.nextInt(20)
    val spotSize = Random.nextInt(20)
    Wall(Point(x, y), spotSize)
  }

  def newWall(p: Point, spotSize: Int): Wall = {
    Wall(p, spotSize)
  }

  def newEmpty(): Empty = {
    val x = Random.nextInt(20)
    val y = Random.nextInt(20)
    val spotSize = Random.nextInt(20)
    Empty(Point(x, y), spotSize)
  }

  def newEmpty(p: Point, spotSize: Int): Empty = {
    Empty(p, spotSize)
  }

  def newApple(): Apple = {
    val x = Random.nextInt(20)
    val y = Random.nextInt(20)
    val spotSize = Random.nextInt(20)
    Apple(Point(x, y), spotSize)
  }

  def newApple(p: Point, spotSize: Int): Apple = {
    Apple(p, spotSize)
  }

  def newSnake(): Snake = {
    val x = Random.nextInt(20)
    val y = Random.nextInt(20)
    val spotSize = Random.nextInt(20)
    Snake(Point(x, y), spotSize)
  }

  def newSnake(p: Point, spotSize: Int): Snake = {
    Snake(p, spotSize)
  }

  def newWallList(): List[Wall] = {
    val size = 5 + Random.nextInt(10)
    List.fill(size)(newWall())
  }

  def newEmptyList(): List[Empty] = {
    val size = 5 + Random.nextInt(10)
    List.fill(size)(newEmpty())
  }

  def newAppleList(): List[Apple] = {
    val size = 5 + Random.nextInt(10)
    List.fill(size)(newApple())
  }

  def newSnakeList(): List[Snake] = {
    val size = 5 + Random.nextInt(10)
    List.fill(size)(newSnake())
  }

}