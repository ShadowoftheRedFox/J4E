package fr.cyu.jee.beans.enums;

public enum Permission {
    EDIT_USER("Can edit user"),
    CREATE_USER("Can create user"),
    DELETE_USER("Can delete user"),

    EDIT_PROJECT("Can edit project"),
    CREATE_PROJECT("Can create project"),
    DELETE_PROJECT("Can delete project"),

    EDIT_DEPARTMENT("Can edit department"),
    CREATE_DEPARTMENT("Can create department"),
    DELETE_DEPARTMENT("Can delete department"),

    EDIT_RANK("Can edit rank"),
    CREATE_RANK("Can create rank"),
    DELETE_RANK("Can delete rank"),

    EDIT_PERMISSION("Can edit permission"),
    CREATE_PERMISSION("Can create permission"),
    DELETE_PERMISSION("Can delete permission"),

    VIEW_REPORT("Can generate report"),
    VIEW_PAYSLIP("Can generate payslips"),
    ;

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
