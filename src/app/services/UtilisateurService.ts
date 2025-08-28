import { Injectable } from '@angular/core';
import { UtilisateurInterface } from '../interfaces/UtilisateurInterface';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  private baseUrl = 'http://localhost:8080/api/utilisateurs';

  constructor(private http: HttpClient) {}

  getAll(): Observable<UtilisateurInterface[]> {
    return this.http.get<UtilisateurInterface[]>(`${this.baseUrl}`);
  }

  getById(id: number): Observable<UtilisateurInterface> {
    return this.http.get<UtilisateurInterface>(`${this.baseUrl}/${id}`);
  }

  create(utilisateur: UtilisateurInterface): Observable<UtilisateurInterface> {
    return this.http.post<UtilisateurInterface>(`${this.baseUrl}`, utilisateur);
  }

  update(id: number, utilisateur: UtilisateurInterface): Observable<UtilisateurInterface> {
    return this.http.put<UtilisateurInterface>(`${this.baseUrl}/${id}`, utilisateur);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
}
