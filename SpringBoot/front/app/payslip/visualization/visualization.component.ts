import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { provideNativeDateAdapter } from '@angular/material/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Employee, Payslip } from '../../../models/APIModels';
import { ApiService } from '../../../services/api.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from "@angular/material/datepicker"
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-visualization',
    imports: [
        RouterLink,
        MatFormFieldModule,
        MatDatepickerModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
    ],
    templateUrl: './visualization.component.html',
    styleUrl: './visualization.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [provideNativeDateAdapter()],
})
export class VisualizationComponent {
    private readonly api = inject(ApiService);
    private readonly route = inject(ActivatedRoute);

    employee_id = 0;
    employee: Employee | null = null;
    allPayslips: Payslip[] = [];
    filteredPayslips: Payslip[] = [];

    constructor() {
        this.route.paramMap.subscribe(res => {
            this.employee_id = Number(res.get("id"));
            if (!this.isNaN(this.employee_id)) {
                this.api.employee.get(this.employee_id).subscribe({
                    next: (res) => {
                        this.employee = res;
                    },
                    error: () => {
                        this.employee_id = NaN;
                    }
                })
            }
        });

        this.api.payslip.getAllOfEmployee(this.employee_id).subscribe({ next: (res) => { this.allPayslips = res; } });

        this.range.valueChanges.subscribe(res => {
            if (res.end == null || res.start == null) { return; }
            this.updateSelection(res.start, res.end);
        })
    }

    isNaN(n: number) {
        return isNaN(n);
    }

    updateSelection(start: Date, end: Date) {
        this.filteredPayslips = [];
        this.allPayslips.forEach(p => {
            if (p.date.getTime() >= start.getTime() && p.date.getTime() <= end.getTime()) {
                this.filteredPayslips.push(p);
            }
        });
    }

    readonly range = new FormGroup({
        start: new FormControl<Date | null>(null),
        end: new FormControl<Date | null>(null),
    });

    add() {
        //TODO
    }
}
