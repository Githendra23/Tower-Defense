package projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Projectile{
    private float speedX=0;
    private float speedY=0;
    TextureRegion region;
    public Bullet(int positionX, int positionY) {
        super(positionX, positionY, 2,2, new Texture("bullet.png"));
        region=new TextureRegion(img);
//        homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
    }

    public TextureRegion drawRocket()
    {
        return region;
    }

    public void shootAt(float targetX, float targetY,int speed){

//        if (positionX<targetX) positionX+=speed;
//        if (positionX>targetX) positionX-=speed;
//        if (positionY<targetY) positionY+=speed;
//        if (positionY>targetY) positionY-=speed;

        float angle =(float) Math.atan2(targetY-getPositionY(),targetX-getPositionX());
        positionX=(int)(getPositionX()+Math.cos(angle)*speed);
        positionY=(int)(getPositionY()+Math.sin(angle)*speed);
        hitbox.x=getPositionX();
        hitbox.y=getPositionY();

    }
}
