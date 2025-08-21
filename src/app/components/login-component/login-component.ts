import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { LoginInterface } from '../../interfaces/LoginInterface';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    
  ],
  templateUrl: './login-component.html',
  styleUrls: ['./login-component.css']
})
export class LoginComponent implements OnInit{
  loginForm! : FormGroup ;
  errorMessage = '';
  isLoading: boolean = false;
  hidePassword: boolean = true; 

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ){}
  
  ngOnInit(): void {
    this.initLoginForm();
  }

  initLoginForm(){
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
      console.log('Formulaire soumis', this.loginForm.value); // ✅ debug
  if (this.loginForm.valid) {
    this.isLoading = true;
    const loginData: LoginInterface = this.loginForm.value;
    this.authService.login(loginData).subscribe({
      next: () => {
        this.router.navigate(['/']);
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = "email ou mot de passe incorrect";
        this.isLoading = false;
      }
    });
  } else {
    this.errorMessage = "Identifiants de connexion invalide !";
  }
}



  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }


}
