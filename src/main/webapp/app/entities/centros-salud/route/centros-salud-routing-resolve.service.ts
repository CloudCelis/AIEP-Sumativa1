import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICentrosSalud } from '../centros-salud.model';
import { CentrosSaludService } from '../service/centros-salud.service';

const centrosSaludResolve = (route: ActivatedRouteSnapshot): Observable<null | ICentrosSalud> => {
  const id = route.params.id;
  if (id) {
    return inject(CentrosSaludService)
      .find(id)
      .pipe(
        mergeMap((centrosSalud: HttpResponse<ICentrosSalud>) => {
          if (centrosSalud.body) {
            return of(centrosSalud.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default centrosSaludResolve;
