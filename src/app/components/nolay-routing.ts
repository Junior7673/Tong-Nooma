import { Routes } from '@angular/router';

export const nolayRoutes: Routes = [
  { path: 'login', loadComponent: () => import('./login-component/login-component').then(m => m.LoginComponent) }
];
