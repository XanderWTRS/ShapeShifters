package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player
{
    private Vector2 position;
    private float width;
    private float height;
    private float speed;
    private ShapeType currentShape;

    public Player(float x, float y, float width, float height)
    {
        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
        this.speed = 200;
        this.currentShape = ShapeType.SQUARE;
    }

    public void update(float deltaTime) //Player movement with arrow-keys or wasd
    {
        if(currentShape == ShapeType.CIRCLE)
        {
            speed = 400;
        }
        else speed = 200;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
        {
            position.x -= speed * deltaTime;  //left
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
        {
            position.x += speed * deltaTime;  //right
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
        {
            position.y += speed * deltaTime;  //up
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) ||Gdx.input.isKeyPressed(Input.Keys.S))
        {
            position.y -= speed * deltaTime;  //down
        }

        float screenWidth = 1920;
        float screenHeight = 1080;

        position.x = MathUtils.clamp(position.x, 0, screenWidth - width);
        position.y = MathUtils.clamp(position.y, 0, screenHeight - height);
    }
    public void render(ShapeRenderer shapeRenderer) {
        switch(currentShape)
        {
            case SQUARE:
                shapeRenderer.setColor(1,0,0,1);
                shapeRenderer.rect(position.x, position.y, width,height);
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

    private void drawStar(ShapeRenderer shapeRenderer)
    {
        float centerX = position.x + width / 2;
        float centerY = position.y + height / 2;
        float radius = width / 2;

        shapeRenderer.line(centerX, centerY + radius, centerX, centerY - radius);
        shapeRenderer.line(centerX - radius, centerY, centerX + radius, centerY);
        shapeRenderer.line(centerX - radius / 2, centerY + radius / 2, centerX + radius / 2, centerY - radius / 2);
    }

    public void switchShape()
    {
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
}
