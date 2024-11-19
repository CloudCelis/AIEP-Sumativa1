import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../pacientes.test-samples';

import { PacientesFormService } from './pacientes-form.service';

describe('Pacientes Form Service', () => {
  let service: PacientesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PacientesFormService);
  });

  describe('Service methods', () => {
    describe('createPacientesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPacientesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacienteId: expect.any(Object),
            nombre: expect.any(Object),
            apellidoPaterno: expect.any(Object),
            apellidoMaterno: expect.any(Object),
            rut: expect.any(Object),
            fechaNacimiento: expect.any(Object),
            genero: expect.any(Object),
            telefono: expect.any(Object),
            email: expect.any(Object),
            direccion: expect.any(Object),
          }),
        );
      });

      it('passing IPacientes should create a new form with FormGroup', () => {
        const formGroup = service.createPacientesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pacienteId: expect.any(Object),
            nombre: expect.any(Object),
            apellidoPaterno: expect.any(Object),
            apellidoMaterno: expect.any(Object),
            rut: expect.any(Object),
            fechaNacimiento: expect.any(Object),
            genero: expect.any(Object),
            telefono: expect.any(Object),
            email: expect.any(Object),
            direccion: expect.any(Object),
          }),
        );
      });
    });

    describe('getPacientes', () => {
      it('should return NewPacientes for default Pacientes initial value', () => {
        const formGroup = service.createPacientesFormGroup(sampleWithNewData);

        const pacientes = service.getPacientes(formGroup) as any;

        expect(pacientes).toMatchObject(sampleWithNewData);
      });

      it('should return NewPacientes for empty Pacientes initial value', () => {
        const formGroup = service.createPacientesFormGroup();

        const pacientes = service.getPacientes(formGroup) as any;

        expect(pacientes).toMatchObject({});
      });

      it('should return IPacientes', () => {
        const formGroup = service.createPacientesFormGroup(sampleWithRequiredData);

        const pacientes = service.getPacientes(formGroup) as any;

        expect(pacientes).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPacientes should not enable id FormControl', () => {
        const formGroup = service.createPacientesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPacientes should disable id FormControl', () => {
        const formGroup = service.createPacientesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
