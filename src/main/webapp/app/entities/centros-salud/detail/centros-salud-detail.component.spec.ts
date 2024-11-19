import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CentrosSaludDetailComponent } from './centros-salud-detail.component';

describe('CentrosSalud Management Detail Component', () => {
  let comp: CentrosSaludDetailComponent;
  let fixture: ComponentFixture<CentrosSaludDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CentrosSaludDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./centros-salud-detail.component').then(m => m.CentrosSaludDetailComponent),
              resolve: { centrosSalud: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CentrosSaludDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentrosSaludDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load centrosSalud on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CentrosSaludDetailComponent);

      // THEN
      expect(instance.centrosSalud()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
