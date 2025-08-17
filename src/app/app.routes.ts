import { Routes } from '@angular/router';
import { AuthGuard } from './services/auth.guard';

export const routes: Routes = [

    {
        path: 'login',
        //canActivate: [AuthGuard],
        loadComponent: () => import('./components/login-component/login-component').then(m => m.LoginComponent)
        
    }
];
