import { AbstractControl, ValidationErrors } from '@angular/forms';

export function dateValidator(control: AbstractControl): ValidationErrors | null {
  const today = new Date();
  const inputDate = new Date(control.value);
  
  if (isNaN(inputDate.getTime())) {
    return { invalidDate: true }; // format non reconnu
  }

  if (inputDate > today) {
    return { futureDate: true }; // date dans le futur
  }

  // Exemple : date avant 2000 interdite
  const minDate = new Date('2000-01-01');
  if (inputDate < minDate) {
    return { outdatedDate: true };
  }

  

  return null;
}
