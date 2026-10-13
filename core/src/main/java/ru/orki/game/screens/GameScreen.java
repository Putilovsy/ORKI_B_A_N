package ru.orki.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.orki.game.MainGame;

/**
 * Экран геймплея (прототип).
 * Содержит движение квадрата в ограниченном пространстве дороги.
 */
public class GameScreen implements Screen {

    private final MainGame game;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final GlyphLayout layout;

    // Границы дорожного полотна (ограниченное пространство)
    private static final float ROAD_LEFT = 60f;
    private static final float ROAD_RIGHT = 420f;

    // Параметры квадрата игрока
    private static final float SQUARE_SIZE = 40f;
    private static final float MOVE_SPEED = 300f; // пикселей в секунду

    private float squareX;
    private float squareY;

    public GameScreen(MainGame game) {
        this.game = game;
        this.shapeRenderer = game.getShapeRenderer();
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.layout = game.getGlyphLayout();

        // Начальная позиция квадрата — по центру дороги в нижней части экрана
        this.squareX = (ROAD_LEFT + ROAD_RIGHT - SQUARE_SIZE) / 2f;
        this.squareY = 80f;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Выход из теста геймплея обратно в главное меню по нажатию ESC
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        // 1. Управление (клавиши A/D и Стрелки влево/вправо)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            squareX -= MOVE_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            squareX += MOVE_SPEED * delta;
        }

        // 2. Ограничение перемещения в пределах дорожного полотна
        float minX = ROAD_LEFT;
        float maxX = ROAD_RIGHT - SQUARE_SIZE;
        squareX = MathUtils.clamp(squareX, minX, maxX);

        // 3. Очистка экрана (темный фон)
        ScreenUtils.clear(0.08f, 0.09f, 0.12f, 1.0f);

        // 4. Отрисовка дороги и машины (ShapeRenderer)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Асфальт (дорожное полотно)
        shapeRenderer.setColor(0.15f, 0.17f, 0.22f, 1.0f);
        shapeRenderer.rect(ROAD_LEFT, 0, ROAD_RIGHT - ROAD_LEFT, Gdx.graphics.getHeight());

        // Левая граница дороги
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(ROAD_LEFT - 6f, 0, 6f, Gdx.graphics.getHeight());

        // Правая граница дороги
        shapeRenderer.rect(ROAD_RIGHT, 0, 6f, Gdx.graphics.getHeight());

        // Разделительная полоса
        shapeRenderer.setColor(0.35f, 0.38f, 0.45f, 1.0f);
        float roadCenterX = (ROAD_LEFT + ROAD_RIGHT) / 2f - 2f;
        shapeRenderer.rect(roadCenterX, 0, 4f, Gdx.graphics.getHeight());

        // Квадрат игрока (яркий неоновый цвет)
        shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 0.0f);
        shapeRenderer.rect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);

        shapeRenderer.end();

        // Контур квадрата для четкости
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
        shapeRenderer.end();

        // 5. Информационная подсказка вверху экрана
        batch.begin();
        font.setColor(Color.LIGHT_GRAY);
        font.getData().setScale(0.95f);
        layout.setText(font, "[ESC] Back to Menu | [A]/[D] Move");
        font.draw(batch, "[ESC] Back to Menu | [A]/[D] Move", (480f - layout.width) / 2f, 770f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
