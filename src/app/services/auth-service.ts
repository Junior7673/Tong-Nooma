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
              @Inject(PLATFORM_ID) private platformId: Object){

  }

  login(credentials: LoginInterface): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      /**tap(response =>{
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role);
        }
      }),*/
      tap(response => {
  if (isPlatformBrowser(this.platformId)) {
    localStorage.setItem('token', response.token);
    if (response.role) {
      localStorage.setItem('role', response.role);
    }
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
    }
  }

  isAuthenticated(): boolean{
        return isPlatformBrowser(this.platformId) && !!localStorage.getItem('token');

  }
   getUserRole(): string {
    return localStorage.getItem('role') ?? '';
  }


  isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN';
  } 

  
}
