import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ChangeDetectorRef } from '@angular/core';
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
  isLoading: boolean = false;

  constructor(
    private actualiteService: ActualiteService,
    private cdr: ChangeDetectorRef // ✅ Pour forcer la détection des changements si nécessaire
  ) {}

  ngOnInit(): void {
    this.loadActualites();
  }

  ngOnDestroy(): void {
    this.actualitesSubscription?.unsubscribe();
  }
/**
   * ✅ Charge toutes les actualités triées par date décroissante
   */
  loadActualites(): void {
    this.isLoading = true;
    
    this.actualitesSubscription = this.actualiteService.getAll().subscribe({
      next: (data) => {
        // ✅ Tri par date décroissante (plus récent en premier)
        this.actualites = data.sort((a, b) => 
          new Date(b.datePub).getTime() - new Date(a.datePub).getTime()
        );
        
        this.isLoading = false;
        console.log('Toutes les actualités chargées:', this.actualites);
      },
      error: (error) => {
        console.error('Erreur lors du chargement:', error);
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * ✅ Gestion des erreurs d'images
   */
  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/images/placeholder-actualite.jpg'; // Image par défaut
  }
}