export interface BaseResponse {
    status: number,
    error?: string,
    message?: string
}

export const EmployeeRank = [
    "ADMIN",
    "MANAGER",
    "PROJECT_LEADER",
    "DEPARTMENT_LEADER",
    "SENIOR",
    "JUNIOR",
] as const;

export type EmployeeRank = typeof EmployeeRank[number];

export const EmployeePermission = [
    "EDIT_USER",
    "CREATE_USER",
    "DELETE_USER",
    "EDIT_PROJECT",
    "CREATE_PROJECT",
    "DELETE_PROJECT",
    "EDIT_DEPARTMENT",
    "CREATE_DEPARTMENT",
    "DELETE_DEPARTMENT",
    "EDIT_RANK",
    "CREATE_RANK",
    "DELETE_RANK",
    "EDIT_PERMISSION",
    "CREATE_PERMISSION",
    "DELETE_PERMISSION",
    "VIEW_REPORT",
    "VIEW_PAYSLIP"
] as const;

export type EmployeePermission = typeof EmployeePermission[number];

export interface Employee {
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    department: number,
    ranks: EmployeeRank[],
    permissions: EmployeePermission[],
    password?: string
}

export interface Department {
    id: number,
    name: string,
    employees: number[]
}

export const ProjectStatus = [
    "ONGOING",
    "FINISHED",
    "CANCELED"
] as const;

export type ProjectStatus = typeof ProjectStatus[number];

export interface Project {
    id: number,
    name: string,
    status: ProjectStatus
    employees: Employee[]
}

export interface Payslip {
    id: number,
    employee: number,
    hour: number,
    wage: number,
    bonus: number,
    malus: number,
    date: number,
}