package snake.model

import indigo._
import snake.model.snakemodel.{Snake, SnakeDirection}

sealed trait ControlScheme {
  def instructSnake(keyboardEvent: KeyboardEvent, snake: Snake, currentDirection: SnakeDirection): Snake =
    (this, keyboardEvent) match {
      case (ControlScheme.Turning(left, _), KeyboardEvent.KeyDown(code)) if code === left =>
        currentDirection.makeLegalTurn(snake.turnLeft).getOrElse(snake)

      case (ControlScheme.Turning(_, right), KeyboardEvent.KeyDown(code)) if code === right =>
        currentDirection.makeLegalTurn(snake.turnRight).getOrElse(snake)

      case (ControlScheme.Directed(up, _, _, _), KeyboardEvent.KeyDown(code)) if code === up =>
        currentDirection.makeLegalTurn(snake.goUp).getOrElse(snake)

      case (ControlScheme.Directed(_, down, _, _), KeyboardEvent.KeyDown(code)) if code === down =>
        currentDirection.makeLegalTurn(snake.goDown).getOrElse(snake)

      case (ControlScheme.Directed(_, _, left, _), KeyboardEvent.KeyDown(code)) if code === left =>
        currentDirection.makeLegalTurn(snake.goLeft).getOrElse(snake)

      case (ControlScheme.Directed(_, _, _, right), KeyboardEvent.KeyDown(code)) if code === right =>
        currentDirection.makeLegalTurn(snake.goRight).getOrElse(snake)

      case _ =>
        snake
    }

  def swap: ControlScheme =
    this match {
      case ControlScheme.Turning(_, _) =>
        ControlScheme.directedKeys

      case ControlScheme.Directed(_, _, _, _) =>
        ControlScheme.turningKeys
    }

}
object ControlScheme {
  val turningKeys: Turning   = Turning(Keys.LEFT_ARROW, Keys.RIGHT_ARROW)
  val directedKeys: Directed = Directed(Keys.UP_ARROW, Keys.DOWN_ARROW, Keys.LEFT_ARROW, Keys.RIGHT_ARROW)

  case class Turning(left: KeyCode, right: KeyCode)                              extends ControlScheme
  case class Directed(up: KeyCode, down: KeyCode, left: KeyCode, right: KeyCode) extends ControlScheme
}
