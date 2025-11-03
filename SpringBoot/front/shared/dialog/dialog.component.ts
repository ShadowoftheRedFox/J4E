import { Component, inject, Type } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButton } from '@angular/material/button';
import { NgComponentOutlet, NgStyle } from '@angular/common';
import { EmployeeFormComponent } from '../../app/employee/employee-form/employee-form.component';

export interface DialogDataType {
    btnOk?: string;
    btnNotOk?: string;
    title?: string;
    text?: string;
    component?: Type<unknown>;
    warn?: boolean;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    data?: any;
    text_style?: string;
};

@Component({
    selector: 'app-dialog',
    standalone: true,
    imports: [
        MatDialogModule,
        NgComponentOutlet,
        NgStyle,
        MatButton,
        EmployeeFormComponent
    ],
    templateUrl: './dialog.component.html',
    styleUrl: './dialog.component.scss'
})
export class DialogComponent {
    readonly data = inject<DialogDataType>(MAT_DIALOG_DATA);
    private dialogRef = inject(MatDialogRef<DialogComponent, unknown>);

    readonly EmployeeForm = EmployeeFormComponent;

    constructor() {
        if (!this.data) this.data = {};
        if (this.data.btnNotOk === undefined) this.data.btnNotOk = "Fermer";
        if (!this.data.title) this.data.title = "Dialog";
        if (!this.data.text_style) this.data.text_style = "";
    }

    output: unknown = undefined;

    getOuput(o: unknown, isValidating = false) {
        this.output = o;

        if (isValidating) {
            this.validate();
        }
    }

    validate() {
        if (this.output == undefined) {
            this.dialogRef.close(0);
        } else {
            this.dialogRef.close(this.output);
        }
    }
}