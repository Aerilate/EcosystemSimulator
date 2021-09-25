class Wolf extends Animal implements Comparable<Wolf> {
    Wolf(int health, Gender gender, boolean alreadyMoved) {
        super(health, gender, alreadyMoved);
    }

    public static void attack(Organism[][] map, int wolfY, int wolfX, int enemyY, int enemyX) {
        if ((((Wolf) map[wolfY][wolfX]).compareTo((Wolf) map[enemyY][enemyX])) == -1) {
            map[wolfY][wolfX].setHealth(map[wolfY][wolfX].getHealth() - 10);
        } else if ((((Wolf) map[wolfY][wolfX]).compareTo((Wolf) map[enemyY][enemyX])) == 1) {
            map[enemyY][enemyX].setHealth(map[wolfY][wolfX].getHealth() - 10);
        }
    }

    public int compareTo(Wolf enemy) {
        if (this.getHealth() == enemy.getHealth()) {
            return 0;
        }
        return this.getHealth() > enemy.getHealth() ? 1 : -1;
    }
}

