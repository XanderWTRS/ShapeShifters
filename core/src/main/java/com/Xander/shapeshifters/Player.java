package com.Xander.shapeshifters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player
{
    private Vector2 position;
    private float width;
    private float height;
    private float speed;

    public Player(float x, float y, float width, float height)
    {
        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
        this.speed = 200;
    }

    public void update(float deltaTime) //Player movement with arrow-keys or wasd
    {
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
    }
    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.rect(position.x, position.y, width, height);
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
}
