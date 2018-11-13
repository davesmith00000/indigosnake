package snake.init

import indigo._
import indigoexts.grid._

object SnakeStartupData {

  def initialise(
      viewport: GameViewport,
      gridSize: GridSize
  ): Either[StartupErrors, SnakeStartupData] =
    Right(createStartupData(viewport, gridSize, gridSize.gridSquareSize))

  def createStartupData(viewport: GameViewport, gridSize: GridSize, blockSize: Int): SnakeStartupData =
    SnakeStartupData(
      viewport = viewport,
      gridSize = gridSize,
      staticAssets = StaticAssets(
        apple = Graphic(0, 0, blockSize, blockSize, 2, GameAssets.snakeTexture)
          .withCrop(blockSize, 0, blockSize, blockSize),
        snake = Graphic(0, 0, blockSize, blockSize, 2, GameAssets.snakeTexture),
        wall = Graphic(0, 0, blockSize, blockSize, 2, GameAssets.snakeTexture)
          .withCrop(blockSize * 2, 0, blockSize, blockSize)
      )
    )

}

case class SnakeStartupData(viewport: GameViewport, gridSize: GridSize, staticAssets: StaticAssets)

case class StaticAssets(apple: Graphic, snake: Graphic, wall: Graphic)
