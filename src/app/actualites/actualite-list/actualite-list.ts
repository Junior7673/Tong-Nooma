import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Actualite } from '../../interfaces/ActualiteInterface';
import { finalize, Subject, takeUntil } from 'rxjs';
import { ActualiteService } from '../../services/actualite-service';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-actualite-list',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './actualite-list.html',
  styleUrls: ['./actualite-list.css']
})
export class ActualiteList implements OnInit, OnDestroy{
  actualites: Actualite[] = [];
  isLoading = false;

  private destroy$ = new Subject<void>();

  constructor(
    private actualiteService: ActualiteService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}


  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  ngOnInit(): void {
    this.loadActualites();
  }

  /** Charger toutes les actualités */
  loadActualites(): void {
    this.isLoading = true;
    this.actualiteService.getAll()
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => {
          this.isLoading = false;
          this.cdr.markForCheck();
        })
      )
      .subscribe({
        next: (data) => {
          this.actualites = (data || []).sort(
            (a, b) => new Date(b.datePub).getTime() - new Date(a.datePub).getTime()
          );
        },
        error: (err) => {
          console.error('Erreur lors du chargement des actualités:', err);
        }
      });
  }

  /** Navigation vers formulaire d’ajout */
  goToAdd(): void {
    this.router.navigate(['/admin/actualites/nouveau']);
  }

  /** Navigation vers formulaire d’édition */
  goToEdit(id: number): void {
    this.router.navigate([`/admin/actualites/${id}/edit`]);
  }

  /** Supprimer une actualité */
  deleteActualite(id: number): void {
    if (confirm('Voulez-vous vraiment supprimer cette actualité ?')) {
      this.actualiteService.delete(id).subscribe({
        next: () => {
          this.actualites = this.actualites.filter(a => a.id !== id);
        },
        error: (err) => {
          console.error('Erreur suppression:', err);
        }
      });
    }
  }

   /** Vérifier si admin */
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  trackById(index: number, actu: Actualite): number {
    return actu?.id ?? index;
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/images/placeholder-actualite.jpg';
  }

}
