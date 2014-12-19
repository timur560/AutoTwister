/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotwister;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author timur
 */
public class MainFrame extends JFrame
{
    private final int 
            _width = 800, 
            _height = 600;

    public JTextField timeoutField;
    public JButton startButton;
    public JButton stopButton;
    public JButton nextButton;
    public InfoArea infoArea;

    // menubar
    public JMenuBar menuBar;
    public JMenu menuFile, menuActions, menuAbout;
    public JMenuItem
            quitMenuItem,
            startMenuItem,
            stopMenuItem,
            nextMenuItem,
            aboutMenuItem;
    
    public MainFrame()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();

        setSize(this._width, this._height);
        setTitle("Automatic Twister Control");
        this.setLocation(screen.width / 2 - this._width / 2, screen.height / 2 - this._height / 2);
        setResizable(false);
        
        Image img = new ImageIcon("icon.png").getImage();
        setIconImage(img);
        
        _initGUI();
    }
    
    private void _initGUI()
    {
        // actions
        ActionListener start = new StartButtonPressListener();
        ActionListener stop = new StopButtonPressListener();
        ActionListener next = new NextButtonPressListener();
        ActionListener quit = new QuitMenuItemListener();
        ActionListener about = new AboutMenuItemListener();

        // init
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuActions = new JMenu("Actions");
        menuAbout = new JMenu("About");
        quitMenuItem = new JMenuItem("Quit");
        startMenuItem = new JMenuItem("Start");
        stopMenuItem = new JMenuItem("Stop");
        nextMenuItem = new JMenuItem("Next Turn");
        aboutMenuItem = new JMenuItem("About");
        
        timeoutField = new JTextField("30", 5);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        nextButton = new JButton("Next Turn");
        infoArea = new InfoArea();

        // action listeners
        startButton.addActionListener(start);
        stopButton.addActionListener(stop);
        nextButton.addActionListener(next);
        quitMenuItem.addActionListener(quit);
        startMenuItem.addActionListener(start);
        stopMenuItem.addActionListener(stop);
        nextMenuItem.addActionListener(next);
        aboutMenuItem.addActionListener(about);
        
        // compose
        JPanel topPanel = new JPanel();

        menuFile.add(quitMenuItem);
        menuActions.add(startMenuItem);
        menuActions.add(stopMenuItem);
        menuActions.add(nextMenuItem);
        menuAbout.add(aboutMenuItem);
        menuBar.add(menuFile);
        menuBar.add(menuActions);
        menuBar.add(menuAbout);
        
        topPanel.add(new JLabel("Timeout:"));
        topPanel.add(timeoutField);
        topPanel.add(new JLabel("sec."));
        topPanel.add(startButton);
        topPanel.add(stopButton);
        topPanel.add(nextButton);
        
        setJMenuBar(menuBar);
        add(topPanel, BorderLayout.NORTH);
        add(infoArea, BorderLayout.CENTER);
    }
    
    private class StartButtonPressListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                infoArea.start(Integer.parseInt(timeoutField.getText()));
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class StopButtonPressListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            infoArea.stop();
        }
    }

    private class NextButtonPressListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            infoArea.forcedNext();
        }
    }

    private class AboutMenuItemListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Program by Tim\n(c)2014", "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
