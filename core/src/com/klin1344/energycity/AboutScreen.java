package com.klin1344.energycity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class AboutScreen implements Screen {
	final EnergyCity game;
	private float width;
	private float height;
	private OrthographicCamera cam;
	private Texture background;
	//private float density;
	
	public AboutScreen(final EnergyCity gam) {
		this.game = gam;
		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false, width, height);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		background = new Texture(Gdx.files.internal("instructions_background.png"));
		//density = Gdx.graphics.getDensity();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		game.batch.begin();
		game.batch.draw(background, 0, 0, width, height);
		game.batch.end();		
		
		if (Gdx.input.justTouched()) {
			dispose();
			game.setScreen(new MainMenu(game, 4));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		background.dispose();
	}

}
