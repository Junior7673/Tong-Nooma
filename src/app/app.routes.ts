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
    loadComponent: () => import('./components/home-component/home-component').then(m => m.HomeComponent) 
    },
    {
        path: 'news',
        canActivate: [AuthGuard],
        loadComponent: () => import('./components/actualite.component/actualite.component').then(m=> m.ActualiteComponent)
    },

    { path: 'nolay', children: nolayRoutes },
    



];
