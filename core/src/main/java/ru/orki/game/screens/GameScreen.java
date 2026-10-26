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

    private static final float HP_BAR_X = 20f;
    private static final float HP_BAR_Y = 735f;
    private static final float HP_BAR_WIDTH = 140f;
    private static final float HP_BAR_HEIGHT = 18f;

    private static final float SKILL_SIZE = 48f;
    private static final float SKILL_Y = 20f;
    private static final float SKILL1_X = 182f;
    private static final float SKILL2_X = 250f;

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

        shapeRenderer.setColor(0.25f, 0.1f, 0.1f, 1.0f);
        shapeRenderer.rect(HP_BAR_X, HP_BAR_Y, HP_BAR_WIDTH, HP_BAR_HEIGHT);

        shapeRenderer.setColor(0.2f, 0.75f, 0.3f, 1.0f);
        shapeRenderer.rect(HP_BAR_X, HP_BAR_Y, HP_BAR_WIDTH, HP_BAR_HEIGHT);

        shapeRenderer.setColor(0.12f, 0.14f, 0.2f, 1.0f);
        shapeRenderer.rect(SKILL1_X, SKILL_Y, SKILL_SIZE, SKILL_SIZE);
        shapeRenderer.rect(SKILL2_X, SKILL_Y, SKILL_SIZE, SKILL_SIZE);

        shapeRenderer.end();

        // Контуры
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);

        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(HP_BAR_X, HP_BAR_Y, HP_BAR_WIDTH, HP_BAR_HEIGHT);

        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(SKILL1_X, SKILL_Y, SKILL_SIZE, SKILL_SIZE);

        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(SKILL2_X, SKILL_Y, SKILL_SIZE, SKILL_SIZE);

        shapeRenderer.end();

        // 5. Информационная подсказка вверху экрана
        batch.begin();

        font.setColor(Color.GRAY);
        font.getData().setScale(0.85f);
        String hintText = "[ESC] Menu | [A]/[D] Move";
        layout.setText(font, hintText);
        font.draw(batch, hintText, (480f - layout.width) / 2f, 790f);

        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);
        font.draw(batch, "HP: 100 / 100", HP_BAR_X, HP_BAR_Y + HP_BAR_HEIGHT + 18f);

        font.setColor(Color.YELLOW);
        font.getData().setScale(1.1f);
        String scoreText = "000000";
        layout.setText(font, scoreText);
        font.draw(batch, scoreText, 460f - layout.width, 765f);

        font.getData().setScale(0.9f);

        font.setColor(Color.CYAN);
        layout.setText(font, "EMP");
        font.draw(batch, "EMP", SKILL1_X + (SKILL_SIZE - layout.width) / 2f, SKILL_Y + 38f);

        font.setColor(Color.ORANGE);
        layout.setText(font, "RAM");
        font.draw(batch, "RAM", SKILL2_X + (SKILL_SIZE - layout.width) / 2f, SKILL_Y + 38f);

        font.setColor(Color.LIGHT_GRAY);
        font.getData().setScale(0.75f);
        layout.setText(font, "[Q]");
        font.draw(batch, "[Q]", SKILL1_X + (SKILL_SIZE - layout.width) / 2f, SKILL_Y + 18f);

        layout.setText(font, "[E]");
        font.draw(batch, "[E]", SKILL2_X + (SKILL_SIZE - layout.width) / 2f, SKILL_Y + 18f);

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
