class Wolf extends Animal implements Comparable<Wolf> {
    final int DAMAGE = 10;

    Wolf(int health, Gender gender, boolean alreadyMoved) {
        super(health, gender, alreadyMoved);
    }

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

    @Override
    public int compareTo(Wolf enemy) {
        if (this.getHealth() == enemy.getHealth()) {
            return 0;
        }
        return this.getHealth() > enemy.getHealth() ? 1 : -1;
    }
}

