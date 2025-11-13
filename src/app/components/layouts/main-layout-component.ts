import { Component } from '@angular/core';
import { HeaderComponent } from "../header.component/header.component";
import { RouterModule } from "@angular/router";
import { CommonModule } from '@angular/common';
import { ScrollProgressComponent } from "../scrollProgressComponent";

@Component({
  selector: 'app-main-layout',
  template: `
    <app-header></app-header>
    <app-scroll-progress></app-scroll-progress>
    <router-outlet></router-outlet>
  `,
  imports: [HeaderComponent, RouterModule, CommonModule, ScrollProgressComponent]
})
export class MainLayoutComponent {}
