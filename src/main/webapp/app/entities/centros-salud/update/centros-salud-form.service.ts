import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICentrosSalud, NewCentrosSalud } from '../centros-salud.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICentrosSalud for edit and NewCentrosSaludFormGroupInput for create.
 */
type CentrosSaludFormGroupInput = ICentrosSalud | PartialWithRequiredKeyOf<NewCentrosSalud>;

type CentrosSaludFormDefaults = Pick<NewCentrosSalud, 'id'>;

type CentrosSaludFormGroupContent = {
  id: FormControl<ICentrosSalud['id'] | NewCentrosSalud['id']>;
  centroSaludID: FormControl<ICentrosSalud['centroSaludID']>;
  nombre: FormControl<ICentrosSalud['nombre']>;
  direccion: FormControl<ICentrosSalud['direccion']>;
  telefono: FormControl<ICentrosSalud['telefono']>;
  vigente: FormControl<ICentrosSalud['vigente']>;
};

export type CentrosSaludFormGroup = FormGroup<CentrosSaludFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CentrosSaludFormService {
  createCentrosSaludFormGroup(centrosSalud: CentrosSaludFormGroupInput = { id: null }): CentrosSaludFormGroup {
    const centrosSaludRawValue = {
      ...this.getFormDefaults(),
      ...centrosSalud,
    };
    return new FormGroup<CentrosSaludFormGroupContent>({
      id: new FormControl(
        { value: centrosSaludRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      centroSaludID: new FormControl(centrosSaludRawValue.centroSaludID, {
        validators: [Validators.required],
      }),
      nombre: new FormControl(centrosSaludRawValue.nombre, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(centrosSaludRawValue.direccion, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(centrosSaludRawValue.telefono),
      vigente: new FormControl(centrosSaludRawValue.vigente, {
        validators: [Validators.maxLength(1)],
      }),
    });
  }

  getCentrosSalud(form: CentrosSaludFormGroup): ICentrosSalud | NewCentrosSalud {
    return form.getRawValue() as ICentrosSalud | NewCentrosSalud;
  }

  resetForm(form: CentrosSaludFormGroup, centrosSalud: CentrosSaludFormGroupInput): void {
    const centrosSaludRawValue = { ...this.getFormDefaults(), ...centrosSalud };
    form.reset(
      {
        ...centrosSaludRawValue,
        id: { value: centrosSaludRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CentrosSaludFormDefaults {
    return {
      id: null,
    };
  }
}
