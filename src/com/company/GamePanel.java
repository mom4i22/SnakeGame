package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25; //How big an object is in the game
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int delay = 75; //the higher the number the slower the game

    //Coordinates
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snakeBodyParts = 6;//initial snake body parts count
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';//begin game going right
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
if(running){
    move();
    checkForEatingApple();
    checkCrash();
}
repaint();
    }

    public void startGame(){
        addApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {

        if (running) {
            //For development purposes only
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
            graphics.setColor(Color.RED);
            graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeBodyParts; i++) {
                if (i == 0) {
                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    graphics.setColor(new Color(45, 180, 0));
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Ink Free",Font.BOLD,30));
            FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: "+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score: "+applesEaten))/2,graphics.getFont().getSize());
        }
        else{
            gameOver(graphics);
        }
    }

    public void addApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move(){
        for(int i=snakeBodyParts;i>0;i--){
            x[i]=x[i-1]; //shifting coordinates by 1
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U': y[0] = y[0]-UNIT_SIZE;
            break;
            case 'D': y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L': x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R': x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }

    public void checkForEatingApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            snakeBodyParts++;
            applesEaten++;
            addApple();
        }
    }

    public void checkCrash(){
        //checks if head collides with body
for(int i=snakeBodyParts;i>0;i--){
    if((x[0]==x[i]) &&(y[0]==y[i])){
        running=false;
    }
}
//checks if head touches left border
        if(x[0]<0){
            running=false;
        }

        //checks for right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }

        //checks for top border
        if(y[0]<0){
            running=false;
        }

        //checks for bottom border
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics){
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Ink Free",Font.BOLD,30));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score: "+applesEaten))/2,graphics.getFont().getSize());

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics fontMetrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER",(SCREEN_WIDTH-fontMetrics1.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
switch(keyEvent.getKeyCode()){
    case KeyEvent.VK_LEFT:
        if(direction != 'R'){
            direction='L';
        }
        break;

    case KeyEvent.VK_RIGHT:
        if(direction != 'L'){
            direction='R';
        }
        break;

    case KeyEvent.VK_UP:
        if(direction != 'D'){
            direction='U';
        }
        break;

    case KeyEvent.VK_DOWN:
        if(direction != 'U'){
            direction='D';
        }
        break;
}
        }
    }
}


