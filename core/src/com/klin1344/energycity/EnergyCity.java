package com.klin1344.energycity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyCity extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager manager;
    private float density;
    public void create() {
    	density = Gdx.graphics.getDensity();
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
       // font = new BitmapFont();
        //font.setScale(3f, 3f);
        font = new BitmapFont(Gdx.files.internal("Roboto.fnt"));
        font.setScale(0.4f * density, 0.4f * density);
        this.setScreen(new MainMenu(this, 4));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        manager.dispose();
    }

}
