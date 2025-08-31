import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Actualite } from '../../interfaces/ActualiteInterface';
import { ActualiteService } from '../../services/actualite-service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-actualite.component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './actualite.component.html',
  styleUrls: ['./actualite.component.css']
})
export class ActualiteComponent implements OnInit, OnDestroy {

  actualites: Actualite[] = [];
  actualitesSubscription?: Subscription;
  isLoading:boolean = false;

  constructor( private actualiteService: ActualiteService){}

  ngOnInit(): void {
    this.loadActualites();
  }

  ngOnDestroy(): void {
    this.actualitesSubscription?.unsubscribe();
  }

  loadActualites(): void {
    this.isLoading = true;
    this.actualiteService.getAll().then((values)=>{
      this.isLoading = false;
      this.actualites = values;
    }).catch((error)=>{
      this.isLoading = false;
      console.log(error);
    })
  
  }
}
