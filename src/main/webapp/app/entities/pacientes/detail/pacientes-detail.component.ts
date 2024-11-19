import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IPacientes } from '../pacientes.model';

@Component({
  standalone: true,
  selector: 'jhi-pacientes-detail',
  templateUrl: './pacientes-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PacientesDetailComponent {
  pacientes = input<IPacientes | null>(null);

  previousState(): void {
    window.history.back();
  }
}
