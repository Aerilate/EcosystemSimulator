/* Wolf.java
 * Wolf Object
 * Eats sheep, tramples plants and fights other wolves
 * May 7, 2018
 * Raymond Wang
 */

/**
 * Wolf
 * The Wolf class extends the Animal class and implements the comparable interface
 */
class Wolf extends Animal implements Comparable<Wolf>{
  Wolf(int health, int gender, boolean alreadyMoved){
    super(health, gender, alreadyMoved);
  }
  
  /**
   * attack
   * Uses the compareTo method and 
   * @param 2D array representing the map, the y and x coordinates of the selected wolf, the y and x coordinates of the enemy wolf
   * @return nothing
   */
  public static void attack(Organism[][] map, int wolfY, int wolfX,int enemyY, int enemyX){
    if ( (((Wolf)map[wolfY][wolfX]).compareTo((Wolf)map[enemyY][enemyX])) == -1 ){
      map[wolfY][wolfX].setHealth( map[wolfY][wolfX].getHealth()-10 );
    } else if ( (((Wolf)map[wolfY][wolfX]).compareTo((Wolf)map[enemyY][enemyX])) == 1){
      map[enemyY][enemyX].setHealth( map[wolfY][wolfX].getHealth()-10 );
    }
  }
  
  /**
   * compareTo
   * Returns a value depending on which wolf has more health
   * @param an object representing a wolf
   * @return -1 if the selected wolf is losing, 0 if tie, and 1 if selected wolf is winning
   */
  public int compareTo(Wolf enemy){
    if ( this.getHealth() < enemy.getHealth() ){
      return -1;
    } else if ( this.getHealth() > enemy.getHealth() ){
      return 1;
    }
    else{
      return 0;
    }
  }
}

