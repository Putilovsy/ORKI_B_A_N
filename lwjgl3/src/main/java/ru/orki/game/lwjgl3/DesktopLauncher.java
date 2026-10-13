package ru.orki.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.orki.game.MainGame;

/**
 * Точка входа для запуска игры.
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Gotham: After Dark - Prototype");
        config.setWindowedMode(480, 800);
        config.useVsync(true);
        config.setForegroundFPS(30);
        
        new Lwjgl3Application(new MainGame(), config);
    }
}
