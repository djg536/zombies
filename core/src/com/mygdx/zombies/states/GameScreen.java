package com.mygdx.zombies.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.zombies.BaseActor;
import com.mygdx.zombies.PhysicsActor;
import com.mygdx.zombies.Player;

import java.util.ArrayList;

public class GameScreen extends BaseScreen {

    private Player player;
    private ArrayList<BaseActor> wallList;
    private ArrayList<BaseActor> removeList;

    private int tileSize = 16;
    private int tileCountWidth = 40;
    private int tileCountHeight = 40;

    final int mapWidth = tileSize * tileCountWidth;
    final int mapHeight = tileSize * tileCountHeight;

    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private int[] backgroundLayers = {0, 1};
    private int[] foregroundLayers = {2};

    public GameScreen(Game g) {
        super(g);
    }

    public void create() {
        player = new Player(new Texture(Gdx.files.internal("block.png")));
        mainStage.addActor(player);

        wallList = new ArrayList<BaseActor>();

        tiledMap = new TmxMapLoader().load("stages/teststage.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewWidth, viewHeight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get(3).getObjects();
        for (MapObject object : objects) {

            RectangleMapObject rectangleObject = (RectangleMapObject) object;
            Rectangle r = rectangleObject.getRectangle();

            player.setPosition(r.x, r.y);
        }

        objects = tiledMap.getLayers().get(2).getObjects();
        for (MapObject object : objects) {
            RectangleMapObject rectangleObject = (RectangleMapObject) object;
            Rectangle r = rectangleObject.getRectangle();

            BaseActor solid = new BaseActor();
            solid.setPosition(r.x, r.y);
            solid.setSize(r.width, r.height);
            solid.setRectangleBoundary();
            wallList.add(solid);
        }


    }

    public void update(float dt) {
//        float playerSpeed = 500;
//        player.setVelocityXY(0,0);

//        if (Gdx.input.isKeyPressed(Input.Keys.W))
//            player.setVelocityXY(0, playerSpeed);
//        if (Gdx.input.isKeyPressed(Input.Keys.A))
//            player.setVelocityXY(-playerSpeed, 0);
//        if (Gdx.input.isKeyPressed(Input.Keys.S))
//            player.setVelocityXY(-0, -playerSpeed);
//        if (Gdx.input.isKeyPressed(Input.Keys.D))
//            player.setVelocityXY(playerSpeed, 0);

        player.update(dt);

        for (BaseActor wall : wallList) {
            player.overlaps(wall, true);
        }

        Camera mainCamera = mainStage.getCamera();
        // centre camera on player
        mainCamera.position.x = player.getX() + player.getOriginX();
        mainCamera.position.y = player.getY() + player.getOriginY();
        //bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewWidth/2, mapWidth - viewWidth/2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewHeight/2, mapHeight - viewHeight/2);
        mainCamera.update();

        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);
    }

    public void render(float dt) {
        uiStage.act(dt);

        if (!isPaused()) {
            mainStage.act(dt);
            update(dt);
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();
        tiledMapRenderer.render(foregroundLayers);
        uiStage.draw();
    }
}
