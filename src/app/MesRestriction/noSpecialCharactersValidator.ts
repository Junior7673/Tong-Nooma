import { AbstractControl, ValidationErrors } from '@angular/forms';

export function noSpecialCharactersValidator(control: AbstractControl): ValidationErrors | null {
  const regex = /^[a-zA-Z0-9\sÀ-ÿ]*$/; // Permet les lettres accentuées et les chiffres
  const valid = regex.test(control.value);
  return valid ? null : { specialChars: 'Caractères spéciaux non autorisés' };
}
