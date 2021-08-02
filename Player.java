/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jump;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Player
{
    private static Player player;
    private int x,y,w,h;
    private int JUMP_SPEED;
    private int MOVE_SPEED;
    private int MIN_JUMP;
    private int OriginalY;
    private boolean CanJump;
    protected boolean CanKill;
    protected boolean isDead;
    private boolean CanWalk;
    protected boolean PiercedEnemy;
    protected boolean EndGame;
    private char Ydirection;
    protected char Xdirection;
    protected Image[] WalkImages;
    protected Image[] StrikeImages;
    protected Image[] StandImages;
    protected Image[] DieImages;
    protected long Walkcount = 0;
    protected long Strikecount = 0;
    protected long Standcount = 0;
    protected long Diecount = 0;
    protected int rand = 0;
    int num=0;

	private Player(int x, int y)
	{
	this.x = x;
    this.y = y;	
    CanJump=false;
    CanKill=false;
    CanWalk=false;
    isDead=false;
    EndGame=false;
    PiercedEnemy=false;
    JUMP_SPEED=-10;
    MOVE_SPEED = 7;
    OriginalY = y;
    MIN_JUMP = OriginalY - 300;
    Ydirection = 'u';
    Xdirection = 'r';
    WalkImages = new Image[8];
    StrikeImages = new Image[12];
    StandImages = new Image[8];
    DieImages = new Image[8];
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/knightwalk/right/knightwalk" + i + ".png" );
		WalkImages[i] = imageIcon.getImage();
		}		
    for(int i=0; i<12; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/knightattack/right/knightattack" + i + ".png" );
		StrikeImages[i] = imageIcon.getImage();
		}		
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/knightstand/knightidle" + i + ".png" );
		StandImages[i] = imageIcon.getImage();
		}	
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/knightdead/knightdead" + i + ".png" );
		DieImages[i] = imageIcon.getImage();
		}			

		w = StrikeImages[5].getWidth(null);
        h = StrikeImages[5].getHeight(null);   
	}
	
        
        public static Player getInstance( int x, int y){
            if(player==null)
                return new Player(x,y);
            else
                return player;
        }
	public void jump()
	{
        if(y==OriginalY && Ydirection=='d'){
        Ydirection='u';
        CanJump=false;
        }
        if(CanJump==true && y<=MIN_JUMP)
        Ydirection='d';
        if(CanJump==true && Ydirection=='u')
        y+=JUMP_SPEED;
        if(CanJump==true && Ydirection=='d')
        y-=JUMP_SPEED;
    }

    public void move()
    {
        if(CanWalk==true && Xdirection=='r' && CanKill==false)
        x+=MOVE_SPEED;
        if(CanWalk==true && Xdirection=='l' && CanKill==false)
        x-=MOVE_SPEED;
    }
   
    public void die(boolean state)
    {
     if(state==true){
     isDead=true;
     }   
     else
     isDead=false;
    }
	
	public void keyPressed(KeyEvent e) 
	{
        int key = e.getKeyCode();
        
        if(isDead==false){
        if (key == KeyEvent.VK_SPACE) {
         CanJump=true;
        }
        if (key == KeyEvent.VK_S)     {
         CanKill=true;
         CanWalk = false;
        }
        if (key == KeyEvent.VK_RIGHT && CanKill==false){
         CanWalk = true;
         Xdirection = 'r';
        }
        if (key == KeyEvent.VK_LEFT && CanKill==false ){
         CanWalk = true;   
         Xdirection = 'l';
        }
    }
    }
    
    public void keyReleased(KeyEvent e) 
	{
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT){
         CanWalk = false;
        }
        if (key == KeyEvent.VK_LEFT){
        CanWalk = false;  
        }
    }


    public void paintComponent(Graphics2D g) 
	{
        if(isDead==false){
    if(CanWalk==true && CanKill==false){
        if(rand%3==0){
        num = (int)(Walkcount%8);
        Walkcount++;
        }
        rand++;
        //g.drawImage(WalkImages[num], x - w/2, y - h/2, null); old
        if(Xdirection=='r')
        g.drawImage(WalkImages[num], x + w, y, w, h, null); 
        else
        g.drawImage(WalkImages[num], x + w+150, y, -w, h, null);
    }
    if(CanWalk==false && CanKill==true){
        if(rand%4==0){
            num = (int)(Strikecount%12);
            Strikecount++;
            }
            rand++;
            if(Xdirection=='r')
            g.drawImage(StrikeImages[num], x + w, y, w, h, null);  
            else
            g.drawImage(StrikeImages[num], x + w+150, y, -w, h, null);  
            if(num==4)
            PiercedEnemy=true;
            if(num==11){
            CanKill=false;
            PiercedEnemy=false;
            num=0;
            }
    }
    if(CanWalk==false && CanKill==false){
        if(rand%3==0){
            num = (int)(Standcount%8);
            Standcount++;
            }
            rand++;
            if(Xdirection=='r')
            g.drawImage(StandImages[num], x + w, y, w, h, null);  // new
            else
            g.drawImage(StandImages[num], x + w+150, y, -w, h, null);

       // g.setColor(Color.red);
       // g.drawRect(x + w + 20, y+30, w-50, h-20);
    }
}
    if(isDead==true){
        if(rand%3==0){
            //if(Diecount<8){
            num = (int)(Diecount%8);
            //System.out.println(Diecount + " " + num);
            Diecount++;
            }
            //}
            rand++;
            if(Xdirection=='r')
            g.drawImage(DieImages[num], x + w, y, w, h, null);  // new
            else
            g.drawImage(DieImages[num], x + w+150, y, -w, h, null);   

            if(num==7){
             EndGame=true;
           }
    }
	}
    public void setX(int x){
    this.x = x;
    }
    public int getX(){
    return x;
    }
    public int getY(){
        return y;
    }

       public Rectangle getBounds() {
	        return new Rectangle(x + w + 20, y+30, w-50, h-20);
	}
    public void setJumpstate(boolean state){
        CanJump=state;
    }
    
}