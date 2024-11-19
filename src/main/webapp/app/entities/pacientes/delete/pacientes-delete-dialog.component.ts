import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPacientes } from '../pacientes.model';
import { PacientesService } from '../service/pacientes.service';

@Component({
  standalone: true,
  templateUrl: './pacientes-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PacientesDeleteDialogComponent {
  pacientes?: IPacientes;

  protected pacientesService = inject(PacientesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pacientesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
