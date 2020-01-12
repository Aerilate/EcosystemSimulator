/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * May 7, 2018
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import javax.imageio.*; 

/**
 * DisplayGrid
 * This class holds the methods that display the map window
 */
class DisplayGrid {
  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Organism[][] world;
  
  /**
   * DisplayGrid
   * Creates the window and the border
   * @param Organism[][] w
   * @return nothing
   */
  DisplayGrid(Organism[][] w) { 
    this.world = w;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("EcoSim5000: Map of World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  /**
   * refresh
   * Refreshes the window
   * @param nothing
   * @return nothing
   */
  public void refresh() { 
    frame.repaint();
  }
  
  /**
   * GridAreaPanel
   * Holds the paintComponent method that initialises the images and displays the sprites of the organisms in the world
   * @param nothing
   * @return nothing
   */
  class GridAreaPanel extends JPanel {
    
    /**
     * paintComponent
     * This class initialises the images and displays the sprites of the organisms in the world
     * @param Graphics g
     * @return nothing
     */
    public void paintComponent(Graphics g) {  
      
      Image plant = Toolkit.getDefaultToolkit().getImage("plant.png");
      Image sheep= Toolkit.getDefaultToolkit().getImage("sheep.png");
      Image wolf = Toolkit.getDefaultToolkit().getImage("wolf.png");
      
      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      
      for(int i = 0; i<world[0].length;i=i+1){ 
        for(int j = 0; j<world.length;j=j+1) { 
          g.setColor(new java.awt.Color(144, 238, 144)); //Light green colour
          g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          
          //Draws the organisms
          if (world[i][j] instanceof Plant){
            g.drawImage(plant,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          } else if (world[i][j] instanceof Sheep){
            g.drawImage(sheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          } else if (world[i][j] instanceof Wolf){
            g.drawImage(wolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          }
        }
      }
    }
  }//end of GridAreaPanel
} //end of DisplayGrid

