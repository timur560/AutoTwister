/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotwister;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.*;

/**
 *
 * @author timur
 */
public class InfoArea extends JComponent {
    public static final int NONE = 0;
    
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    public static final int HAND = 1;
    public static final int LEG = 2;

    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int YELLOW = 3;
    public static final int BLUE = 4;

    public static final int STOPPED = 0;
    public static final int STARTED = 1;
    public static final int FORCED_NEXT = 2;
            
    protected int _side = 0;
    protected int _part = 0;
    protected int _color = 0;
    protected int _timeout = 10;
    protected int _state = 0;
    protected int _currentSec = 0;

    public java.util.Timer timer;
            
    public InfoArea()
    {
        super();
        this._state = InfoArea.STOPPED;
    }
    
    public void update(int side, int part, int color)
    {
        this._side = side;
        this._part = part;
        this._color = color;
        
        this.repaint();
    }

    public void reset()
    {
        this._side = InfoArea.NONE;
        this._part = InfoArea.NONE;
        this._color = InfoArea.NONE;
        
        this._currentSec = 0;
        
        this.repaint();
    }

    public void playSound() throws FileNotFoundException, IOException
    {
        String bellSoundFile = "bell.au";
        InputStream in = new FileInputStream(bellSoundFile);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }
    
    public void update(int sec)
    {
        this._currentSec = sec;
        this.repaint();
    }

    public void start(final int timeout) throws InterruptedException
    {
        _timeout = timeout;
        _state = InfoArea.STARTED;

        timer = new java.util.Timer();
        timer.schedule(new UpdateTask(), 1000); // 1 sec

    }
    
    class UpdateTask extends TimerTask
    {

        @Override
        public void run() {
            Random random = new Random();
            if (_currentSec == 0) {
                update(random.nextInt(2) + 1, random.nextInt(2) + 1, random.nextInt(4) + 1);
                try {
                    playSound();
                } catch (IOException ex) {
                    Logger.getLogger(InfoArea.class.getName()).log(Level.SEVERE, null, ex);
                }
                _currentSec = _timeout;
            } else {
                _currentSec--;
                update(_currentSec);
            }
            
            if (_state == InfoArea.STARTED) {
                timer.schedule(new UpdateTask(), 1000); // 1 sec
            } else if (_state == InfoArea.FORCED_NEXT) {
                _currentSec = _timeout;
                _state = InfoArea.STARTED;
                update(random.nextInt(2) + 1, random.nextInt(2) + 1, random.nextInt(4) + 1);
                try {
                    playSound();
                } catch (IOException ex) {
                    Logger.getLogger(InfoArea.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.schedule(new UpdateTask(), 1000); // 1 sec
            } else if (_state == InfoArea.STOPPED) {
                reset();
            }
        }
        
    }
    
    public void stop()
    {
        this._state = InfoArea.STOPPED;
    }

    public void forcedNext()
    {
        if (this._state == InfoArea.STARTED) {
            this._state = InfoArea.FORCED_NEXT;
        }
    }
            
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Dimension d = this.getSize();
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("Tahome", Font.BOLD, 24));

        g2.drawString(Integer.toString(this._currentSec), 20, 30);

        g2.setFont(new Font("Tahome", Font.BOLD, 40));
        
        String side = "";
        String part = "";
        
        String s;
        
        if (this._side == InfoArea.NONE || this._part == InfoArea.NONE) {
            s = "Set timeout and press Start";
        } else {
            if (this._side == InfoArea.LEFT) {
                side = "Left";
            } else if (this._side == InfoArea.RIGHT) {
                side = "Right";
            }

            if (this._part == InfoArea.HAND) {
                part = "hand";
            } else if (this._part == InfoArea.LEG) {
                part = "leg";
            }

            s = side + " " + part;
        }
        
        int strWidth = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();
        
        g2.drawString(s, d.width - d.width / 2 - (strWidth / 2), 100);
        
        if (this._color == InfoArea.NONE) {
            g2.setPaint(Color.LIGHT_GRAY);
        } else if (this._color == InfoArea.RED) {
            g2.setPaint(Color.RED);
        } else if (this._color == InfoArea.GREEN) {
            g2.setPaint(Color.GREEN);
        } else if (this._color == InfoArea.YELLOW) {
            g2.setPaint(Color.YELLOW);
        } else if (this._color == InfoArea.BLUE) {
            g2.setPaint(Color.BLUE);
        }

        Ellipse2D circle = new Ellipse2D.Double(d.width - d.width / 2 - 100, 250, 200, 200);
        g2.fill(circle);

    }
}
