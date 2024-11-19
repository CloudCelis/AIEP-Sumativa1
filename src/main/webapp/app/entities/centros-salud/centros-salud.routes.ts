import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CentrosSaludResolve from './route/centros-salud-routing-resolve.service';

const centrosSaludRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/centros-salud.component').then(m => m.CentrosSaludComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/centros-salud-detail.component').then(m => m.CentrosSaludDetailComponent),
    resolve: {
      centrosSalud: CentrosSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/centros-salud-update.component').then(m => m.CentrosSaludUpdateComponent),
    resolve: {
      centrosSalud: CentrosSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/centros-salud-update.component').then(m => m.CentrosSaludUpdateComponent),
    resolve: {
      centrosSalud: CentrosSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centrosSaludRoute;
