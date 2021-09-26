class Wolf extends Animal implements Comparable<Wolf> {
    final int DAMAGE = 10;

    Wolf(int health, Gender gender) {
        super(health, gender);
    }

    /**
     * Wolf that loses comparison takes damage. Both take damage if wolves compare equal.
     * @param other
     */
    public void attack(Wolf other) {
        int diff = this.compareTo(other);
        // both take damage if diff == 0
        if (diff >= 0) {
            other.takeDamage();
        }
        if (diff <= 0) {
            this.takeDamage();
        }
    }

    public void takeDamage() {
        this.setHealth(this.getHealth() - DAMAGE);
    }

    /**
     * Compares health of two wolves
     * @param enemy
     * @return one of -1, 0, 1
     */
    @Override
    public int compareTo(Wolf enemy) {
        if (this.getHealth() == enemy.getHealth()) {
            return 0;
        }
        return this.getHealth() > enemy.getHealth() ? 1 : -1;
    }
}
