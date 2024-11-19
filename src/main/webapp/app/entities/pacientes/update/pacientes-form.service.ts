import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPacientes, NewPacientes } from '../pacientes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPacientes for edit and NewPacientesFormGroupInput for create.
 */
type PacientesFormGroupInput = IPacientes | PartialWithRequiredKeyOf<NewPacientes>;

type PacientesFormDefaults = Pick<NewPacientes, 'id'>;

type PacientesFormGroupContent = {
  id: FormControl<IPacientes['id'] | NewPacientes['id']>;
  pacienteId: FormControl<IPacientes['pacienteId']>;
  nombre: FormControl<IPacientes['nombre']>;
  apellidoPaterno: FormControl<IPacientes['apellidoPaterno']>;
  apellidoMaterno: FormControl<IPacientes['apellidoMaterno']>;
  rut: FormControl<IPacientes['rut']>;
  fechaNacimiento: FormControl<IPacientes['fechaNacimiento']>;
  genero: FormControl<IPacientes['genero']>;
  telefono: FormControl<IPacientes['telefono']>;
  email: FormControl<IPacientes['email']>;
  direccion: FormControl<IPacientes['direccion']>;
};

export type PacientesFormGroup = FormGroup<PacientesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PacientesFormService {
  createPacientesFormGroup(pacientes: PacientesFormGroupInput = { id: null }): PacientesFormGroup {
    const pacientesRawValue = {
      ...this.getFormDefaults(),
      ...pacientes,
    };
    return new FormGroup<PacientesFormGroupContent>({
      id: new FormControl(
        { value: pacientesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      pacienteId: new FormControl(pacientesRawValue.pacienteId, {
        validators: [Validators.required],
      }),
      nombre: new FormControl(pacientesRawValue.nombre),
      apellidoPaterno: new FormControl(pacientesRawValue.apellidoPaterno),
      apellidoMaterno: new FormControl(pacientesRawValue.apellidoMaterno),
      rut: new FormControl(pacientesRawValue.rut),
      fechaNacimiento: new FormControl(pacientesRawValue.fechaNacimiento),
      genero: new FormControl(pacientesRawValue.genero),
      telefono: new FormControl(pacientesRawValue.telefono),
      email: new FormControl(pacientesRawValue.email),
      direccion: new FormControl(pacientesRawValue.direccion),
    });
  }

  getPacientes(form: PacientesFormGroup): IPacientes | NewPacientes {
    return form.getRawValue() as IPacientes | NewPacientes;
  }

  resetForm(form: PacientesFormGroup, pacientes: PacientesFormGroupInput): void {
    const pacientesRawValue = { ...this.getFormDefaults(), ...pacientes };
    form.reset(
      {
        ...pacientesRawValue,
        id: { value: pacientesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PacientesFormDefaults {
    return {
      id: null,
    };
  }
}
