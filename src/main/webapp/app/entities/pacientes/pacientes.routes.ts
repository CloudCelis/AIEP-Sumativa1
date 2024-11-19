import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PacientesResolve from './route/pacientes-routing-resolve.service';

const pacientesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/pacientes.component').then(m => m.PacientesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/pacientes-detail.component').then(m => m.PacientesDetailComponent),
    resolve: {
      pacientes: PacientesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/pacientes-update.component').then(m => m.PacientesUpdateComponent),
    resolve: {
      pacientes: PacientesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/pacientes-update.component').then(m => m.PacientesUpdateComponent),
    resolve: {
      pacientes: PacientesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pacientesRoute;
