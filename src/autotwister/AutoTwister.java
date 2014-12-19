/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotwister;

import java.awt.*;
import javax.swing.JFrame;

/**
 *
 * @author timur
 */
public class AutoTwister {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setVisible(true);
            }
        });
    }
    
}
