package sprites;

import javax.swing.ImageIcon;

public class Enemy extends Sprite {
    private int initialY;
    
    public Enemy(int x, int speed){
        w = 200;
        h = 200;
        this.x = x;
        this.speed = speed;
        y = 70;
        initialY = y;
        image = new ImageIcon(Enemy.class.getResource("monster-enemy.gif"));
    }

    public void move(){
        if(y > 800){
            resetPosition();
        }
        y += speed;
    }

    public void resetPosition() {
        y = initialY;
    }
}