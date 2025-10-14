package fr.cyu.jee.beans;

public enum Sex {
    MALE("Homme"), FEMALE("Femme"), OTHER("Autre");

    private String name;

    Sex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
