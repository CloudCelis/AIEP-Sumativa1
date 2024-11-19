import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ICentrosSalud } from '../centros-salud.model';

@Component({
  standalone: true,
  selector: 'jhi-centros-salud-detail',
  templateUrl: './centros-salud-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CentrosSaludDetailComponent {
  centrosSalud = input<ICentrosSalud | null>(null);

  previousState(): void {
    window.history.back();
  }
}
