enum OrganismType {
    Plant,
    Sheep,
    Wolf
}

public class OrganismFactory {
    int plantHealth = 10;
    int sheepHealth = 30;
    int wolfHealth = 12;

    /**
     * Creates an organism whose underlying subclass matches the OrganismType.
     * @param organism
     * @return Organism
     */
    Organism createOrganism(OrganismType organism) {
        switch (organism) {
            case Plant:
                return new Plant(plantHealth);
            case Sheep:
                return new Sheep(sheepHealth, generateGender());
            case Wolf:
                return new Wolf(wolfHealth, generateGender());
        }
        return null;
    }

    /**
     * Randomly returns male or female with equal probability.
     * @return Gender.Male or Gender.Female
     */
    Gender generateGender() {
        return Math.random() < 0.5 ? Gender.Male : Gender.Female;
    }
}


