import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentrosSalud } from 'app/entities/centros-salud/centros-salud.model';
import { CentrosSaludService } from 'app/entities/centros-salud/service/centros-salud.service';
import { IMedico } from '../medico.model';
import { MedicoService } from '../service/medico.service';
import { MedicoFormGroup, MedicoFormService } from './medico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-medico-update',
  templateUrl: './medico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MedicoUpdateComponent implements OnInit {
  isSaving = false;
  medico: IMedico | null = null;

  centrosSaludsCollection: ICentrosSalud[] = [];

  protected medicoService = inject(MedicoService);
  protected medicoFormService = inject(MedicoFormService);
  protected centrosSaludService = inject(CentrosSaludService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MedicoFormGroup = this.medicoFormService.createMedicoFormGroup();

  compareCentrosSalud = (o1: ICentrosSalud | null, o2: ICentrosSalud | null): boolean =>
    this.centrosSaludService.compareCentrosSalud(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medico }) => {
      this.medico = medico;
      if (medico) {
        this.updateForm(medico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medico = this.medicoFormService.getMedico(this.editForm);
    if (medico.id !== null) {
      this.subscribeToSaveResponse(this.medicoService.update(medico));
    } else {
      this.subscribeToSaveResponse(this.medicoService.create(medico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedico>>): void {
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

  protected updateForm(medico: IMedico): void {
    this.medico = medico;
    this.medicoFormService.resetForm(this.editForm, medico);

    this.centrosSaludsCollection = this.centrosSaludService.addCentrosSaludToCollectionIfMissing<ICentrosSalud>(
      this.centrosSaludsCollection,
      medico.centrosSalud,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.centrosSaludService
      .query({ filter: 'medico-is-null' })
      .pipe(map((res: HttpResponse<ICentrosSalud[]>) => res.body ?? []))
      .pipe(
        map((centrosSaluds: ICentrosSalud[]) =>
          this.centrosSaludService.addCentrosSaludToCollectionIfMissing<ICentrosSalud>(centrosSaluds, this.medico?.centrosSalud),
        ),
      )
      .subscribe((centrosSaluds: ICentrosSalud[]) => (this.centrosSaludsCollection = centrosSaluds));
  }
}
