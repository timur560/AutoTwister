/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotwister;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author timur
 */
class QuitMenuItemListener implements ActionListener {

    public QuitMenuItemListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
}
