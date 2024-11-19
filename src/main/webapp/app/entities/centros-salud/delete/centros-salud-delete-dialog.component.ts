import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICentrosSalud } from '../centros-salud.model';
import { CentrosSaludService } from '../service/centros-salud.service';

@Component({
  standalone: true,
  templateUrl: './centros-salud-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CentrosSaludDeleteDialogComponent {
  centrosSalud?: ICentrosSalud;

  protected centrosSaludService = inject(CentrosSaludService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centrosSaludService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
