import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPacientes } from '../pacientes.model';
import { PacientesService } from '../service/pacientes.service';
import { PacientesFormGroup, PacientesFormService } from './pacientes-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pacientes-update',
  templateUrl: './pacientes-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PacientesUpdateComponent implements OnInit {
  isSaving = false;
  pacientes: IPacientes | null = null;

  protected pacientesService = inject(PacientesService);
  protected pacientesFormService = inject(PacientesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PacientesFormGroup = this.pacientesFormService.createPacientesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pacientes }) => {
      this.pacientes = pacientes;
      if (pacientes) {
        this.updateForm(pacientes);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pacientes = this.pacientesFormService.getPacientes(this.editForm);
    if (pacientes.id !== null) {
      this.subscribeToSaveResponse(this.pacientesService.update(pacientes));
    } else {
      this.subscribeToSaveResponse(this.pacientesService.create(pacientes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPacientes>>): void {
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

  protected updateForm(pacientes: IPacientes): void {
    this.pacientes = pacientes;
    this.pacientesFormService.resetForm(this.editForm, pacientes);
  }
}
