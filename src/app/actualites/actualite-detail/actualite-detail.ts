import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Actualite } from '../../interfaces/ActualiteInterface';
import { Subject, takeUntil } from 'rxjs';
import { ActualiteService } from '../../services/actualite-service';

@Component({
  selector: 'app-actualite-detail',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './actualite-detail.html',
  styleUrls: ['./actualite-detail.css']
})
export class ActualiteDetail implements OnInit, OnDestroy{
  actu!: Actualite;
  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private actualiteService: ActualiteService 
  ){}

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = +idParam;
      this.loadActualite(id);
    }
  }

  loadActualite(id: number): void{
    this.actualiteService.getById(id)
      .pipe(
        takeUntil(this.destroy$)
      )
      .subscribe({
        next: (actualite) => {
          this.actu = actualite;
        },
        error: (error) => {
          console.error('Erreur lors du chargement:', error);
        }
      });
  }

  backToList(): void{
    this.router.navigate(['/actualites']);
}
}
