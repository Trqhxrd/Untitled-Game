package com.github.trqhxrd.untitledgame;

import java.awt.*;

public abstract class GameObject {

protected float x;
protected float y;
protected int width;
protected int height;

public GameObject(float x, float y, int width, int height) {

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

}

public void setLocation(int x, int y) {

    this.x = x;
    this.y = y;

}

public Rectangle getBox() {

    Rectangle rectangle = new Rectangle();
    rectangle.setBounds((int)x, (int)y, width, height);
    return rectangle;
}




    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
