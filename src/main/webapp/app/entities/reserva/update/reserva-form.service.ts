import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IReserva, NewReserva } from '../reserva.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReserva for edit and NewReservaFormGroupInput for create.
 */
type ReservaFormGroupInput = IReserva | PartialWithRequiredKeyOf<NewReserva>;

type ReservaFormDefaults = Pick<NewReserva, 'id'>;

type ReservaFormGroupContent = {
  id: FormControl<IReserva['id'] | NewReserva['id']>;
  medico: FormControl<IReserva['medico']>;
  reserva: FormControl<IReserva['reserva']>;
  paciente: FormControl<IReserva['paciente']>;
  centroSalud: FormControl<IReserva['centroSalud']>;
  fechaHora: FormControl<IReserva['fechaHora']>;
  estado: FormControl<IReserva['estado']>;
  pacientes: FormControl<IReserva['pacientes']>;
  fkMedico: FormControl<IReserva['fkMedico']>;
  centrosSalud: FormControl<IReserva['centrosSalud']>;
};

export type ReservaFormGroup = FormGroup<ReservaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReservaFormService {
  createReservaFormGroup(reserva: ReservaFormGroupInput = { id: null }): ReservaFormGroup {
    const reservaRawValue = {
      ...this.getFormDefaults(),
      ...reserva,
    };
    return new FormGroup<ReservaFormGroupContent>({
      id: new FormControl(
        { value: reservaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      medico: new FormControl(reservaRawValue.medico),
      reserva: new FormControl(reservaRawValue.reserva),
      paciente: new FormControl(reservaRawValue.paciente),
      centroSalud: new FormControl(reservaRawValue.centroSalud),
      fechaHora: new FormControl(reservaRawValue.fechaHora),
      estado: new FormControl(reservaRawValue.estado),
      pacientes: new FormControl(reservaRawValue.pacientes),
      fkMedico: new FormControl(reservaRawValue.fkMedico),
      centrosSalud: new FormControl(reservaRawValue.centrosSalud),
    });
  }

  getReserva(form: ReservaFormGroup): IReserva | NewReserva {
    return form.getRawValue() as IReserva | NewReserva;
  }

  resetForm(form: ReservaFormGroup, reserva: ReservaFormGroupInput): void {
    const reservaRawValue = { ...this.getFormDefaults(), ...reserva };
    form.reset(
      {
        ...reservaRawValue,
        id: { value: reservaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ReservaFormDefaults {
    return {
      id: null,
    };
  }
}
