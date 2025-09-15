import { Component } from '@angular/core';
import { HeaderComponent } from "../header.component/header.component";
import { RouterModule } from "@angular/router";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-main-layout',
  template: `
    <app-header></app-header>
    <router-outlet></router-outlet>
  `,
  imports: [HeaderComponent, RouterModule, CommonModule]
})
export class MainLayoutComponent {}
