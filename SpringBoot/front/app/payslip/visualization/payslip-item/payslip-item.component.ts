import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Payslip } from '../../../../models/APIModels';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from "@angular/material/form-field";
import { toDate } from '../../../../models/date';

@Component({
    selector: 'app-payslip-item',
    imports: [
        MatIconModule,
        MatButtonModule,
        MatFormFieldModule
    ],
    templateUrl: './payslip-item.component.html',
    styleUrl: './payslip-item.component.scss'
})
export class PayslipItemComponent {
    @Input({ required: true }) payslip!: Payslip;
    @Output() edit = new EventEmitter<number>();
    @Output() delete = new EventEmitter<number>();
    @Output() print = new EventEmitter<Payslip>();

    formatDate(date: number) {
        return toDate(new Date(date).toDateString());
    }
}
