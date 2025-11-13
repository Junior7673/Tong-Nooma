import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './guards/admin-guard';

const routes: Routes = [
  {
    path: '',
    canActivate: [AdminGuard],
    children: [
      { path: '', redirectTo: 'actualites', pathMatch: 'full'},

      {
        path: 'actualites', 
        loadComponent: () => import('./actualites/actualite-list/actualite-list').then(m => m.ActualiteList)
      },
      {
        path: 'actualites/nouveau', 
        loadComponent: () => import('./actualites/actualite-form/actualite-form').then(m => m.ActualiteForm)
      },
      {
        path: 'actualites/:id',
        loadComponent: () => import('./actualites/actualite-detail/actualite-detail').then(m => m.ActualiteDetail)
      },
      {
        path: 'actualites/:id/edit',
        loadComponent: () => import('./actualites/actualite-form/actualite-form').then(m => m.ActualiteForm)
      },
      { path: '**', redirectTo: 'actualites' },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
