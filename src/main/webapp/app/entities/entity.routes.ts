import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'sumativaApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'centros-salud',
    data: { pageTitle: 'sumativaApp.centrosSalud.home.title' },
    loadChildren: () => import('./centros-salud/centros-salud.routes'),
  },
  {
    path: 'pacientes',
    data: { pageTitle: 'sumativaApp.pacientes.home.title' },
    loadChildren: () => import('./pacientes/pacientes.routes'),
  },
  {
    path: 'medico',
    data: { pageTitle: 'sumativaApp.medico.home.title' },
    loadChildren: () => import('./medico/medico.routes'),
  },
  {
    path: 'reserva',
    data: { pageTitle: 'sumativaApp.reserva.home.title' },
    loadChildren: () => import('./reserva/reserva.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
