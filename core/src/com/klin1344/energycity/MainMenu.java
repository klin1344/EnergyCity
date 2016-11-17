package com.klin1344.energycity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu implements Screen {

	public static final int GAME_EASY = 0;
	public static final int GAME_MEDIUM = 1;
	public static final int GAME_HARD = 2;
	public static final int GAME_INSANE = 3;

	final EnergyCity game;

	private OrthographicCamera cam;

	private int height = Gdx.graphics.getHeight();
	private int width = Gdx.graphics.getWidth();

	private Texture easyClassic;
	private Texture mediumClassic;
	private Texture hardClassic;

	private Rectangle easyRectangle;
	private Rectangle mediumRectangle;
	private Rectangle hardRectangle;
	private int gameType;
	private Texture loadingBar;
	private Rectangle barRectangle;
	private Texture menuBackground;
	private Texture easyPressed;
	private Texture mediumPressed;
	private Texture hardPressed;
	private Texture insanePressed;
	private Texture instructionsPressed;

	private Texture insane;
	private Texture instructions;
	private Rectangle insaneRectangle;
	private Rectangle instructionsRectangle;
	private Texture welcome;
	private Rectangle welcomeRectangle;

	private Texture versionInfo;
	private Rectangle infoRectangle;

	private Texture facebookLike;
	private Rectangle likeRectangle;
	private boolean gameSelected = false;


	private float density;

	private int restartGame;



	public MainMenu(final EnergyCity gam, int restartGam) {
		game = gam;

		game.manager = new AssetManager();


		cam = new OrthographicCamera(width, height);
		cam.setToOrtho(false, width, height);

		density = Gdx.graphics.getDensity();
		//System.out.println(density);

		restartGame = restartGam;

		menuBackground = new Texture(Gdx.files.internal("menu_buildings.jpg"));

		easyClassic = new Texture(Gdx.files.internal("classic_easy.png"));
		mediumClassic = new Texture(Gdx.files.internal("classic_medium.png"));
		hardClassic = new Texture(Gdx.files.internal("classic_hard.png"));
		insane = new Texture(Gdx.files.internal("classic_insane.png"));
		instructions = new Texture(Gdx.files.internal("instructions.png"));
		welcome = new Texture(Gdx.files.internal("welcome.png"));

		easyPressed = new Texture(Gdx.files.internal("easy_pressed.png"));
		mediumPressed = new Texture(Gdx.files.internal("medium_pressed.png"));
		hardPressed = new Texture(Gdx.files.internal("hard_pressed.png"));
		insanePressed = new Texture(Gdx.files.internal("insane_pressed.png"));
		instructionsPressed = new Texture(Gdx.files.internal("instructions_pressed.png"));

		welcomeRectangle = new Rectangle();
		welcomeRectangle.width = width/1.2f;
		welcomeRectangle.height = height/3;
		welcomeRectangle.x = width/2 - welcomeRectangle.width/2;
		welcomeRectangle.y = height - welcomeRectangle.height;

		easyRectangle = new Rectangle();
		easyRectangle.width = width/3.4f;
		easyRectangle.height = height/3.2f;
		easyRectangle.x = 0;
		easyRectangle.y = welcomeRectangle.y - (easyRectangle.height + (5 * density));
		//System.out.println(easyRectangle.y);

		mediumRectangle = new Rectangle();
		mediumRectangle.width = easyRectangle.width;
		mediumRectangle.height = easyRectangle.height;
		mediumRectangle.x = width/2 - mediumRectangle.width/2;
		mediumRectangle.y = easyRectangle.y;

		hardRectangle = new Rectangle();
		hardRectangle.width = easyRectangle.width;
		hardRectangle.height = easyRectangle.height;
		hardRectangle.x = width - hardRectangle.width;
		hardRectangle.y = easyRectangle.y;

		instructionsRectangle = new Rectangle();
		instructionsRectangle.width = easyRectangle.width/1.2f;
		instructionsRectangle.height = easyRectangle.height/1.2f;
		instructionsRectangle.y = easyRectangle.y - (instructionsRectangle.height + (density * 10));
		instructionsRectangle.x = width/1.8f;


		insaneRectangle = new Rectangle();
		insaneRectangle.width = instructionsRectangle.width;
		insaneRectangle.height = instructionsRectangle.height;
		insaneRectangle.y = instructionsRectangle.y;
		insaneRectangle.x = width/5;

		loadingBar = new Texture(Gdx.files.internal("loading_bar.png"));
		barRectangle = new Rectangle();
		barRectangle.width = 0;
		barRectangle.height = instructionsRectangle.y - (5 * density);
		barRectangle.x = 0;
		barRectangle.y = 0;

		versionInfo = new Texture(Gdx.files.internal("version_info.png"));
		infoRectangle = new Rectangle();
		infoRectangle.width = width/6;
		infoRectangle.height = height/25;
		//arcadeRectangle.x = 

		facebookLike = new Texture(Gdx.files.internal("facebook_like.png"));
		likeRectangle = new Rectangle();
		likeRectangle.width = width/7;
		likeRectangle.height = height/12;
		likeRectangle.x =(width - (likeRectangle.width + (10 * density)));
		likeRectangle.y = 10 * density;

		switch (restartGame) {
		case GAME_EASY:
			gameType = GAME_EASY;
			gameSelected = true;
			LoadAssets(GAME_EASY);
			break;
		case GAME_MEDIUM:
			gameType = GAME_MEDIUM;
			gameSelected = true;
			LoadAssets(GAME_MEDIUM);
			break;
		case GAME_HARD:
			gameType = GAME_HARD;
			gameSelected = true;
			LoadAssets(GAME_HARD);
			break;
		case GAME_INSANE:
			gameType = GAME_INSANE;
			gameSelected = true;
			LoadAssets(GAME_INSANE);
			break;
		default:
			break;
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		game.batch.setProjectionMatrix(cam.combined);

		game.batch.begin();
		//Gdx.gl.glClearColor(0, 0, 0, 0);
		game.batch.draw(menuBackground, 0, 0, width, height);
		game.batch.draw(welcome, welcomeRectangle.x, welcomeRectangle.y, welcomeRectangle.width, welcomeRectangle.height);
		game.batch.draw(easyClassic, easyRectangle.x, easyRectangle.y, easyRectangle.width, easyRectangle.height);
		game.batch.draw(mediumClassic, mediumRectangle.x, mediumRectangle.y, mediumRectangle.width, mediumRectangle.height);
		game.batch.draw(hardClassic, hardRectangle.x, hardRectangle.y, hardRectangle.width, hardRectangle.height);
		game.batch.draw(instructions, instructionsRectangle.x, instructionsRectangle.y, instructionsRectangle.width, instructionsRectangle.height);
		game.batch.draw(insane, insaneRectangle.x, insaneRectangle.y, insaneRectangle.width, insaneRectangle.height);
		game.batch.draw(versionInfo, 0, 0, infoRectangle.width, infoRectangle.height);
		game.batch.draw(facebookLike, likeRectangle.x, likeRectangle.y, likeRectangle.width, likeRectangle.height);
		//game.batch.draw(loadingBar, barRectangle.x, barRectangle.y, barRectangle.width, barRectangle.height);

		if (Gdx.input.isTouched()) {
			//System.out.println(Gdx.input.getX() + " , " + (height - Gdx.input.getY()));
			//System.out.println((height - easyRectangle.y));
			//System.out.println(mediumRectangle.y + mediumRectangle.height);
			int x = Gdx.input.getX();
			int y = height - Gdx.input.getY();

			if ((x > easyRectangle.x) && (x < (easyRectangle.x + easyRectangle.width)) && (y < (easyRectangle.y + easyRectangle.height)) && (y > (easyRectangle.y))) {
				game.batch.draw(easyPressed, easyRectangle.x, easyRectangle.y, easyRectangle.width, easyRectangle.height);
				gameType = GAME_EASY;
				gameSelected = true;
				LoadAssets(GAME_EASY);
				//System.out.println(game.manager.update());
				//System.out.println("easy");
			}
			else if ((x > mediumRectangle.x) && (x < (mediumRectangle.x + mediumRectangle.width)) && (y < (mediumRectangle.y + mediumRectangle.height)) && (y > (mediumRectangle.y))) {
				game.batch.draw(mediumPressed, mediumRectangle.x, mediumRectangle.y, mediumRectangle.width, mediumRectangle.height);
				gameType = GAME_MEDIUM;
				gameSelected = true;
				LoadAssets(GAME_MEDIUM);
				//System.out.println("medium");
			}
			else if ((x > hardRectangle.x) && (x < (hardRectangle.x + hardRectangle.width)) && (y < (hardRectangle.y + hardRectangle.height)) && (y > (hardRectangle.y))) {
				game.batch.draw(hardPressed, hardRectangle.x, hardRectangle.y, hardRectangle.width, hardRectangle.height);
				gameType = GAME_HARD;
				gameSelected = true;
				LoadAssets(GAME_HARD);
				//System.out.println("hard");
			}
			else if ((x > insaneRectangle.x) && (x < (insaneRectangle.x + insaneRectangle.width)) && (y < (insaneRectangle.y + insaneRectangle.height)) && (y > (insaneRectangle.y))) {
				game.batch.draw(insanePressed, insaneRectangle.x, insaneRectangle.y, insaneRectangle.width, insaneRectangle.height);
				gameType = GAME_INSANE;
				gameSelected = true;
				LoadAssets(GAME_INSANE);
				//System.out.println("hard");
			}
			else if ((x > instructionsRectangle.x) && (x < (instructionsRectangle.x + instructionsRectangle.width)) && (y < (instructionsRectangle.y + instructionsRectangle.height)) && (y > (instructionsRectangle.y))) {
				game.batch.draw(instructionsPressed, instructionsRectangle.x, instructionsRectangle.y, instructionsRectangle.width, instructionsRectangle.height);
				dispose();
				game.setScreen(new AboutScreen(game));
				//dispose();
				//System.out.println("hard");
			}
			else if ((x > likeRectangle.x) && (x < (likeRectangle.x + likeRectangle.width)) && (y < (likeRectangle.y + likeRectangle.height)) && (y > (likeRectangle.y))) {
				Gdx.net.openURI("https://www.facebook.com/energycityandroid");
			}

		}
		if (gameSelected) {
			barRectangle.width = game.manager.getProgress() * width;
			game.batch.draw(loadingBar, barRectangle.x, barRectangle.y, barRectangle.width, barRectangle.height);
			if (game.manager.update()) {
				barRectangle.width = width;
				if (gameType == GAME_EASY) {
					dispose();
					game.setScreen(new GameClassic(game, GAME_EASY));
				}
				else if (gameType == GAME_MEDIUM) {
					dispose();
					game.setScreen(new GameClassic(game, GAME_MEDIUM));
				}
				else if (gameType == GAME_HARD) {
					dispose();
					game.setScreen(new GameClassic(game, GAME_HARD));
				}
				else if (gameType == GAME_INSANE) {
					dispose();
					game.setScreen(new GameClassic(game, GAME_INSANE));
				}
			}
		}
		game.batch.end();
		//barRectangle.width = game.manager.getProgress() * width;
		//System.out.println(game.manager.getProgress());
	}

	private void LoadAssets(int gameType) {
		game.manager.load("night_background.jpg", Texture.class);
		game.manager.load("battery.png", Texture.class);
		game.manager.load("cloud.png", Texture.class);
		game.manager.load("lightning.png", Texture.class);
		game.manager.load("buildings_dark_left.png", Texture.class);
		game.manager.load("buildings_dark_right.png", Texture.class);
		game.manager.load("buildings_light_left.png", Texture.class);
		game.manager.load("buildings_light_right.png", Texture.class);
		game.manager.load("buildings_light_left_1.png", Texture.class);
		game.manager.load("buildings_light_left_3.png", Texture.class);
		game.manager.load("buildings_light_right_2.png", Texture.class);
		game.manager.load("buildings_light_right_4.png", Texture.class);
		game.manager.load("buildings_light_right_5.png", Texture.class);
		game.manager.load("battery_filler.png", Texture.class);
		game.manager.load("raindrop.png", Texture.class);
		game.manager.load("moon.png", Texture.class);
		game.manager.load("buildings_light_right_5.png", Texture.class);
		game.manager.load("laser_light_right.png", Texture.class);
		game.manager.load("laser_light_left.png", Texture.class);
		game.manager.load("ic_replay.png", Texture.class);
		game.manager.load("ic_menu.png", Texture.class);
		game.manager.load("cricket.mp3", Sound.class);
		game.manager.load("blimp.mp3", Music.class);
		game.manager.load("batteryCharge.mp3", Sound.class);
		game.manager.load("rain.mp3", Music.class);
		game.manager.load("thunder.mp3", Sound.class);
		game.manager.load("poweroutage.mp3", Sound.class);
		game.manager.load("betterScore.mp3", Sound.class);
		game.manager.load("worseScore.mp3", Sound.class);
		game.manager.load("star.png", Texture.class);
		game.manager.load("confetti.png", Texture.class);
		game.manager.load("powerdown.mp3", Sound.class);

		switch (gameType) {
		case GAME_EASY:
			game.manager.load("blimp_easy.png", Texture.class);
			break;
		case GAME_MEDIUM:
			game.manager.load("blimp_medium.png", Texture.class);
			break;
		case GAME_HARD:
			game.manager.load("blimp_hard.png", Texture.class);
			break;
		case GAME_INSANE:
			game.manager.load("blimp_insane.png", Texture.class);
			break;
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
		easyClassic.dispose();
		mediumClassic.dispose();
		hardClassic.dispose();
		loadingBar.dispose();
		insane.dispose();
		instructions.dispose();
		welcome.dispose();
		versionInfo.dispose();
		menuBackground.dispose();
		easyPressed.dispose();
		mediumPressed.dispose();
		hardPressed.dispose();
		insanePressed.dispose();
		instructionsPressed.dispose();
		//game.manager.clear();
		facebookLike.dispose();
	}

}
