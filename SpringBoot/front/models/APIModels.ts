
export const EmployeeRole = [
    "Administrateur"
] as const;

export type EmployeeRole = typeof EmployeeRole[number];

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
    "VIEW_PAYSLIP",

    "NONE"
] as const;

export type EmployeePermission = typeof EmployeePermission[number];

export interface Employee {
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    department: number,
    role: EmployeeRole,
    permissions: EmployeePermission[],
    password?: string
}

export interface Department {
    id: number,
    name: string,
    employees: Employee[]
}

export const ProjectStatus = [
    "ONGOING",
    "FINISHED",
    "CANCELED"
];

export type ProjectStatus = typeof ProjectStatus[number];

export interface Project {
    id: number,
    name: string,
    status: /*keyof typeof*/ ProjectStatus
    employees: Employee[]
}

export interface Payslip {
    id: number,
    employee: Employee
}