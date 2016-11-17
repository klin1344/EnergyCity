package com.klin1344.energycity;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameClassic implements Screen {
	final EnergyCity game;

	private Texture battery;
	private Texture cloud;
	private Texture lightning;
	private Texture buildingsDarkRight;
	private Texture buildingsDarkLeft;
	private Texture buildingsLightRight;
	private Texture buildingsLightLeft;
	private Texture batteryFiller;
	private Texture rainDrop;
	private Texture buildingsLightRight2;
	private Texture buildingsLightRight4;
	private Texture buildingsLightRight5;
	private Texture buildingsLightLeft1;
	private Texture buildingsLightLeft3;
	private Texture nightBackground;
	private Texture laserLightRight;
	private Texture laserLightLeft;
	private Texture moon;
	private Texture blimp;
	private Texture restart;
	private Texture menu;
	private Texture star;
	private Texture confetti;
	private OrthographicCamera cam;

	private Rectangle batteryRectangle;
	private Rectangle buildingsRightRectangle;
	private Rectangle buildingsLeftRectangle;
	private Rectangle fillerRectangle;
	private Rectangle moonRectangle;
	private Rectangle laserLightRectangle;
	private Rectangle blimpRectangle;
	private Rectangle restartRectangle;
	private Rectangle menuRectangle;

	private int height;
	private int width;
	private int random;
	private int  lightningCounter = 0;
	private int batteryCounter = 0;
	private int gameType;

	private Array<Rectangle> cloudArray;
	private Array<Rectangle> lightningArray;
	private Array<Rectangle> rainDropsArray;
	private Array<Rectangle> starArray;
	private Array<Rectangle> confettiArray;

	private long lastConfettiTime;
	private long lastCloudTime;
	private long lastDropTime;
	private long lastBuildingTime;
	private long highScore;
	private long scoreTimer = 0;
	private long initialScoreTime;
	private float density;

	private boolean isTouched = false;
	private boolean blimpBool = true;
	private boolean cricketBool = true;
	private boolean scoreSoundBool = true;

	private Music rainMusic;
	private Music blimpSound;
	private Sound thunderSound;
	private Sound outageSound;
	private Sound cricketSound;
	private Sound chargeSound;
	private Sound scoreSound;
	private Sound dischargeSound;

	private Preferences scorePrefs;

	public GameClassic(final EnergyCity gam, final int gameInt) {
		this.game = gam;
		gameType = gameInt;

		density = Gdx.graphics.getDensity();
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false, width, height);

		switch (gameType) {
		case MainMenu.GAME_EASY:
			scorePrefs = Gdx.app.getPreferences("EASY_PREFERENCES");
			blimp = game.manager.get("blimp_easy.png", Texture.class);
			break;
		case MainMenu.GAME_MEDIUM:
			scorePrefs = Gdx.app.getPreferences("MEDIUM_PREFERENCES");
			blimp = game.manager.get("blimp_medium.png", Texture.class);
			star = game.manager.get("star.png", Texture.class);
			starArray = new Array<Rectangle>();
			for (int i = 0; i < 75; i++) {
				spawnStar();
			}
			break; 
		case MainMenu.GAME_HARD:
			scorePrefs = Gdx.app.getPreferences("HARD_PREFERENCES");
			blimp = game.manager.get("blimp_hard.png", Texture.class);
			star = game.manager.get("star.png", Texture.class);
			starArray = new Array<Rectangle>();
			for (int i = 0; i < 100; i++) {
				spawnStar();
			}
			confetti = game.manager.get("confetti.png", Texture.class);
			confettiArray = new Array<Rectangle>();
			break; 
		case MainMenu.GAME_INSANE:
			scorePrefs = Gdx.app.getPreferences("INSANE_PREFERENCES");
			blimp = game.manager.get("blimp_insane.png", Texture.class);
			star = game.manager.get("star.png", Texture.class);
			starArray = new Array<Rectangle>();
			for (int i = 0; i < 125; i++) {
				spawnStar();
			}
			confetti = game.manager.get("confetti.png", Texture.class);
			confettiArray = new Array<Rectangle>();
			break; 
		default:
			scorePrefs = Gdx.app.getPreferences("EASY_PREFERENCES");
			blimp = game.manager.get("blimp_easy.png", Texture.class);
			break;
		}
		highScore = scorePrefs.getLong("HIGHSCORE");

		nightBackground = game.manager.get("night_background.jpg", Texture.class);
		battery = game.manager.get("battery.png", Texture.class);
		buildingsDarkLeft = game.manager.get("buildings_dark_left.png", Texture.class);
		buildingsDarkRight = game.manager.get("buildings_dark_right.png", Texture.class);
		buildingsLightLeft = game.manager.get("buildings_light_left.png", Texture.class);
		buildingsLightRight = game.manager.get("buildings_light_right.png", Texture.class);
		buildingsLightLeft1 = game.manager.get("buildings_light_left_1.png", Texture.class);
		buildingsLightLeft3 = game.manager.get("buildings_light_left_3.png", Texture.class);
		buildingsLightRight2 = game.manager.get("buildings_light_right_2.png", Texture.class);
		buildingsLightRight4 = game.manager.get("buildings_light_right_4.png", Texture.class);
		buildingsLightRight5 = game.manager.get("buildings_light_right_5.png", Texture.class);
		lightning = game.manager.get("lightning.png", Texture.class);
		batteryFiller = game.manager.get("battery_filler.png", Texture.class);
		rainDrop = game.manager.get("raindrop.png", Texture.class);
		rainMusic = game.manager.get("rain.mp3", Music.class);
		thunderSound = game.manager.get("thunder.mp3", Sound.class);
		outageSound =  game.manager.get("poweroutage.mp3", Sound.class);
		moon = game.manager.get("moon.png", Texture.class);
		cloud = game.manager.get("cloud.png", Texture.class);
		laserLightRight = game.manager.get("laser_light_right.png", Texture.class);
		laserLightLeft = game.manager.get("laser_light_left.png", Texture.class);
		restart = game.manager.get("ic_replay.png", Texture.class);
		menu = game.manager.get("ic_menu.png", Texture.class);
		cricketSound = game.manager.get("cricket.mp3", Sound.class);
		blimpSound = game.manager.get("blimp.mp3", Music.class);
		chargeSound = game.manager.get("batteryCharge.mp3", Sound.class);
		dischargeSound = game.manager.get("powerdown.mp3", Sound.class);



		batteryRectangle = new Rectangle();
		batteryRectangle.width = width/4;
		batteryRectangle.height = batteryRectangle.width/1.2f;
		batteryRectangle.x = width/2 - batteryRectangle.width/2;
		batteryRectangle.y = 0;

		buildingsLeftRectangle = new Rectangle();
		buildingsLeftRectangle.width = (width - (width - batteryRectangle.x)) - (20 * density);
		buildingsLeftRectangle.height = height/1.7f;
		buildingsLeftRectangle.x = 0;
		buildingsLeftRectangle.y = 0;

		buildingsRightRectangle = new Rectangle();
		buildingsRightRectangle.width = buildingsLeftRectangle.width;
		buildingsRightRectangle.height = buildingsLeftRectangle.height;
		buildingsRightRectangle.x = width - buildingsRightRectangle.width;
		buildingsRightRectangle.y = 0;

		fillerRectangle = new Rectangle();
		fillerRectangle.width = batteryRectangle.width;
		fillerRectangle.x = batteryRectangle.x;
		fillerRectangle.y = batteryRectangle.height/100;

		moonRectangle = new Rectangle();
		moonRectangle.width = width/6;
		moonRectangle.height = height/4;
		moonRectangle.x = buildingsRightRectangle.x + (35 * density);
		moonRectangle.y = 0;

		laserLightRectangle = new Rectangle();
		laserLightRectangle.width = width/5;
		laserLightRectangle.y = height/6;

		restartRectangle = new Rectangle();
		restartRectangle.width = width/25;
		restartRectangle.height = restartRectangle.width;
		restartRectangle.x = width - restartRectangle.width;
		restartRectangle.y = height - restartRectangle.height;

		menuRectangle = new Rectangle();
		menuRectangle.width = restartRectangle.width/1.1f;
		menuRectangle.height = menuRectangle.width;
		menuRectangle.x = width - (restartRectangle.width + menuRectangle.width + (15 * density));
		menuRectangle.y = restartRectangle.y;

		blimpRectangle = new Rectangle();
		blimpRectangle.width = width/1.2f;
		blimpRectangle.height = height/1.2f;
		blimpRectangle.x = width;
		blimpRectangle.y = height/2 - blimpRectangle.height/2;

		rainDropsArray = new Array<Rectangle>();
		cloudArray = new Array<Rectangle>();
		lightningArray = new Array<Rectangle>();

		rainMusic.setVolume(0.8f);
		rainMusic.setLooping(true);
		rainMusic.play();

		initialScoreTime = TimeUtils.nanoTime();
	}

	private void spawnConfetti() {
		Rectangle confettiRectangle = new Rectangle();
		confettiRectangle.width = width/4;
		confettiRectangle.height = confettiRectangle.width;
		confettiRectangle.x = MathUtils.random(0, width - confettiRectangle.width);
		confettiRectangle.y = height;
		confettiArray.add(confettiRectangle);
		lastConfettiTime = TimeUtils.nanoTime();		
	}

	private void spawnStar() {
		Rectangle starRectangle = new Rectangle();
		starRectangle.width = MathUtils.random((width/120), (width/50));
		starRectangle.height = starRectangle.width;	
		starRectangle.x = MathUtils.random(0, (width - starRectangle.width));
		starRectangle.y = MathUtils.random((height/1.9f), (height - starRectangle.height));
		starArray.add(starRectangle);
	}

	private void spawnCloud() {
		Rectangle cloudRectangle = new Rectangle();
		cloudRectangle.width = width/4;
		cloudRectangle.height = width/7;
		cloudRectangle.x = width - cloudRectangle.width;
		cloudRectangle.y = height - cloudRectangle.height;
		cloudArray.add(cloudRectangle);
		lastCloudTime = TimeUtils.nanoTime();
		switch (gameType) {
		case  MainMenu.GAME_EASY: 
			random = MathUtils.random(0, 3);
			break;
		case  MainMenu.GAME_MEDIUM:
			random = MathUtils.random(0, 5);
			break;
		case MainMenu.GAME_HARD:
			random = MathUtils.random(0, 6);
			break;
		case MainMenu.GAME_INSANE:
			random = MathUtils.random(0, 6);
			break;
		default:
			random = MathUtils.random(0, 4);
			break;
		}
	}

	private void spawnLightning() {
		Rectangle lightningRectangle = new Rectangle();
		lightningRectangle.height = width/6.5f;
		lightningRectangle.width = lightningRectangle.height/3;
		lightningRectangle.x = width - width/4/2;
		lightningRectangle.y = height - (lightningRectangle.height);
		lightningArray.add(lightningRectangle);
	}

	private void spawnRainDrop() {
		Rectangle rainDropRectangle = new Rectangle();
		rainDropRectangle.width = width/125;
		rainDropRectangle.height = height/35;
		rainDropRectangle.x = MathUtils.random(0, width - rainDropRectangle.width/2);
		rainDropRectangle.y = height - (10 * density);
		rainDropsArray.add(rainDropRectangle);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();

		game.batch.draw(nightBackground, 0, 0, width, height);

		for(Rectangle rainDropRectangle: rainDropsArray) {
			game.batch.draw(rainDrop, rainDropRectangle.x, rainDropRectangle.y, rainDropRectangle.width, rainDropRectangle.height);
		}

		switch (batteryCounter) {
		case 0:
			break;
		case 1:
			game.batch.draw(batteryFiller, fillerRectangle.x, fillerRectangle.y, fillerRectangle.width, batteryRectangle.height/6);
			break;
		case 2:
			game.batch.draw(batteryFiller, fillerRectangle.x, fillerRectangle.y, fillerRectangle.width, batteryRectangle.height/3);
			break;
		case 3:
			game.batch.draw(batteryFiller, fillerRectangle.x, fillerRectangle.y, fillerRectangle.width, batteryRectangle.height/2);
			break;
		case 4:
			game.batch.draw(batteryFiller, fillerRectangle.x, fillerRectangle.y, fillerRectangle.width, batteryRectangle.height/1.5f);
			break;
		case 5:
			game.batch.draw(batteryFiller, fillerRectangle.x, fillerRectangle.y, fillerRectangle.width, batteryRectangle.height/1.25f);
			break;
		default:
			break;
		}

		game.batch.draw(battery, batteryRectangle.x, batteryRectangle.y, batteryRectangle.width, batteryRectangle.height);

		for(Rectangle lightningRectangle: lightningArray) {
			game.batch.draw(lightning, lightningRectangle.x, lightningRectangle.y, lightningRectangle.width, lightningRectangle.height);
		}

		for(Rectangle cloudRectangle: cloudArray) {
			game.batch.draw(cloud, cloudRectangle.x, cloudRectangle.y, cloudRectangle.width, cloudRectangle.height);
		}

		switch (lightningCounter) {
		case 0: 
			game.batch.draw(buildingsDarkRight, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsDarkLeft, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
			break;
		case 1:
			game.batch.draw(buildingsDarkRight, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsLightLeft1, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
			break;
		case 2:
			game.batch.draw(buildingsLightRight2, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsLightLeft1, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
			break;
		case 3:
			game.batch.draw(buildingsLightRight2, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsLightLeft3, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
			break;
		case 4:
			game.batch.draw(buildingsLightRight4, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsLightLeft3, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
			break;
		case 5:
			game.batch.draw(buildingsLightRight5, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsLightLeft3, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
			break;
		case 6:
			rainMusic.stop();
			if ((gameType == MainMenu.GAME_MEDIUM) || (gameType == MainMenu.GAME_HARD) || (gameType == MainMenu.GAME_INSANE)) {
				for(Rectangle starRectangle: starArray) {
					game.batch.draw(star, starRectangle.x, starRectangle.y, starRectangle.width, starRectangle.height);
				}
			}

			game.batch.draw(moon, moonRectangle.x, moonRectangle.y, moonRectangle.width, moonRectangle.height);
			game.batch.draw(buildingsLightRight, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
			game.batch.draw(buildingsLightLeft, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);

			/*if (TimeUtils.nanoTime() - lastDropTime > 100000) {
				lightningCounter++;
			}*/
			if (moonRectangle.y < (height - moonRectangle.height)) {
				moonRectangle.y += 60 * density * Gdx.graphics.getDeltaTime();
			}
			else {
				cricketSound.stop();
				playBlimpMusic();
				game.batch.draw(laserLightRight, 0, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightRight, buildingsLeftRectangle.width/2, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightRight, buildingsLeftRectangle.width/1.1f, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightRight, buildingsRightRectangle.x, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightRight, buildingsRightRectangle.x + buildingsRightRectangle.width/2, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightLeft, buildingsRightRectangle.x + buildingsRightRectangle.width/2, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightLeft, buildingsRightRectangle.x, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightLeft, buildingsLeftRectangle.width/2.4f, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightLeft, width/2, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(laserLightLeft, 0, laserLightRectangle.y, laserLightRectangle.width, height);
				game.batch.draw(buildingsLightRight, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
				game.batch.draw(buildingsLightLeft, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
				if (TimeUtils.nanoTime() - lastBuildingTime > 100000000) {
					game.batch.draw(buildingsDarkLeft, buildingsLeftRectangle.x, buildingsLeftRectangle.y, buildingsLeftRectangle.width, buildingsLeftRectangle.height);
					game.batch.draw(buildingsDarkRight, buildingsRightRectangle.x, buildingsRightRectangle.y, buildingsRightRectangle.width, buildingsRightRectangle.height);
					lastBuildingTime = TimeUtils.nanoTime();
				}
				if ((gameType == MainMenu.GAME_HARD) || (gameType == MainMenu.GAME_INSANE)) {
					for(Rectangle confettiRectangle: confettiArray) {
						game.batch.draw(confetti, confettiRectangle.x, confettiRectangle.y, confettiRectangle.width, confettiRectangle.height);
					}
					if(TimeUtils.nanoTime() - lastConfettiTime > 400000000) {
						spawnConfetti();
					}
				}

				game.batch.draw(blimp, blimpRectangle.x, blimpRectangle.y, blimpRectangle.width, blimpRectangle.height);

				if (blimpRectangle.x > (width/2 - blimpRectangle.width/2)) {
					blimpRectangle.x -= 80 * density * Gdx.graphics.getDeltaTime();
				}
				else {
					game.font.draw(game.batch, scoreTimer + " sec", width/2.4f, height/2.6f);
					game.font.draw(game.batch, highScore + " sec", width/2.4f, height/3.1f);
					if (!blimpSound.isPlaying()) {
						playScoreSound();
					}
				}
			}
			break;
		}
		game.batch.draw(restart, restartRectangle.x, restartRectangle.y, restartRectangle.width, restartRectangle.height);
		game.batch.draw(menu, menuRectangle.x, menuRectangle.y, menuRectangle.width, menuRectangle.height);
		game.batch.end();

		if (lightningCounter < 6) {
			switch (gameType) {
			case MainMenu.GAME_EASY: 
				if (TimeUtils.nanoTime() - lastDropTime > 600) {
					spawnRainDrop();
				}
				if(TimeUtils.nanoTime() - lastCloudTime > 999999999) {
					spawnCloud();
					spawnLightning();
				}
				break;
			case MainMenu.GAME_MEDIUM:
				if (TimeUtils.nanoTime() - lastDropTime > 50) {
					spawnRainDrop();
				}
				if(TimeUtils.nanoTime() - lastCloudTime > 899999999) {
					spawnCloud();
					spawnLightning();
				}
				break;
			case MainMenu.GAME_HARD:
				if (TimeUtils.nanoTime() - lastDropTime > 1) {
					spawnRainDrop();
				}
				if(TimeUtils.nanoTime() - lastCloudTime > 799999999) {
					spawnCloud();
					spawnLightning();
				}
				break;
			case MainMenu.GAME_INSANE:
				if (TimeUtils.nanoTime() - lastDropTime > 1) {
					spawnRainDrop();
				}
				if(TimeUtils.nanoTime() - lastCloudTime > 699999999) {
					spawnCloud();
					spawnLightning();
				}
				break;
			default:
				if (TimeUtils.nanoTime() - lastDropTime > 600) {
					spawnRainDrop();
				}
				if(TimeUtils.nanoTime() - lastCloudTime > 999999999) {
					spawnCloud();
					spawnLightning();
				}
				break;
			}
		}

		Iterator<Rectangle> iter2 = rainDropsArray.iterator();
		while(iter2.hasNext()) {
			Rectangle rainDropRectangle = iter2.next();
			rainDropRectangle.y -= 600 * density * Gdx.graphics.getDeltaTime();
			if ((rainDropRectangle.y + rainDropRectangle.height <= 0)) {
				iter2.remove();
			}
		}

		if ((gameType == MainMenu.GAME_HARD) || (gameType == MainMenu.GAME_INSANE)) {
			Iterator<Rectangle> iter3 = confettiArray.iterator();
			while(iter3.hasNext()) {
				Rectangle confettiRectangle = iter3.next();
				confettiRectangle.y -= 150 * density * Gdx.graphics.getDeltaTime();
				if ((confettiRectangle.y + confettiRectangle.height <= 0)) {
					iter3.remove();
				}
			}
		}

		Iterator<Rectangle> iter = cloudArray.iterator();
		while(iter.hasNext()) {
			Rectangle cloudRectangle = iter.next();
			switch (random) {
			case 0:
				cloudRectangle.x -= 100 * density * Gdx.graphics.getDeltaTime();
				break;
			case 1:
				cloudRectangle.x -= 200 * density * Gdx.graphics.getDeltaTime();
				break;
			case 2:
				cloudRectangle.x -= 300 * density * Gdx.graphics.getDeltaTime();
				break;
			case 3:
				cloudRectangle.x -= 400 * density * Gdx.graphics.getDeltaTime();
				break;
			case 4:
				cloudRectangle.x -= 500 * density * Gdx.graphics.getDeltaTime();
				break;
			case 5:
				cloudRectangle.x -= 600 * density * Gdx.graphics.getDeltaTime();
				break;
			case 6:
				cloudRectangle.x -= 700 * density * Gdx.graphics.getDeltaTime();
				break;
			case 7:
				cloudRectangle.x -= 800 * density * Gdx.graphics.getDeltaTime();
				break;
			case 8:
				cloudRectangle.x -= 900 * density * Gdx.graphics.getDeltaTime();
				break;
			case 9:
				cloudRectangle.x -= 1000 * density * Gdx.graphics.getDeltaTime();
				break;
			}
			if(cloudRectangle.x + cloudRectangle.width <= 0) {
				iter.remove();
			}
		}

		Iterator<Rectangle> iter1 = lightningArray.iterator();
		while(iter1.hasNext()) {
			Rectangle lightningRectangle = iter1.next();
			switch (random) {
			case 0:
				lightningRectangle.x -= 100 * density * Gdx.graphics.getDeltaTime();
				break;
			case 1:
				lightningRectangle.x -= 200 * density * Gdx.graphics.getDeltaTime();
				break;
			case 2:
				lightningRectangle.x -= 300 * density * Gdx.graphics.getDeltaTime();
				break;
			case 3:
				lightningRectangle.x -= 400 * density * Gdx.graphics.getDeltaTime();
				break;
			case 4:
				lightningRectangle.x -= 500 * density * Gdx.graphics.getDeltaTime();
				break;
			case 5:
				lightningRectangle.x -= 600 * density * Gdx.graphics.getDeltaTime();
				break;
			case 6:
				lightningRectangle.x -= 700 * density * Gdx.graphics.getDeltaTime();
				break;
			case 7:
				lightningRectangle.x -= 800 * density * Gdx.graphics.getDeltaTime();
				break;
			case 8:
				lightningRectangle.x -= 900 * density * Gdx.graphics.getDeltaTime();
				break;
			case 9:
				lightningRectangle.x -= 1000 * density * Gdx.graphics.getDeltaTime();
				break;

			}

			if (Gdx.input.justTouched()) {
				setIsTouched(true);
				thunderSound.play(0.9f);

			}
			if (isTouched) {
				lightningArray.get(0).y -= 1200 * density * Gdx.graphics.getDeltaTime();
			}

			if (((lightningRectangle.overlaps(batteryRectangle)) && (!lightningRectangle.overlaps(buildingsRightRectangle) || !lightningRectangle.overlaps(buildingsLeftRectangle)))
					|| ((lightningRectangle.overlaps(batteryRectangle)) && (lightningRectangle.overlaps(buildingsRightRectangle) || lightningRectangle.overlaps(buildingsLeftRectangle)))) {
				if (lightningRectangle.y <= batteryRectangle.height/1.5f) {
					//lightningCounter++;
					batteryCounter++;
					if (lightningCounter > 6) {
						lightningCounter = 0;
					}
					if (batteryCounter >= 6) {
						batteryCounter = 1;
					}
					else if (batteryCounter == 5) {
						lightningCounter++;
						chargeSound.play();
					}
					isTouched = false;
					iter1.remove();
				}
			}
			else if ((!lightningRectangle.overlaps(batteryRectangle)) && (lightningRectangle.overlaps(buildingsRightRectangle) || lightningRectangle.overlaps(buildingsLeftRectangle))) {
				if (lightningRectangle.y <= buildingsLeftRectangle.height/1.5f) {
					outageSound.play(1);
					iter1.remove();
					lightningCounter = 0;
					batteryCounter = 0;
					isTouched = false;
					//dischargeSound.play(0.5f);

				}
			}
			else if ((lightningRectangle.x <= 0)) {
				if ((batteryCounter > 0) && (batteryCounter < 5)) {
					batteryCounter--;
					dischargeSound.play(0.8f);
				}
				iter1.remove();
				isTouched = false;
			}
			else if (lightningRectangle.y + lightningRectangle.height <= 0) {
				iter1.remove();
				isTouched = false;
			}

			if ((lightningCounter == 6) && (cricketBool)) {
				scoreTimer = ((TimeUtils.nanoTime() - initialScoreTime)/1000000000);
				if ((scoreTimer <= highScore) || (highScore == 0)) {
					highScore = scoreTimer;
					scorePrefs.putLong("HIGHSCORE", highScore);
					scorePrefs.flush();

					scoreSound = game.manager.get("betterScore.mp3", Sound.class);

				}
				else {
					scoreSound = game.manager.get("worseScore.mp3", Sound.class);

				}
				lightningArray.clear();
				cricketSound.play(1);
				cricketBool = false;
			}
		}
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = height - Gdx.input.getY();
			if ((x > restartRectangle.x) && (x < (restartRectangle.x + restartRectangle.width)) && (y < (restartRectangle.y + restartRectangle.height)) && (y > restartRectangle.y)) {
				thunderSound.stop();
				rainMusic.stop();
				blimpSound.stop();
				cricketSound.stop();
				outageSound.stop();
				chargeSound.stop();
				dispose();
				game.setScreen(new MainMenu(game, gameType));
			}
			else if ((x > menuRectangle.x) && (x < (menuRectangle.x + menuRectangle.width)) && (y < (menuRectangle.y + menuRectangle.height)) && (y > menuRectangle.y)) {
				thunderSound.stop();
				rainMusic.stop();
				blimpSound.stop();
				cricketSound.stop();
				outageSound.stop();
				chargeSound.stop();
				dispose();
				game.setScreen(new MainMenu(game, 4));
			}
		}
	}

	private void playBlimpMusic() {
		if (blimpBool) {
			blimpSound.setLooping(false);
			blimpSound.play();
		}
		blimpBool = false;
	}

	private void playScoreSound() {
		if (scoreSoundBool) {
			scoreSound.play();
		}
		scoreSoundBool = false;	
	}

	private void setIsTouched(boolean bool) {
		isTouched = bool;
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
	public void dispose() {
		game.manager.clear();
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}
}
