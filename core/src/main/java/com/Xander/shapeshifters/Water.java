package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Water
{
    private float x, y, width, height;

    public Water(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.rect(x, y, width, height);
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return playerX < x + width && playerX + playerWidth > x && playerY < y + height && playerY + playerHeight > y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
