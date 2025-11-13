import { Routes } from '@angular/router';
import { AuthGuard } from './services/auth.guard';
import { nolayRoutes } from './components/nolay-routing';
import { MainLayoutComponent } from './components/layouts/main-layout-component';
import { AuthLayoutComponent } from './components/layouts/auth-layout-component';

export const routes: Routes = [

    {
        path: '',
        component: MainLayoutComponent,
        canActivate: [AuthGuard],
        data: { roles: ['UTILISATEUR', 'ADMIN'] }, // ✅ restrict to user and admin roles
        children: [
            {path: '', loadComponent: () => import('./components/home-component/home-component').then(m => m.HomeComponent)}, 
            {path: 'news', loadComponent: () => import('./components/actualite.component/actualite.component').then(m=> m.ActualiteComponent)},
            {path: 'gallery', loadComponent: () => import('./components/galerie.component/galerie.component').then(m=> m.GalerieComponent)},
            {path: 'programs', loadComponent: () => import('./components/programme.component/programme.component').then(m=> m.ProgrammeComponent)},
            {path: 'contact', loadComponent: () => import('./components/contact.component/contact.component').then(m => m.ContactComponent)}
            
        ]
    },

    {
        path: '',
        component: AuthLayoutComponent,
        children: [
            {path: 'login', loadComponent: () => import('./components/login-component/login-component').then(m => m.LoginComponent)},
            {path: 'register', loadComponent: ()=> import('./components/register-component/register-component').then(m => m.RegisterComponent)}
        ]
    },

    { 
        path: 'actualite', 
        loadComponent: () => import('./actualites/actualite-list/actualite-list').then(m=> m.ActualiteList)
    },
    {
        path: 'actualite/:id',
        loadComponent:() => import('./actualites/actualite-detail/actualite-detail').then(m=> m.ActualiteDetail)
    },

    { path: 'nolay', children: nolayRoutes },
    { path: '**', redirectTo: 'home' }, // ✅ fallback
    
    { 
        path: 'admin', 
        canActivate: [AuthGuard],
        data: { roles: ['ADMIN'] }, // ✅ restrict to admin role
        loadChildren: () => import('./admin-routing.module').then(m => m.AdminRoutingModule) 
    } // ✅ lazy load admin module

];
