export enum EmployeeRole { ADMIN }
export enum Permission { }

export interface Employee {
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    department: number,
    role: EmployeeRole,
    permissions: Permission[]
}

export interface Department {
    id: number,
    name: string,
    employees: Employee[]
}

export enum ProjectStatus { ONGOING, FINISHED, CANCELED }

export interface Projects {
    id: number,
    name: string,
    status: /*keyof typeof*/ ProjectStatus
    employees: Employee[]
}

export interface Payslip {
    id: number,
    employee: Employee
}