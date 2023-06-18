import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

class PhysicsObject {
    double x, y; // position
    double vx, vy; // velocity
    double ax, ay; // acceleration

    public PhysicsObject(double x, double y, double vx, double vy, double ax, double ay) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
    }

    public void update() {
        vx += ax;
        vy += ay;
        x += vx;
        y += vy;

        // if object hits a wall, reverse velocity
        if (x < 10 || x > 770) {
            vx = -vx;
        }
        if (y < 10 || y > 570) {
            vy = -vy;
        }
    }

    public int getIntX() {
        return (int) x;
    }

    public int getIntY() {
        return (int) y;
    }
}

class PhysicsPanel extends JPanel {
    ArrayList<PhysicsObject> objects;

    public PhysicsPanel(ArrayList<PhysicsObject> objects) {
        this.objects = objects;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the bounding box
        g.drawRect(10, 10, 760, 560);
        // Draw the physics objects
        for (PhysicsObject object : objects) {
            g.fillOval(object.getIntX() - 10, object.getIntY() - 10, 20, 20);
        }
    }
}

public class PhysicsSimulator {
    // The model
    static ArrayList<PhysicsObject> objects = new ArrayList<>();

    // The view
    static PhysicsPanel panel = new PhysicsPanel(objects);

    public static void main(String[] args) {
        Random rand = new Random();

        // Create a bunch of objects with random initial conditions
        for (int i = 0; i < 10; i++) {
            double x = rand.nextDouble() * 760 + 10;
            double y = rand.nextDouble() * 560 + 10;
            double vx = rand.nextDouble() * 4 - 2;
            double vy = rand.nextDouble() * 4 - 2;
            PhysicsObject object = new PhysicsObject(x, y, vx, vy, 0, 0.1);
            objects.add(object);
        }

        JFrame frame = new JFrame("Physics Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // The physics loop
        int delay = 10; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                for (PhysicsObject object : objects) {
                    object.update();
                }
                panel.repaint();
            }
        };
        new Timer(delay, taskPerformer).start();
    }
}
