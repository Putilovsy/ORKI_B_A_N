package ru.orki.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.orki.game.screens.MainMenuScreen;

/**
 * Главный класс приложения Gotham: After Dark.
 * Наследует com.badlogic.gdx.Game для поддержки переключения экранов (Screen).
 * Хранит общие рендереры (ShapeRenderer, SpriteBatch, BitmapFont).
 */
public class MainGame extends Game {

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont(); // Встроенный шрифт LibGDX Arial
        glyphLayout = new GlyphLayout();

        // Запуск с экрана главного меню
        setScreen(new MainMenuScreen(this));
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }
}
