package io.github.lost_beyond


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.graphics.use
import ktx.collections.toGdxArray

// Clase principal del juego
class GameMain : KtxGame<KtxScreen>() {
    lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        addScreen(FirstScreen(this)) // Inicia en FirstScreen
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        batch.dispose()
        super.dispose()
    }
}

// Pantalla principal con animación
class FirstScreen(private val game: GameMain) : KtxScreen {
    private val texture = Texture(Gdx.files.internal("Basepersonaje.png"))

    private val frameWidth = 240
    private val frameHeight = 240
    private val frameCols = 4
    private val frameRows = 1

    private val animation: Animation<TextureRegion>
    private var stateTime = 0f

    init {
        val tempFrames = TextureRegion.split(texture, frameWidth, frameHeight)
        val animationFrames = mutableListOf<TextureRegion>()

        for (i in 0 until frameCols) {
            animationFrames.add(tempFrames[0][i])
        }

        animation = Animation(0.2f, animationFrames.toGdxArray())
    }

    override fun render(delta: Float) {
        stateTime += delta
        val currentFrame = animation.getKeyFrame(stateTime, true)

        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        game.batch.use {
            it.draw(currentFrame, 100f, 160f, 240f * 2, 240f * 2) // Doble tamaño

        }
    }

    override fun dispose() {
        texture.dispose()
    }
}

