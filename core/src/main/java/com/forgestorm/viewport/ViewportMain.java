package com.forgestorm.viewport;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class ViewportMain extends Game {

    @Override
    public void create() {
        // Using screens to try to eliminate them being the cause of the issue...
        setScreen(new TestScreen());
    }

    static class TestScreen implements Screen {

        private Viewport backgroundViewport;
        private SpriteBatch spriteBatch;
        private Texture texture;

        private GdxGltfQuickStart gdxGltfQuickStart;
        private Viewport worldViewport;

        @Override
        public void show() {
            // Background setup
            backgroundViewport = new ScreenViewport();
            backgroundViewport.apply();
            spriteBatch = new SpriteBatch();
            texture = new Texture(Gdx.files.internal("beach.jpg"));

            // World setup
            gdxGltfQuickStart = new GdxGltfQuickStart();
            gdxGltfQuickStart.create();

            // Setting viewport bounds and world size here to test if the change works
            int x = 500;
            int y = 500;
            int width = 100;
            int height = 100;
            worldViewport = new ScreenViewport(gdxGltfQuickStart.getCamera());
            worldViewport.setScreenBounds(x, y, width, height);
            worldViewport.setWorldSize(width, height);
        }

        @Override
        public void render(float v) {
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

            // Draw background
            // TODO: This is not working as expected
            backgroundViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            backgroundViewport.getCamera().position.set(0, 0, 0);
            backgroundViewport.apply();
            spriteBatch.setProjectionMatrix(backgroundViewport.getCamera().combined);
            spriteBatch.begin();
            spriteBatch.draw(texture, 0, 0);
            spriteBatch.end();

            // Update the viewport bounds AND world size to set the screen XY and width/height
            int x = 100;
            int y = 100;
            int width = 800;
            int height = 200;
            worldViewport.setScreenBounds(x, y, width, height);
            worldViewport.setWorldSize(width, height);
            worldViewport.apply(true);

            // Draw world
            gdxGltfQuickStart.render();
            System.out.println("Camera Viewport: " + gdxGltfQuickStart.getCamera().viewportWidth + "x" + gdxGltfQuickStart.getCamera().viewportHeight);
        }

        @Override
        public void resize(int i, int i1) {

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
            spriteBatch.dispose();
            texture.dispose();
            gdxGltfQuickStart.dispose();
        }
    }
}
