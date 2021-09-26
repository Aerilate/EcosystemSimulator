import javax.swing.*;
import java.awt.*;

class DisplayGrid {
    private JFrame frame;
    private int maxX, maxY, GridToScreenRatio;
    private Organism[][] world;

    DisplayGrid(Organism[][] w) {
        this.world = w;

        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / (world.length + 1);  //ratio to fit in screen as square map

        this.frame = new JFrame("EcoSim5000: Map of World");
        GridAreaPanel worldPanel = new GridAreaPanel();
        frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }

    public void refresh() {
        frame.repaint();
    }

    class GridAreaPanel extends JPanel {
        public void paintComponent(Graphics g) {
            Image plant = Toolkit.getDefaultToolkit().getImage("src/res/plant.png");
            Image sheep = Toolkit.getDefaultToolkit().getImage("src/res/sheep.png");
            Image wolf = Toolkit.getDefaultToolkit().getImage("src/res/wolf.png");

            setDoubleBuffered(true);
            g.setColor(Color.BLACK);

            for (int i = 0; i < world[0].length; i = i + 1) {
                for (int j = 0; j < world.length; j = j + 1) {
                    g.setColor(new java.awt.Color(144, 238, 144)); //Light green colour
                    g.fillRect(j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);

                    //Draws the organisms
                    if (world[i][j] instanceof Plant) {
                        g.drawImage(plant, j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio, this);
                    } else if (world[i][j] instanceof Sheep) {
                        g.drawImage(sheep, j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio, this);
                    } else if (world[i][j] instanceof Wolf) {
                        g.drawImage(wolf, j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio, this);
                    }
                }
            }
        }
    }
}
