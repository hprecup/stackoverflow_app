import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BannedUserDialogComponent } from './banned-user-dialog.component';

describe('BannedUserDialogComponent', () => {
  let component: BannedUserDialogComponent;
  let fixture: ComponentFixture<BannedUserDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BannedUserDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BannedUserDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
