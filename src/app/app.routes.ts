import { Routes } from '@angular/router';
import { AuthGuard } from './services/auth.guard';
import { nolayRoutes } from './components/nolay-routing';

export const routes: Routes = [

    {
        path: 'login',
        //canActivate: [AuthGuard],
        loadComponent: () => import('./components/login-component/login-component').then(m => m.LoginComponent)    
    },

    {
        path: 'register',
        loadComponent: ()=> import('./components/register-component/register-component').then(m => m.RegisterComponent)
    },

    {
        path: '',
        canActivate: [AuthGuard],
        loadComponent: () => import('./components/header.component/header.component').then(m => m.HeaderComponent)
    },

    /**{
        path: 'nolay/login',
        loadComponent: () => import('./components/login-component/login-component').then(m => m.LoginComponent)
    },*/

    {
  path: 'nolay',
  children: nolayRoutes
}


];
