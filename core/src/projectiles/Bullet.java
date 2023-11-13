package projectiles;

import com.badlogic.gdx.graphics.Texture;

public class Bullet extends Projectile{
    private float speedX=0;
    private float speedY=0;
    public Bullet(int positionX, int positionY) {
        super(positionX, positionY, 2,2, new Texture("rocket.png"));
//        homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
    }

    public Texture drawRocket()
    {
        return img;
    }

    public void shootAt(float targetX, float targetY,int speed){

        if (positionX<targetX) positionX+=speed;
        if (positionX>targetX) positionX-=speed;
        if (positionY<targetY) positionY+=speed;
        if (positionY>targetY) positionY-=speed;


    }
}
