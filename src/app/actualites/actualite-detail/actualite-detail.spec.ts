import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualiteDetail } from './actualite-detail';

describe('ActualiteDetail', () => {
  let component: ActualiteDetail;
  let fixture: ComponentFixture<ActualiteDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualiteDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualiteDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
