import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectEmployeesComponent } from './select-employees.component';

describe('SelectEmployeesComponent', () => {
  let component: SelectEmployeesComponent;
  let fixture: ComponentFixture<SelectEmployeesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectEmployeesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectEmployeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
