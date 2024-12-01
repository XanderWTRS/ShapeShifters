package com.Xander.shapeshifters;

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
    private float dashCooldownTime = 0.2f;  // How long to wait before dashing again (in seconds)
    private float dashCooldownTimer = 0f;   // Timer to track the cooldown



    public Player(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.speed = 200;
        this.dashDistance = 300;
        this.canDash = true;
        this.currentShape = ShapeType.SQUARE;
    }

    public void update(float deltaTime, List<Water> waterBlocks) {
        if (currentShape == ShapeType.CIRCLE) {
            speed = 400;
        } else {
            speed = 200;
        }

        if (dashCooldownTimer > 0) {
            dashCooldownTimer -= deltaTime;  // Decrease the cooldown timer
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

        // Check if the new position will cause a collision with water
        if (isCollidingWithWater(position.x + velocityX, position.y + velocityY, waterBlocks)) {
            velocityX = 0;  // Prevent horizontal movement
            velocityY = 0;  // Prevent vertical movement
        }

        // Update the position with the velocity
        position.x += velocityX;
        position.y += velocityY;

        // Clamp the position to the screen boundaries
        float screenWidth = 1920;
        float screenHeight = 1080;
        position.x = MathUtils.clamp(position.x, 0, screenWidth - width);
        position.y = MathUtils.clamp(position.y, 0, screenHeight - height);
    }

    public void render(ShapeRenderer shapeRenderer) {
        switch (currentShape) {
            case SQUARE:
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.rect(position.x, position.y, width, height);
                break;
            case CIRCLE:
                shapeRenderer.setColor(0, 1, 0, 1); // Green
                shapeRenderer.circle(position.x + width / 2, position.y + height / 2, width / 2); // Draw circle
                break;
            case TRIANGLE:
                shapeRenderer.setColor(0, 0, 1, 1); // Blue
                shapeRenderer.triangle(position.x, position.y, position.x + width, position.y, position.x + width / 2, position.y + height); // Draw triangle
                break;
            case STAR:
                shapeRenderer.setColor(1, 1, 0, 1); // Yellow
                drawStar(shapeRenderer); // Custom method to draw a star
                break;
        }
    }

    public boolean isCollidingWithWater(float newX, float newY, List<Water> waterBlocks) {
        for (Water water : waterBlocks) {
            if (water.checkCollision(newX, newY, width, height)) {
                return true;  // Return true if there's a collision
            }
        }
        return false;  // No collision
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

        // Calculate direction based on movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -dashDistance;  // Dash to the left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = dashDistance;   // Dash to the right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = dashDistance;   // Dash up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -dashDistance;  // Dash down
        }

        // Move the player in the calculated direction
        position.x += velocityX;
        position.y += velocityY;

        // Disable dashing until it resets
        canDash = false; // Disable dashing until reset
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
