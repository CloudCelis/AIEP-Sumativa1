import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentrosSalud } from '../centros-salud.model';
import { CentrosSaludService } from '../service/centros-salud.service';
import { CentrosSaludFormGroup, CentrosSaludFormService } from './centros-salud-form.service';

@Component({
  standalone: true,
  selector: 'jhi-centros-salud-update',
  templateUrl: './centros-salud-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CentrosSaludUpdateComponent implements OnInit {
  isSaving = false;
  centrosSalud: ICentrosSalud | null = null;

  protected centrosSaludService = inject(CentrosSaludService);
  protected centrosSaludFormService = inject(CentrosSaludFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CentrosSaludFormGroup = this.centrosSaludFormService.createCentrosSaludFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centrosSalud }) => {
      this.centrosSalud = centrosSalud;
      if (centrosSalud) {
        this.updateForm(centrosSalud);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centrosSalud = this.centrosSaludFormService.getCentrosSalud(this.editForm);
    if (centrosSalud.id !== null) {
      this.subscribeToSaveResponse(this.centrosSaludService.update(centrosSalud));
    } else {
      this.subscribeToSaveResponse(this.centrosSaludService.create(centrosSalud));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentrosSalud>>): void {
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

  protected updateForm(centrosSalud: ICentrosSalud): void {
    this.centrosSalud = centrosSalud;
    this.centrosSaludFormService.resetForm(this.editForm, centrosSalud);
  }
}
