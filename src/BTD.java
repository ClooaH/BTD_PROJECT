import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class BTD extends Canvas implements Runnable {

    int width = 16*100;
    int height = 16*54;
    BufferStrategy bs;
    Thread thread;
    boolean running = false;
    int x1,y1;

    private BTD() {
        setSize(width, height);
        JFrame frame = new JFrame("BTD");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.addMouseListener(new ML());
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
//        repaint();
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
        g.fillRect(0, 0, 1600, 900);
        g.setColor(Color.GRAY);
        g.fillRect(0, 100, 250, 80);
        g.fillRect(250, 100, 80, 600);
        g.fillRect(250, 700, 350, 80);
        g.fillRect(600, 450, 80, 330);
        g.fillRect(600, 370, 1000, 80);
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
        g.fillOval(700, 500, 65, 85);
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
            x1 = mouseEvent.getX();
            y1 = mouseEvent.getY();
            System.out.println(x1 + " , " + y1);
        }

        @Override
        public void mousePressed(MouseEvent e) {

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
}