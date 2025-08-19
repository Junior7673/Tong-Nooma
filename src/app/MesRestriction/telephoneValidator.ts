import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function telephoneValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value?.toString().trim();

    // Autorise champ vide -> géré par Validators.required ailleurs
    if (!value) {
      return null;
    }

    // Supprime les espaces pour la validation stricte
    const cleaned = value.replace(/\s+/g, '');

    // Vérifie longueur réelle
    if (cleaned.length < 8 || cleaned.length > 20) {
      return { invalidTelephone: true };
    }

    // Vérifie format : + optionnel puis uniquement chiffres
    if (!/^\+?\d+$/.test(cleaned)) {
      return { invalidTelephone: true };
    }

    return null;
  };
}
