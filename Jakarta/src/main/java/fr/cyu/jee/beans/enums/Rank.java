package fr.cyu.jee.beans.enums;

public enum Rank {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    PROJECT_LEADER("Project leader"),
    DEPARTMENT_LEADER("Department leader");

    private String rank;

    Rank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }
}
