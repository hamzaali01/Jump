/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jump;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.Font;

import java.util.ArrayList;

public class Board extends JPanel implements ActionListener
{
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private final int DELAY = 10;
    private int w = 1024;
    private int h = 768;	
    private Timer timer;
    private int count = 0;
    JLabel scorelabel = new JLabel("Score = 0");
    JLabel gamelabel = new JLabel("Game Over!");
    public int score=0;
    public static final Color LIGHT_BLUE  = new Color(51,153,255);
    public static final Color GREEN  = new Color(0,204,0);
    public static boolean inGame;
    private int EnemySpeed;

    private Player player;
    private ArrayList<Enemy> EnemyList;
    private Image img;
    private Menu menu;
    public static boolean CanResetGame;
    int wait=0;
	
       
    public Board(String BackgroundImage) 
    {    	
        initBoard(new ImageIcon(BackgroundImage).getImage());
    }
    
    private void initBoard(Image img) //Initializes all the game objects
    {	
        this.img = img;
    	addKeyListener(new TAdapter());
        //setBackground(Color.DARK_GRAY);
        setFocusable(true);
        add(scorelabel);
        add(gamelabel);

        gamelabel.setVisible(false);
        player = Player.getInstance(150, (h/2)+35);
        EnemyList = new ArrayList<>();

        inGame = false;
        CanResetGame=false;
        EnemySpeed=6;
        
        menu = new Menu();
	
        setPreferredSize(new Dimension((int)w, (int)h));   //Set the size of Window     
        timer = new Timer(DELAY, this); //Timer with 10 ms delay 
        timer.start();
    }
    
    
    @Override
    public void paintComponent(Graphics g) //Draws all the components on screen
    {
    	g.setColor(getBackground());		//get the background color
        g.clearRect(0 , 0, (int)w, (int)h);	//clear the entire window
    	Dimension size = getSize();  //get the current window size  	
        w = (int)size.getWidth();
        h = (int)size.getHeight();

       // super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        
        //g.setColor(LIGHT_BLUE);
        //g.fillRect(0, 0, w, (h/2)+170);
        //g.setColor(GREEN);
        //g.fillRect(0, (h/2)+170, w, h);
        scorelabel.setForeground(Color.WHITE);
        gamelabel.setForeground(Color.RED);
        
        Graphics2D g2d = (Graphics2D) g;
        player.paintComponent(g2d);
        DrawEnemies(g);

        var font = new Font("Serif", Font.BOLD, 18);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("S --> STRIKE",10, 20);

        font = new Font("Serif", Font.BOLD, 18);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("SPACE --> JUMP",10, 50);

        if(inGame==false){
            font = new Font("Serif", Font.BOLD, 40);
            g2d.setColor(Color.RED);
            g2d.setFont(font);
            g2d.drawString("GAME OVER",(w/2)-120, h/2);   
        }

        Toolkit.getDefaultToolkit().sync();

    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        step();
        if(inGame==true)
        count++;
        
        if(menu.isVisible()==false && inGame==false)
        wait++;
        //if(count%13==0)
        //score++;
    }
    public void step(){
     if(inGame==true){
        //int rand2 = (int)(Math.random()*1000); use this to increase randominess of enemy spawn time
            if(count==150 /*|| rand2<=2*/){
             int rand = (int)(Math.random()*2);
             if(rand==0)
             EnemyList.add(new Enemy(w, (h/2)+148 , 'l' ,EnemySpeed));
             if(rand==1)
             EnemyList.add(new Enemy(0, (h/2)+148 , 'r' , EnemySpeed));
             /*if(rand==2){  //change 2 to 3 in Line 144 
               EnemyList.add(new Enemy(w, (h/2)+148 , 'l' ,EnemySpeed));   
               EnemyList.add(new Enemy(0, (h/2)+148 , 'r' , EnemySpeed));
             }*/
             count=0;
            }
        player.jump();
        checkCollision();
        player.move();
        EnemiesMove();
        IncreaseEnemySpeed();
        scorelabel.setText("Score = " + score);
        dispose();
        repaint();
     }
     else{
         if(wait==160){
         menu = new Menu();
         wait=0;
         }
     }

     if(CanResetGame==true)
     resetGame();
    }
    private class TAdapter extends KeyAdapter 
    {

        @Override
        public void keyReleased(KeyEvent e) 
        {
           player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
           player.keyPressed(e);
        }
    }
    

    public void checkCollision(){
        ArrayList<Enemy> enemies = EnemyList;
        for(int i=0; i<enemies.size();i++){  
         if(enemies.get(i).getBounds().intersects(player.getBounds())==true){
             enemies.get(i).SetCanKill(true);
             if(player.CanKill==true && player.Xdirection!=enemies.get(i).Xdirection && player.PiercedEnemy==true ){//player.PiercedEnemy==true
                 enemies.get(i).die(true);
             }
             if(player.CanKill==false && enemies.get(i).CanKill==true && enemies.get(i).PiercedPlayer==true){
                player.die(true);
                if(player.EndGame==true)
                inGame=false;
             }
         }
       }
    } 

    public void IncreaseEnemySpeed(){
     if(score==0)
         EnemySpeed = 6;
     if(score>=100 && score<200)
         EnemySpeed = 8;
     if(score>=200 && score<300)
         EnemySpeed = 10;
     if(score>=300 && score<400)
         EnemySpeed = 12;
     if(score>=400 && score<500)
         EnemySpeed = 14;
     if(score>=500)
         EnemySpeed = 16;
    }
    
    public void DrawEnemies(Graphics g){
        ArrayList<Enemy> enemies = EnemyList;
        Graphics2D g2d = (Graphics2D) g;
       for(int i=0; i<enemies.size();i++){  
       enemies.get(i).paintComponent(g2d);
       }
    }
    
    public void EnemiesMove(){
         ArrayList<Enemy> enemies = EnemyList;
          for(int i=0; i<enemies.size();i++){  
           enemies.get(i).move();
       }
    }
    
    public void dispose(){
         ArrayList<Enemy> enemies = EnemyList;
          for(int i=0; i<enemies.size();i++){  
           if(enemies.isEmpty()==false && enemies.get(i).RemoveEnemy==true){
               enemies.remove(i);
               score+=20;
           }
           if(enemies.isEmpty()==false){
           if(enemies.get(i).Xdirection=='l' && enemies.get(i).getX()<-30 || enemies.get(i).Xdirection=='r' && enemies.get(i).getX()>w+30){
              enemies.remove(i);
              score+=20;
           }
           }
       }
    }

    public void resetGame(){
        player.EndGame=false;
        EnemyList.clear();
        score=0;
        count=0;
        player.die(false);
        inGame=true;
        player.setX(150);
        CanResetGame=false;    
    }
}