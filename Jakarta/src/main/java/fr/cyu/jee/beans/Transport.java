package fr.cyu.jee.beans;

public enum Transport {
    CAR("Voiture"), PLANE("Plane"), TRUCK("Camion");

    private String name;

    Transport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
