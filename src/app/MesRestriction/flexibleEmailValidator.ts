import { AbstractControl, ValidationErrors } from '@angular/forms';

export function flexibleEmailValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i; // Le "i" rend insensible à la casse

  if (!value || regex.test(value)) {
    return null; // Champ valide
  }

  return { flexibleEmail: true }; // Champ invalide
}


/*

<div *ngIf="categorieForm.get('email')?.hasError('invalidEmail') && categorieForm.get('email')?.touched">
  <p class="error-message">
    Format d’adresse invalide. Utilise : prenomnomXX@gmail ou prenomnomXX@gmail.com
  </p>
</div>
*/ 