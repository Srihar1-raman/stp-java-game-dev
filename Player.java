package sprites;

import javax.swing.ImageIcon;
// import java.awt.Graphics;

public class Player extends Sprite{
    // int x;
    // int y;
    // int w;
    // int h;
    // ImageIcon image;
    public Player(){
        w = 200;
        h = 200;
        x = 50;
        y = 500;
        image = new ImageIcon(Player.class.getResource("player.gif"));
    }


    public void move(){
        x = x + speed;
    }

    public boolean outOfScreen(){
        return x>1200;
    }


    // public void draw(Graphics pen){
    //     pen.drawImage(image.getImage(), x, y, w, h, null);
    // }
}
