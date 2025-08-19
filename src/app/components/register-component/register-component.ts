import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { noSpecialCharactersValidator } from '../../MesRestriction/noSpecialCharactersValidator';
import { flexibleEmailValidator } from '../../MesRestriction/flexibleEmailValidator';
import { passwordStrengthValidator } from '../../MesRestriction/passwordStrengthValidator';
import { telephoneValidator } from '../../MesRestriction/telephoneValidator';

@Component({
  selector: 'app-register-component',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, CommonModule, RouterModule],
  templateUrl: './register-component.html',
  styleUrls: ['./register-component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage = '';
  successMessage = '';
  isLoading = false;
  hidePassword = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    // ✅ Initialisation immédiate de la FormGroup
    this.registerForm = this.fb.group({
      nom: ['', [Validators.required, noSpecialCharactersValidator]],
      prenom: ['', [Validators.required, noSpecialCharactersValidator]],
      email: ['', [Validators.required, flexibleEmailValidator]],
      telephone: ['', [Validators.required, telephoneValidator()]],
      password: ['', [Validators.required, passwordStrengthValidator]],
      //dateInscrip: ['', [Validators.required, dateValidator]]
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.errorMessage = 'Veuillez remplir correctement tous les champs';
      return;
    }

    this.isLoading = true;
    const registerData = {
      ...this.registerForm.value,
      //dateInscrip: new Date()
    };

    this.authService.register(registerData).subscribe({
      next: (response) => {
        this.successMessage = 'Inscription réussie !';
        this.errorMessage = '';
        console.log('Réponse API:', response);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = 'Erreur lors de l’inscription : ' + (err.error?.message || '');
        this.successMessage = '';
        console.error(err);
      }
    });
  }

  get nom() { return this.registerForm.get('nom'); }
  get prenom() { return this.registerForm.get('prenom'); }
  get email() { return this.registerForm.get('email'); }
  get telephone(){ return this.registerForm.get('telephone'); }
  get password() { return this.registerForm.get('password'); }
 // get dateInscrip() { return this.registerForm.get('dateInscrip'); }
}
