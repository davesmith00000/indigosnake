package snake.scenes

import indigo._
import indigoexts.lenses.Lens
import indigoexts.scenes._
import snake.model.{SnakeGameModel, SnakeViewModel}
import snake.init.{GameAssets, Settings}

object GameOverScene extends Scene[SnakeGameModel, SnakeViewModel, Int, Unit] {
  val name: SceneName = SceneName("game over")

  val sceneModelLens: Lens[SnakeGameModel, Int] =
    Lens(_.gameModel.score, (m, _) => m)

  val sceneViewModelLens: Lens[SnakeViewModel, Unit] =
    Lens.fixed(())

  def updateSceneModel(gameTime: GameTime, pointsScored: Int): GlobalEvent => UpdatedModel[Int] = {
    case KeyboardEvent.KeyUp(Keys.SPACE) =>
      UpdatedModel(pointsScored)
        .addGlobalEvents(SceneEvent.JumpTo(StartScene.name))

    case _ =>
      pointsScored
  }

  def updateSceneViewModel(
      gameTime: GameTime,
      pointsScored: Int,
      sceneViewModel: Unit,
      frameInputEvents: FrameInputEvents
  ): UpdatedViewModel[Unit] =
    ()

  def updateSceneView(
      gameTime: GameTime,
      pointsScored: Int,
      sceneViewModel: Unit,
      frameInputEvents: FrameInputEvents
  ): SceneUpdateFragment = {
    val horizontalCenter: Int = (Settings.viewportWidth / Settings.magnificationLevel) / 2
    val verticalMiddle: Int   = (Settings.viewportHeight / Settings.magnificationLevel) / 2

    SceneUpdateFragment.empty
      .addUiLayerNodes(
        Text("Game Over!", horizontalCenter, verticalMiddle - 20, 1, GameAssets.fontKey).alignCenter,
        Text(s"You scored: $pointsScored pts!", horizontalCenter, verticalMiddle - 5, 1, GameAssets.fontKey).alignCenter,
        Text("(hit space to restart)", horizontalCenter, 220, 1, GameAssets.fontKey).alignCenter
      )
  }
}
