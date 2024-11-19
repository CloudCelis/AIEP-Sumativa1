import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPacientes } from 'app/entities/pacientes/pacientes.model';
import { PacientesService } from 'app/entities/pacientes/service/pacientes.service';
import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { ICentrosSalud } from 'app/entities/centros-salud/centros-salud.model';
import { CentrosSaludService } from 'app/entities/centros-salud/service/centros-salud.service';
import { ReservaService } from '../service/reserva.service';
import { IReserva } from '../reserva.model';
import { ReservaFormGroup, ReservaFormService } from './reserva-form.service';

@Component({
  standalone: true,
  selector: 'jhi-reserva-update',
  templateUrl: './reserva-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReservaUpdateComponent implements OnInit {
  isSaving = false;
  reserva: IReserva | null = null;

  pacientesSharedCollection: IPacientes[] = [];
  medicosSharedCollection: IMedico[] = [];
  centrosSaludsSharedCollection: ICentrosSalud[] = [];

  protected reservaService = inject(ReservaService);
  protected reservaFormService = inject(ReservaFormService);
  protected pacientesService = inject(PacientesService);
  protected medicoService = inject(MedicoService);
  protected centrosSaludService = inject(CentrosSaludService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReservaFormGroup = this.reservaFormService.createReservaFormGroup();

  comparePacientes = (o1: IPacientes | null, o2: IPacientes | null): boolean => this.pacientesService.comparePacientes(o1, o2);

  compareMedico = (o1: IMedico | null, o2: IMedico | null): boolean => this.medicoService.compareMedico(o1, o2);

  compareCentrosSalud = (o1: ICentrosSalud | null, o2: ICentrosSalud | null): boolean =>
    this.centrosSaludService.compareCentrosSalud(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reserva }) => {
      this.reserva = reserva;
      if (reserva) {
        this.updateForm(reserva);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reserva = this.reservaFormService.getReserva(this.editForm);
    if (reserva.id !== null) {
      this.subscribeToSaveResponse(this.reservaService.update(reserva));
    } else {
      this.subscribeToSaveResponse(this.reservaService.create(reserva));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReserva>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reserva: IReserva): void {
    this.reserva = reserva;
    this.reservaFormService.resetForm(this.editForm, reserva);

    this.pacientesSharedCollection = this.pacientesService.addPacientesToCollectionIfMissing<IPacientes>(
      this.pacientesSharedCollection,
      reserva.pacientes,
    );
    this.medicosSharedCollection = this.medicoService.addMedicoToCollectionIfMissing<IMedico>(
      this.medicosSharedCollection,
      reserva.fkMedico,
    );
    this.centrosSaludsSharedCollection = this.centrosSaludService.addCentrosSaludToCollectionIfMissing<ICentrosSalud>(
      this.centrosSaludsSharedCollection,
      reserva.centrosSalud,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pacientesService
      .query()
      .pipe(map((res: HttpResponse<IPacientes[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPacientes[]) =>
          this.pacientesService.addPacientesToCollectionIfMissing<IPacientes>(pacientes, this.reserva?.pacientes),
        ),
      )
      .subscribe((pacientes: IPacientes[]) => (this.pacientesSharedCollection = pacientes));

    this.medicoService
      .query()
      .pipe(map((res: HttpResponse<IMedico[]>) => res.body ?? []))
      .pipe(map((medicos: IMedico[]) => this.medicoService.addMedicoToCollectionIfMissing<IMedico>(medicos, this.reserva?.fkMedico)))
      .subscribe((medicos: IMedico[]) => (this.medicosSharedCollection = medicos));

    this.centrosSaludService
      .query()
      .pipe(map((res: HttpResponse<ICentrosSalud[]>) => res.body ?? []))
      .pipe(
        map((centrosSaluds: ICentrosSalud[]) =>
          this.centrosSaludService.addCentrosSaludToCollectionIfMissing<ICentrosSalud>(centrosSaluds, this.reserva?.centrosSalud),
        ),
      )
      .subscribe((centrosSaluds: ICentrosSalud[]) => (this.centrosSaludsSharedCollection = centrosSaluds));
  }
}
