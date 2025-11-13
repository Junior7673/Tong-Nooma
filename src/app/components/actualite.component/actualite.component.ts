import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Subject, takeUntil, finalize } from 'rxjs';
import { Actualite } from '../../interfaces/ActualiteInterface';
import { ActualiteService } from '../../services/actualite-service';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-actualite',
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
  isLoading = false;
  isSubmitting = false;

  actuForm!: FormGroup;
  showForm = false;
  selectedFile?: File;

  private destroy$ = new Subject<void>();

  constructor(
    private actualiteService: ActualiteService,
    private authService: AuthService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadActualites();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  initForm(): void {
    this.actuForm = this.fb.group({
      titre: ['', Validators.required],
      contenu: ['', [Validators.required, Validators.minLength(10)]],
    });
  }

  loadActualites(): void {
    if (this.isLoading) return;
    this.isLoading = true;
    this.actualiteService.getAll()
      .pipe(takeUntil(this.destroy$), finalize(() => {
        this.isLoading = false;
        this.cdr.markForCheck();
      }))
      .subscribe({
        next: (data) => {
          this.actualites = (data || []).sort((a, b) =>
            new Date(b.datePub).getTime() - new Date(a.datePub).getTime()
          );
        },
        error: (error) => {
          console.error('Erreur lors du chargement:', error);
        }
      });
  }

  addActualite(): void {
  if (!this.authService.isAdmin()) {
    alert('Accès refusé : seul un administrateur peut publier.');
    return;
  }

  if (this.actuForm.invalid) return;

  const formData = new FormData();
  formData.append('titre', this.actuForm.value.titre);
  formData.append('contenu', this.actuForm.value.contenu);
  if (this.selectedFile) {
    formData.append('image', this.selectedFile);
  }

  this.actualiteService.create(formData).subscribe({
    next: () => {
      this.loadActualites();
      this.actuForm.reset();
      this.showForm = false;
      this.selectedFile = undefined;
      alert('✅ Actualité publiée avec succès !');
    },
    error: (err) => {
      console.error('Erreur ajout actualité:', err);
      alert('❌ Erreur lors de la publication (vérifie ton rôle ADMIN et le token)');
    }
  });
}


  // Méthode appelée au submit
  onSubmit(): void {
    if (!this.authService.isAdmin()) {
      console.warn('Accès refusé : seul un administrateur peut publier.');
      return;
    }

    if (this.actuForm.invalid) {
      // Force l'affichage des erreurs
      this.actuForm.markAllAsTouched();
      return;
    }

    const formData = new FormData();
    formData.append('titre', (this.actuForm.value.titre || '').trim());
    formData.append('contenu', (this.actuForm.value.contenu || '').trim());

    if (this.selectedFile) {
      formData.append('image', this.selectedFile, this.selectedFile.name);
    }

    // Supporter Observable et Promise pour être robuste
    const result: any = this.actualiteService.create(formData);
    this.isSubmitting = true;

    if (result && typeof result.subscribe === 'function') {
      result.pipe(takeUntil(this.destroy$), finalize(() => {
        this.isSubmitting = false;
        this.cdr.markForCheck();
      })).subscribe({
        next: () => {
          this.afterSuccessfulSubmit();
        },
        error: (err: any) => {
          console.error('Erreur ajout actualité:', err);
        }
      });
    } else if (result && typeof result.then === 'function') {
      result.then(() => {
        this.afterSuccessfulSubmit();
      }).catch((err: any) => {
        console.error('Erreur ajout actualité:', err);
      }).finally(() => {
        this.isSubmitting = false;
        this.cdr.markForCheck();
      });
    } else {
      console.error('actualiteService.create doit retourner un Observable ou une Promise. Vérifie le service.');
      this.isSubmitting = false;
    }
  }

  private afterSuccessfulSubmit(): void {
    this.loadActualites();
    this.actuForm.reset();
    this.showForm = false;
    this.selectedFile = undefined;
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/images/placeholder-actualite.jpg';
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  trackById(index: number, actu: Actualite): number {
    return actu?.id ?? index;
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  isInvalidControl(name: string): boolean {
    const ctrl = this.actuForm.get(name);
    return !!(ctrl && ctrl.invalid && (ctrl.touched || ctrl.dirty));
  }
}
