import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

public class BTD extends Canvas implements Runnable {

    int width = 16 * 100;
    int height = 16 * 54;
    BufferStrategy bs;
    Thread thread;
    boolean running = false;
    int x1, y1;

    int enemy2width = 65;
    int enemy2y = 16*6;
    int enemy2x = 0;

    public BTD() {
        setSize(width, height);
        JFrame frame = new JFrame("BTD");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.addMouseListener(new ML());
        this.addKeyListener(new KL());
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        double ns = 1000000000.0 / 30.0;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }

    private void update() {
    }

    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        draw(g);
        g.dispose();
        bs.show();
    }

    public void draw(Graphics g) {
        drawmap(g);
        drawtower1(g);
        drawtower2(g);
        drawtower3(g);
        drawenemy1(g);
        drawenemy2(g);

    }

    private void drawmap(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 100 * 16, 54 * 16);
        g.setColor(Color.GRAY);
        g.fillRect(0, 16 * 6, 16 * 16, 16 * 6);
        g.fillRect(16 * 16, 16 * 6, 16 * 6, 16 * 40);
        g.fillRect(16 * 16, 16 * 46, 16 * 22, 16 * 6);
        g.fillRect(16 * 38, 16 * 26, 16 * 6, 16 * 26);
        g.fillRect(16 * 38, 16 * 20, 16 * 62, 16 * 6);
        g.setColor(Color.WHITE);
        g.fillRect(60*16, 16*35,16*20,16*7);
        g.setFont(new Font("PERMANENT MARKER", Font.ITALIC, 16*5));
        g.setColor(Color.BLACK);
        g.drawString("START", 61*16, 16*40);

    }

    private void drawtower1(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(500, 100, 55, 75);
        int x = 500;
        for (int i = 0; i < 3; i++) {
            g.fillRect(x, 92, 15, 15);
            x = x + 20;
        }
    }

    private void drawtower2(Graphics g) {
        g.setColor(new Color(101, 67, 33));
        g.fillRect(800, 100, 10, 80);
        g.fillRect(810, 110, 10, 4);
    }

    private void drawtower3(Graphics g) {
        g.setColor(Color.ORANGE);
        int x[] = {1125, 1150, 1175};
        int y[] = {150, 100, 150};
        g.fillPolygon(x, y, 3);
        g.fillOval(1140, 90, 20, 20);
    }

    private void drawenemy1(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(400, 500, 60, 80);
    }

    private void drawenemy2(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(enemy2x, enemy2y, enemy2width, 85);
    }

    public static void main(String[] args) {
        BTD minGrafik = new BTD();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                minGrafik.setVisible(true);
            }
        });
        minGrafik.start();
    }

    private class ML implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            x1 = mouseEvent.getX();
            y1 = mouseEvent.getY();
            System.out.println(x1 + "," + y1);
            if (x1 >= 16*60 && x1 <= 16*80 && y1 >= 16*35 && y1 <= 16*42) {
                for (int i = 0; i < 16*16; i++) {
                    enemy2x++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16*40; i++) {
                    enemy2y++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16*22; i++) {
                    enemy2x++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16*26; i++) {
                    enemy2y--;
                    try {
                        TimeUnit.MILLISECONDS.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16*62; i++) {
                    enemy2x++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    public class KL implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == '2') {
                System.out.println("Hallko");
                System.exit(1);
            }
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}