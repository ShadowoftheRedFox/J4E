package fr.cyu.jee.beans.enums;

public enum Permission {
    THING1("Thing1"),
    THING2("Thing2"),
    THING3("Thing3");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
