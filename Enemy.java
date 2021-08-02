/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jump;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy
{
    private int x,y,w,h, EnemySpeed;
    protected boolean CanKill;
    protected boolean PiercedPlayer;
    protected boolean RemoveEnemy;
    protected boolean isDead;
    protected Image[] WalkImages;
    protected Image[] StrikeImages;
    protected Image[] DieImages;
    protected long Walkcount = 0;
    protected long Strikecount = 0;
    protected long Diecount = 0;
    protected int rand = 0;
    protected char Xdirection;
    int num=0;


	public Enemy(int x, int y, char direction, int EnemySpeed)
	{
	this.x = x;
    this.y = y;	
    this.EnemySpeed=EnemySpeed;
    Xdirection = direction;
    CanKill=false;
    isDead=false;
    RemoveEnemy=false;
    WalkImages = new Image[8];
    StrikeImages = new Image[12];
    DieImages = new Image[8];
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/enemywalk/left/enemywalk" + i + ".png" );
		WalkImages[i] = imageIcon.getImage();
		}		
    for(int i=0; i<12; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/enemyattack/left/enemyattack" + i + ".png" );
		StrikeImages[i] = imageIcon.getImage();
		}
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/enemydead/knightdead" + i + ".png" );
		DieImages[i] = imageIcon.getImage();
		}		
		w = StrikeImages[5].getWidth(null);
        h = StrikeImages[5].getHeight(null);   
	}
	
    public void move()
    {
        if(isDead==false){
        if(CanKill==false){
            if(Xdirection=='l')
            x-=EnemySpeed;
            else
            x+=EnemySpeed;
        }
    }
    }

    public void paintComponent(Graphics2D g) 
	{
        if(isDead==false){
    if(CanKill==false){
        if(rand%3==0){
        num = (int)(Walkcount%8);
        Walkcount++;
        }
        rand++;
        if(Xdirection=='l')
        g.drawImage(WalkImages[num], x - w/2, y-h/2, w, h, null);
        else
        g.drawImage(WalkImages[num], x+150 - w/2, y-h/2, -w, h, null);

        //to show image bounds (remove later)
		//g.setColor(Color.blue);
        //g.drawRect(x+40 - w/2, y-h/2 + 70, w-60, h-70);
    }
    if(CanKill==true){
        if(rand%4==0){
            num = (int)(Strikecount%12);
            Strikecount++;
            }
            rand++;
            if(Xdirection=='l')
            g.drawImage(StrikeImages[num], x - w/2, y-h/2, w, h, null);  
            else
            g.drawImage(StrikeImages[num], x+150 - w/2, y-h/2, -w, h, null);
            if(num==4)
            PiercedPlayer=true;
            if(num==11){
            CanKill=false;
            PiercedPlayer=false;
            num=0;
            }
        }
    }
        if(isDead==true){
                if(rand%2==0){
                    if(Diecount<8){
                        num = (int)(Diecount%8);
                        Diecount++;
                        }
                    }
                    rand++;
                    if(Xdirection=='r')
                    g.drawImage(DieImages[num],x - w/2, y-h/2, w, h, null);  // new
                    else
                    g.drawImage(DieImages[num], x+150 - w/2, y-h/2, -w, h, null);   

                    if(num==7){
                     RemoveEnemy=true;
                    }
            }
        }

    public void die(boolean state)
    {
     if(state==true){
     isDead=true;
     }   
     else
     isDead=false;
    }

    public int getX(){
    return x;
    }
    public int getY(){
        return y;
    }

       public Rectangle getBounds() {
	    return new Rectangle(x+40 - w/2, y-h/2 + 70, w-60, h-70);
	}
    public void SetCanKill(boolean val){
     CanKill = val;
    }
    
}
