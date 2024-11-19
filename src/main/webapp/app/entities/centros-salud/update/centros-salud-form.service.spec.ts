import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../centros-salud.test-samples';

import { CentrosSaludFormService } from './centros-salud-form.service';

describe('CentrosSalud Form Service', () => {
  let service: CentrosSaludFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentrosSaludFormService);
  });

  describe('Service methods', () => {
    describe('createCentrosSaludFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCentrosSaludFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            centroSaludID: expect.any(Object),
            nombre: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            vigente: expect.any(Object),
          }),
        );
      });

      it('passing ICentrosSalud should create a new form with FormGroup', () => {
        const formGroup = service.createCentrosSaludFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            centroSaludID: expect.any(Object),
            nombre: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            vigente: expect.any(Object),
          }),
        );
      });
    });

    describe('getCentrosSalud', () => {
      it('should return NewCentrosSalud for default CentrosSalud initial value', () => {
        const formGroup = service.createCentrosSaludFormGroup(sampleWithNewData);

        const centrosSalud = service.getCentrosSalud(formGroup) as any;

        expect(centrosSalud).toMatchObject(sampleWithNewData);
      });

      it('should return NewCentrosSalud for empty CentrosSalud initial value', () => {
        const formGroup = service.createCentrosSaludFormGroup();

        const centrosSalud = service.getCentrosSalud(formGroup) as any;

        expect(centrosSalud).toMatchObject({});
      });

      it('should return ICentrosSalud', () => {
        const formGroup = service.createCentrosSaludFormGroup(sampleWithRequiredData);

        const centrosSalud = service.getCentrosSalud(formGroup) as any;

        expect(centrosSalud).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICentrosSalud should not enable id FormControl', () => {
        const formGroup = service.createCentrosSaludFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCentrosSalud should disable id FormControl', () => {
        const formGroup = service.createCentrosSaludFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
