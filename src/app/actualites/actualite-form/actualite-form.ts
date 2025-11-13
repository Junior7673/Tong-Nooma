import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { ActualiteService } from '../../services/actualite-service';
import { Actualite } from '../../interfaces/ActualiteInterface';

@Component({
  selector: 'app-actualite-form',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './actualite-form.html',
  styleUrls: ['./actualite-form.css']
})
export class ActualiteForm implements OnInit, OnDestroy {

  form !: FormGroup;
  isEditMode = false;
  actuId! : number;
  previewUrl: string | ArrayBuffer | null = null;

  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private actualiteService: ActualiteService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit(): void {
      this.form = this.fb.group({
      titre: ['', [Validators.required, Validators.minLength(3)]],
      contenu: ['', [Validators.required, Validators.minLength(10)]],
      image: [null]
    });
    // Vérifie si on est en mode édition
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEditMode = true;
        this.actuId = +idParam;
        this.loadActualite(this.actuId);
      }
    });
  }

    ngOnDestroy(): void {
       this.destroy$.next();
    this.destroy$.complete();
    }

    /** Charger l’actualité pour l’édition */
  loadActualite(id: number): void {
    this.actualiteService.getById(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (actu: Actualite) => {
          this.form.patchValue({
            titre: actu.titre,
            contenu: actu.contenu
          });
          if (actu.imageUrl) {
            this.previewUrl = actu.imageUrl;
          }
        },
        error: (err) => {
          console.error('Erreur lors du chargement:', err);
        }
      });
  }

  /** Gérer l’upload d’image */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      this.form.patchValue({ image: file });

      // Preview
      const reader = new FileReader();
      reader.onload = e => this.previewUrl = reader.result;
      reader.readAsDataURL(file);
    }
  }

  /** Soumission du formulaire */
  onSubmit(): void {
    if (this.form.invalid) return;

    const formData = new FormData();
    formData.append('titre', this.form.get('titre')?.value);
    formData.append('contenu', this.form.get('contenu')?.value);
    if (this.form.get('image')?.value) {
      formData.append('image', this.form.get('image')?.value);
    }

    if (this.isEditMode) {
      this.actualiteService.update(this.actuId, this.form.value).subscribe({
        next: () => {
          alert('✅ Actualité mise à jour avec succès');
          this.router.navigate(['/actualites']);
        },
        error: (err) => {
          console.error('Erreur update:', err);
        }
      });
    }else {
      this.actualiteService.create(formData).subscribe({
        next: () => {
          alert('✅ Actualité créée avec succès');
          this.router.navigate(['/actualites']);
        },
        error: (err) => {
          console.error('Erreur création:', err);
        }
      });
    }
  }

  /** Retour en arrière */
  cancel(): void {
    this.router.navigate(['/actualites']);
  }
}
