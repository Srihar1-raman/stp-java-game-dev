import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import sprites.Player;
import sprites.Enemy;
import javax.swing.Timer;

public class Board extends JPanel implements gameConstants {
    Timer timer;
    BufferedImage background;
    BufferedImage restartImage;
    Player player;
    Enemy enemies[] = new Enemy[3];
    private int score = 0;
    private int health = 100;
    private boolean isGameOver = false;
    
    public Board(){
        loadbg();
        loadRestartImage();
        initGame();
        setFocusable(true);
    }

    private void initGame() {
        player = new Player();
        loadEnemies();
        score = 0;
        health = 100;
        isGameOver = false;
        gameLoop();
        bindEvents();
    }

    private void loadRestartImage() {
        try {
            restartImage = ImageIO.read(Board.class.getResource("restart.png"));
        } catch (Exception e) {
            System.out.println("Error loading restart image");
            System.out.println(e);
        }
    }

    private void gameOver(Graphics pen){
        if(player.outOfScreen()){
            isGameOver = true;
            pen.setFont(new Font("times", Font.BOLD, 90));
            pen.setColor(Color.BLUE);
            pen.drawString("Game WIN", 370, 350);
            drawRestartMessage(pen);
            timer.stop();
            return;
        }

        if (health <= 0) {
            isGameOver = true;
            pen.setFont(new Font("times", Font.BOLD, 90));
            pen.setColor(Color.RED);
            pen.drawString("Game Over", 370, 350);
            drawRestartMessage(pen);
            timer.stop();
            return;
        }
    }

    private void drawRestartMessage(Graphics pen) {
        if (restartImage != null) {
            int imageWidth = 200;
            int imageHeight = 200;
            int x = (GWIDTH - imageWidth) / 2;
            int y = 400;
            
            pen.drawImage(restartImage, x, y, imageWidth, imageHeight, null);
        }
        
        pen.setFont(new Font("times", Font.BOLD, 30));
        pen.setColor(Color.WHITE);
        pen.drawString("Press any key to restart", GWIDTH/2 - 150, 650);
    }

    private boolean isCollide(Enemy enemy){
        int xdistance = Math.abs(player.x - enemy.x);
        int ydistance = Math.abs(player.y - enemy.y);
        int maxH = Math.max(player.h, enemy.h);
        int maxW = Math.max(player.w, enemy.w);
        return xdistance <= maxW-80 && ydistance <= maxH-80;
    }

    private void bindEvents(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){
            }

            @Override
            public void keyReleased(KeyEvent e){
                if (!isGameOver) {
                    player.speed = 0;
                }
            }

            @Override
            public void keyPressed(KeyEvent e){
                if (isGameOver) {
                    initGame();
                    return;
                }
                
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    player.speed = 10;
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    player.speed = -10;
                }
            }
        });
    }

    private void loadEnemies(){
        int x = 300;
        int gap = 350;
        int speed = 10;
        for(int i = 0; i < enemies.length; i++){
            enemies[i] = new Enemy(x, speed);
            x += gap;
            speed += 5;
        }
    }

    private void gameLoop(){
        timer = new Timer(50, (e) -> {
            updateGame();
            repaint();
        });
        timer.start();
    }
    
    private void updateGame() {
        if (!isGameOver) {
            score += 1;
            
            // Check collisions
            for (Enemy enemy : enemies) {
                if (isCollide(enemy)) {
                    health -= 5;  // Reduce health on collision
                    enemy.resetPosition();  // Reset enemy position
                }
            }
        }
    }

    private void loadbg(){
        try{
            background = ImageIO.read(Board.class.getResource("bg0.jpeg"));
        }
        catch (Exception e){
            System.out.println("error with loading bg");
            System.out.println(e);
        }
    }

    private void printEnemies(Graphics pen){
        for(Enemy enemy: enemies){
            enemy.draw(pen);
            enemy.move();
        }
    }    

    private void drawHUD(Graphics pen) {
        // Draw score
        pen.setFont(new Font("Arial", Font.BOLD, 20));
        pen.setColor(Color.WHITE);
        pen.drawString("Score: " + score, 20, 30);

        // Draw health bar
        pen.setColor(Color.RED);
        pen.fillRect(20, 40, 200, 20);
        pen.setColor(Color.GREEN);
        pen.fillRect(20, 40, health * 2, 20);
        pen.setColor(Color.WHITE);
        pen.drawRect(20, 40, 200, 20);
        pen.drawString("Health: " + health, 230, 57);
    }

    @Override
    public void paintComponent(Graphics pen){
        super.paintComponent(pen);
        pen.drawImage(background, 0, 0, GWIDTH, GHEIGHT, null);
        player.draw(pen);
        player.move();
        printEnemies(pen);
        drawHUD(pen);
        gameOver(pen);
    }
}