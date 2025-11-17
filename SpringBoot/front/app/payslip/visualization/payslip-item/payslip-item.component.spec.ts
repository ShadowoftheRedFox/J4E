import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayslipItemComponent } from './payslip-item.component';

describe('PayslipItemComponent', () => {
  let component: PayslipItemComponent;
  let fixture: ComponentFixture<PayslipItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PayslipItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PayslipItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
