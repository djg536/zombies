package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.zombies.states.Level;

import java.lang.Math;

public class Player extends Entity {

	private String name;
	private int speed;
	private int health;
	private float points = 18110;
	private String pointDisplay = "18110.0";
	private Sprite sprite;
	private int rotation;

	private float directionX;
	private float directionY;
	private float normalX;
	private float normalY;
	private double angleRads;
	private float nuAngle;

	private double noise;

	private float time;
	private int timer;
	private int last;

	private Sprite hud;
	private SpriteBatch spriteBatch;
	private SpriteBatch UIBatch;

	public Player(Level level, int x, int y, int h) {
		super();

		spriteBatch = level.worldBatch;
		UIBatch = level.UIBatch;

		sprite = new Sprite(new Texture(Gdx.files.internal("block.png")));
		hud = new Sprite(new Texture(Gdx.files.internal("block.png")));
		health = h;

		box2dWorld = level.box2dWorld;
		body = box2dWorld.createBody(level.mob);
		final PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(sprite.getWidth() / 2 / Zombies.PhysicsDensity,
				sprite.getHeight() / 2 / Zombies.PhysicsDensity);

		FixtureDef fixtureDef = new FixtureDef() {
			{
				shape = polyShape;
				density = 40;
				friction = 0.5f;
				restitution = 0f;
				filter.maskBits = Zombies.projectileFilter;
				filter.categoryBits = Zombies.playerFilter;
			}
		};

		body.createFixture(fixtureDef);
		body.setUserData(new InfoContainer(InfoContainer.BodyID.PLAYER, this));
		body.setTransform(x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, 0);
		body.setLinearDamping(6);
		body.setFixedRotation(true);
		polyShape.dispose();
	}

	public int points() {

		time += Gdx.graphics.getDeltaTime();
		timer = Math.round(time);

		if (timer % 2 == 0 && timer != last) {
			points -= Math.round(Math.random() * 10);
			pointDisplay = Integer.toString((int) points);

			last = timer;
		}

		return timer;
	}

	public double getNoise() {

		if (body.getLinearVelocity().x == 0) {
			noise = Math.abs(body.getLinearVelocity().y);
		} else if (body.getLinearVelocity().y == 0) {
			noise = Math.abs(body.getLinearVelocity().x);
		} else if (Math.abs(body.getLinearVelocity().x) > 0 && Math.abs(body.getLinearVelocity().y) > 0) {
			noise = Math.sqrt((body.getLinearVelocity().x * body.getLinearVelocity().x)
					+ (body.getLinearVelocity().y * body.getLinearVelocity().y));
		} else {
			noise = 0;
		}

		noise = noise * 1000;

		return noise;
	}

	public int health() {

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			health -= 1;

			System.out.println(health);
		}
		if (health <= 0) {
			System.out.println("RESTART");
			return 1;
		}
		return 0;
	}

	private void look(Vector3 mouseCoords) {

		int mouseX = (int) mouseCoords.x;
		int mouseY = (int) mouseCoords.y;

		directionX = mouseX - getPositionX();
		directionY = mouseY - getPositionY();

		normalX = 0;

		if (mouseY > getPositionY()) {
			normalY = getPositionY() + 10;
		} else if (mouseY < getPositionY()) {
			normalY = getPositionY() - 10;
		}

		double dotProduct = normalX * directionX + normalY * directionY;
		double sizeA = (Math.sqrt(normalX * normalX + normalY * normalY));
		double sizeB = (Math.sqrt(directionX * directionX + directionY * directionY));

		angleRads = Math.acos(dotProduct / (sizeA * sizeB));

		if (mouseX > getPositionX()) {
			angleRads = -angleRads;
		}

		double angle = Math.toDegrees(angleRads);

		nuAngle = (float) angle;
		sprite.setRotation(nuAngle);
		// System.out.println(positionX + ", " + positionY + " : " + mouseX + ", " +
		// mouseY + " : " + nuAngle);
	}

	private void move() {

		if (Gdx.input.isKeyPressed(Keys.W)) {
			body.applyLinearImpulse(new Vector2(0, 1), body.getPosition(), true);
		}

		if (Gdx.input.isKeyPressed(Keys.S)) {
			body.applyLinearImpulse(new Vector2(0, -1), body.getPosition(), true);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			body.applyLinearImpulse(new Vector2(-1, 0), body.getPosition(), true);
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			body.applyLinearImpulse(new Vector2(1, 0), body.getPosition(), true);
		}
	}

	private void attack() {

	}

	public int update(Vector3 mouseCoords) {
		move();
		look(mouseCoords);
		sprite.setPosition(getPositionX() - sprite.getWidth() / 2, getPositionY() - sprite.getHeight() / 2);
		sprite.setRotation(nuAngle);
		points();
		return health();
	}

	public void render() {

		sprite.draw(spriteBatch);
	}

	public void hudRender() {

		Zombies.pointsFont.draw(UIBatch, pointDisplay, 100, 590);

		for (int i = 0; i < health; i++) {
			hud.setPosition(100 + i * 50, 620);
			hud.draw(UIBatch);
		}
	}

	public int getPositionX() {
		return (int) (body.getPosition().x * Zombies.PhysicsDensity);
	}

	public int getPositionY() {
		return (int) (body.getPosition().y * Zombies.PhysicsDensity);
	}

	public Body getBody() {
		return body;
	}

	public double getAngleRads() {
		return angleRads;
	}

	public void setPowerUp(PowerUp powerUp) {

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if(health <= 0) {						
			getInfo().flagForDeletion();
		}
	}
}
