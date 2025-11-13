import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actualite } from '../interfaces/ActualiteInterface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ActualiteService {
  private baseUrl = 'http://localhost:8080/actualite';
  
  constructor(private http: HttpClient) {}

  // ✅ Récupérer toutes les actualités
  getAll(): Observable<Actualite[]> {
    return this.http.get<Actualite[]>(this.baseUrl);
  }

  // ✅ Récupérer une actualité par ID
  getById(id: number): Observable<Actualite> {
    return this.http.get<Actualite>(`${this.baseUrl}/${id}`);
  }

  // ✅ Créer une actualité (multipart si image)
  create(data: FormData): Observable<Actualite> {
  const token = localStorage.getItem('token');
  return this.http.post<Actualite>(`${this.baseUrl}`, data, {
    headers: token ? { Authorization: `Bearer ${token}` } : {}
  });
}


  // ✅ Mettre à jour une actualité
  update(id: number, actu: Actualite): Observable<Actualite> {
    return this.http.put<Actualite>(`${this.baseUrl}/${id}`, actu);
  }

  // ✅ Supprimer une actualité
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
