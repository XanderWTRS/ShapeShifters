package com.Xander.shapeshifters;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameScreen implements Screen {
    private ShapeRenderer shapeRenderer;
    private Player player;

    public GameScreen(MainGame game) {
        // Initialize ShapeRenderer and Player here
        shapeRenderer = new ShapeRenderer();
        player = new Player(100, 100, 50, 50);  // Set initial player position and size
    }

    @Override
    public void show() {
        // Called when the screen is displayed
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the player
        player.update(Gdx.graphics.getDeltaTime());

        // Render the player
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shapeRenderer);  // This calls the Player's render method
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize handling if needed
    }

    @Override
    public void hide() {
        // Dispose resources when hiding the screen
        shapeRenderer.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        // Dispose any other resources
    }
}
