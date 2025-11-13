import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { LoginInterface } from '../interfaces/LoginInterface';
import { LoginResponse } from '../interfaces/LoginResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  // 🔑 Connexion
  login(credentials: LoginInterface): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role ?? '');
          localStorage.setItem('expiresAt', response.expiresAt.toString());
          this.scheduleAutoLogout();
        }
      }),
      catchError(err => throwError(() => err))
    );
  }

  // 👤 Inscription (ajoute aussi expiresAt si le backend l’envoie)
  register(newUser: LoginInterface): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/register`, newUser).pipe(
      tap(response => {
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role ?? '');
          localStorage.setItem('expiresAt', response.expiresAt.toString());
          this.scheduleAutoLogout();
        }
      }),
      catchError(err => throwError(() => err))
    );
  }

  // 🚪 Déconnexion
  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      localStorage.removeItem('expiresAt');
    }
  }

  // ✅ Vérifie si le token est encore valide
  isAuthenticated(): boolean {
    if (!isPlatformBrowser(this.platformId)) return false;
    const token = localStorage.getItem('token');
    const expiresAt = localStorage.getItem('expiresAt');
    if (!token || !expiresAt) return false;
    return Date.now() < parseInt(expiresAt, 10);
  }

  // 🎭 Rôle utilisateur
  getUserRole(): string {
    return localStorage.getItem('role') ?? '';
  }

  isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN';
  }

  // ⏳ Déconnexion automatique
  private scheduleAutoLogout() {
    const expiresAt = localStorage.getItem('expiresAt');
    if (!expiresAt) return;
    const timeout = parseInt(expiresAt, 10) - Date.now();
    if (timeout > 0) {
      setTimeout(() => this.logout(), timeout);
    } else {
      this.logout();
    }
  }
}
