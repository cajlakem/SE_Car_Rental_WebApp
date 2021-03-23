import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicepointComponent } from './servicepoint.component';

describe('ServicepointComponent', () => {
  let component: ServicepointComponent;
  let fixture: ComponentFixture<ServicepointComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServicepointComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicepointComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
