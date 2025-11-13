import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualiteForm } from './actualite-form';

describe('ActualiteForm', () => {
  let component: ActualiteForm;
  let fixture: ComponentFixture<ActualiteForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualiteForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualiteForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
