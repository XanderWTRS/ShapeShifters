package com.Xander.shapeshifters;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.List;

public class Player {
    private Vector2 position;
    private float width;
    private float height;
    private float speed;
    private float dashDistance;
    public boolean canDash;
    private ShapeType currentShape;
    private float dashCooldownTime = 0.2f;
    private float dashCooldownTimer = 0f;
    private Sound dashSound;

    public Player(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.speed = 200;
        this.dashDistance = 300;
        this.canDash = true;
        this.currentShape = ShapeType.SQUARE;
        dashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dash_sound.mp3"));
    }

    public void update(float deltaTime, List<Water> waterBlocks) {
        if (currentShape == ShapeType.CIRCLE) {
            speed = 400;
        } else {
            speed = 200;
        }

        if (dashCooldownTimer > 0) {
            dashCooldownTimer -= deltaTime;
        }

        float velocityX = 0;
        float velocityY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -speed * deltaTime;
        }

        if (isCollidingWithWater(position.x + velocityX, position.y + velocityY, waterBlocks)) {
            velocityX = 0;
            velocityY = 0;
        }

        position.x += velocityX;
        position.y += velocityY;

        float screenWidth = 1920;
        float screenHeight = 1080;
        position.x = MathUtils.clamp(position.x, 0, screenWidth - width);
        position.y = MathUtils.clamp(position.y, 0, screenHeight - height);
    }

    public void render(ShapeRenderer shapeRenderer) {
        switch (currentShape) {
            case SQUARE:
                shapeRenderer.setColor(1, 0, 0, 1); // Red
                shapeRenderer.rect(position.x, position.y, width, height);
                break;
            case CIRCLE:
                shapeRenderer.setColor(0, 1, 0, 1); // Green
                shapeRenderer.circle(position.x + width / 2, position.y + height / 2, width / 2);
                break;
            case TRIANGLE:
                shapeRenderer.setColor(0, 0, 1, 1); // Blue
                shapeRenderer.triangle(position.x, position.y, position.x + width, position.y, position.x + width / 2, position.y + height);
                break;
            case STAR:
                shapeRenderer.setColor(1, 1, 0, 1); // Yellow
                drawStar(shapeRenderer);
                break;
        }
    }

    public boolean isCollidingWithWater(float newX, float newY, List<Water> waterBlocks) {
        for (Water water : waterBlocks) {
            if (water.checkCollision(newX, newY, width, height)) {
                return true;
            }
        }
        return false;
    }

    private void drawStar(ShapeRenderer shapeRenderer) {
        float centerX = position.x + width / 2;
        float centerY = position.y + height / 2;
        float radius = width / 2;

        shapeRenderer.line(centerX, centerY + radius, centerX, centerY - radius);
        shapeRenderer.line(centerX - radius, centerY, centerX + radius, centerY);
        shapeRenderer.line(centerX - radius / 2, centerY + radius / 2, centerX + radius / 2, centerY - radius / 2);
    }

    public void switchShape() {
        if (currentShape == ShapeType.SQUARE) {
            currentShape = ShapeType.CIRCLE;
        } else if (currentShape == ShapeType.CIRCLE) {
            currentShape = ShapeType.TRIANGLE;
        } else if (currentShape == ShapeType.TRIANGLE) {
            currentShape = ShapeType.STAR;
        } else if (currentShape == ShapeType.STAR) {
            currentShape = ShapeType.SQUARE;
        }
    }

    public void dashForward() {
        float velocityX = 0;
        float velocityY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -dashDistance;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = dashDistance;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = dashDistance;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -dashDistance;
        }

        position.x += velocityX;
        position.y += velocityY;

        dashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dash_sound.mp3"));
        AudioManager.playSound(dashSound);

        canDash = false;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public ShapeType getCurrentShape() {
        return currentShape;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDashDistance() {
        return dashDistance;
    }
    public void startDashCooldown() {
        dashCooldownTimer = dashCooldownTime;
    }

    public boolean isCanDash() {
        return canDash;
    }

    public float getDashCooldownTime() {
        return dashCooldownTime;
    }

    public float getDashCooldownTimer() {
        return dashCooldownTimer;
    }
}
