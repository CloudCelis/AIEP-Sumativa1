import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPacientes } from '../pacientes.model';
import { PacientesService } from '../service/pacientes.service';

const pacientesResolve = (route: ActivatedRouteSnapshot): Observable<null | IPacientes> => {
  const id = route.params.id;
  if (id) {
    return inject(PacientesService)
      .find(id)
      .pipe(
        mergeMap((pacientes: HttpResponse<IPacientes>) => {
          if (pacientes.body) {
            return of(pacientes.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pacientesResolve;
