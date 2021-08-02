/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jump;

import java.awt.EventQueue;
import javax.swing.JFrame;
    
public class Application extends JFrame {
        
    public Application() {
    
        initUI();
    }
    
    private void initUI() {
    
        add(new Board("E:/GamesAndStuff/Jump/background2.png"));

        setSize(1024, 768);
        setResizable(false);
        setTitle("Jump");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        }    
        
    public static void main(String[] args) {
            
        EventQueue.invokeLater(() -> {
        Application ex = new Application();
        ex.setVisible(true);
            });
        }   
    }
