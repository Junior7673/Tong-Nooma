import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { LoginInterface } from '../interfaces/LoginInterface';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { LoginResponse } from '../interfaces/LoginResponse';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient,
              @Inject(PLATFORM_ID) private platformId: Object) {}

  login(credentials: LoginInterface): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('token', response.token);
          if (response.role) {
            localStorage.setItem('role', response.role);
          }
          if (response.expiresAt) {
            localStorage.setItem('expiresAt', response.expiresAt.toString());
          }
          this.scheduleAutoLogout();
        }
      }),
      catchError(err => throwError(() => err))
    );
  }

  register(newUser: LoginInterface): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, newUser).pipe(
      catchError(err => throwError(() => err))
    );
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      localStorage.removeItem('expiresAt'); // ✅
    }
  }

  isAuthenticated(): boolean {
    if (!isPlatformBrowser(this.platformId)) return false;
    const token = localStorage.getItem('token');
    const expiresAt = localStorage.getItem('expiresAt');
    if (!token || !expiresAt) return false;
    return Date.now() < parseInt(expiresAt, 10);
  }

  getUserRole(): string {
    return localStorage.getItem('role') ?? '';
  }

  isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN';
  }

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