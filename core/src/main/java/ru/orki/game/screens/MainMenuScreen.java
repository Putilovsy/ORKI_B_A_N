package ru.orki.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.orki.game.MainGame;

/**
 * Простейший экран главного меню с двумя кнопками:
 * 1. PLAY (Играть) -> переход в GameScreen
 * 2. EXIT (Выход) -> закрытие приложения
 * поддерживает клики мыши (с подсветкой при наведении) и быстрые клавиши (Enter/Esc/1/2).
 */
public class MainMenuScreen implements Screen {

    private final MainGame game;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final GlyphLayout layout;

    // Параметры геометрии кнопок
    private static final float BTN_WIDTH = 220f;
    private static final float BTN_HEIGHT = 52f;
    private final float btnX;
    private final float playBtnY = 440f;
    private final float exitBtnY = 350f;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.shapeRenderer = game.getShapeRenderer();
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.layout = game.getGlyphLayout();
        this.btnX = (480f - BTN_WIDTH) / 2f;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Координаты курсора мыши (в системе координат LibGDX: Y снизу вверх)
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        boolean playHovered = mouseX >= btnX && mouseX <= btnX + BTN_WIDTH &&
                              mouseY >= playBtnY && mouseY <= playBtnY + BTN_HEIGHT;

        boolean exitHovered = mouseX >= btnX && mouseX <= btnX + BTN_WIDTH &&
                              mouseY >= exitBtnY && mouseY <= exitBtnY + BTN_HEIGHT;

        // Обработка клика мыши и клавиатуры
        if ((playHovered && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen(new GameScreen(game));
            return;
        }

        if ((exitHovered && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit();
            return;
        }

        // 1. Очистка экрана (темный фон ночного Готэма)
        ScreenUtils.clear(0.08f, 0.09f, 0.12f, 1.0f);

        // 2. Фоновая дорога для сохранения атмосферы
        float roadLeft = 60f;
        float roadRight = 420f;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.12f, 0.13f, 0.17f, 1.0f);
        shapeRenderer.rect(roadLeft, 0, roadRight - roadLeft, Gdx.graphics.getHeight());

        shapeRenderer.setColor(0.22f, 0.25f, 0.32f, 1.0f);
        shapeRenderer.rect(roadLeft - 4f, 0, 4f, Gdx.graphics.getHeight());
        shapeRenderer.rect(roadRight, 0, 4f, Gdx.graphics.getHeight());

        // 3. Заливка кнопок
        // Кнопка PLAY (с подсветкой при наведении)
        if (playHovered) {
            shapeRenderer.setColor(0.0f, 0.55f, 0.8f, 1.0f);
        } else {
            shapeRenderer.setColor(0.16f, 0.19f, 0.26f, 1.0f);
        }
        shapeRenderer.rect(btnX, playBtnY, BTN_WIDTH, BTN_HEIGHT);

        // Иконка треугольника (Play)
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.triangle(
            btnX + 28f, playBtnY + 16f,
            btnX + 28f, playBtnY + BTN_HEIGHT - 16f,
            btnX + 44f, playBtnY + BTN_HEIGHT / 2f
        );

        // Кнопка EXIT (с подсветкой при наведении)
        if (exitHovered) {
            shapeRenderer.setColor(0.75f, 0.2f, 0.2f, 1.0f);
        } else {
            shapeRenderer.setColor(0.16f, 0.19f, 0.26f, 1.0f);
        }
        shapeRenderer.rect(btnX, exitBtnY, BTN_WIDTH, BTN_HEIGHT);

        // Иконка квадрата (Stop/Exit)
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(btnX + 28f, exitBtnY + 18f, 16f, 16f);

        shapeRenderer.end();

        // 4. Обводка кнопок
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(playHovered ? Color.CYAN : Color.GRAY);
        shapeRenderer.rect(btnX, playBtnY, BTN_WIDTH, BTN_HEIGHT);

        shapeRenderer.setColor(exitHovered ? Color.RED : Color.GRAY);
        shapeRenderer.rect(btnX, exitBtnY, BTN_WIDTH, BTN_HEIGHT);
        shapeRenderer.end();

        // 5. Отрисовка текста
        batch.begin();

        // Заголовок игры
        font.setColor(Color.CYAN);
        font.getData().setScale(1.5f);
        layout.setText(font, "GOTHAM: AFTER DARK");
        font.draw(batch, "GOTHAM: AFTER DARK", (480f - layout.width) / 2f, 660f);

        // Подзаголовок
        font.setColor(Color.GRAY);
        font.getData().setScale(1.0f);
        layout.setText(font, "-- SCROLL SHOOTER --");
        font.draw(batch, "-- SCROLL SHOOTER --", (480f - layout.width) / 2f, 625f);

        // Надписи на кнопках
        font.setColor(Color.WHITE);
        font.getData().setScale(1.3f);

        layout.setText(font, "PLAY");
        font.draw(batch, "PLAY", btnX + 70f, playBtnY + (BTN_HEIGHT + layout.height) / 2f);

        layout.setText(font, "EXIT");
        font.draw(batch, "EXIT", btnX + 70f, exitBtnY + (BTN_HEIGHT + layout.height) / 2f);

        // Подсказка управления в меню
        font.setColor(Color.LIGHT_GRAY);
        font.getData().setScale(0.9f);
        layout.setText(font, "Click mouse or press [ENTER] / [ESC]");
        font.draw(batch, "Click mouse or press [ENTER] / [ESC]", (480f - layout.width) / 2f, 240f);

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
