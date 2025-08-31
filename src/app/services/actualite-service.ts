import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actualite } from '../interfaces/ActualiteInterface';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class ActualiteService {

  private baseUrl = 'http://localhost:8080/actualite';

  constructor( private http: HttpClient){}

  getAll(): Promise<Actualite[]> {
    return new Promise<Actualite[]>((resolve, reject) => {
      
      this.http.get<Actualite[]>(this.baseUrl).subscribe(
        (data)=>{
          resolve(data);
        },
        (error)=>{
          reject(error);
        }
      )
    });
  }

  getById(id: number): Observable<Actualite> {
    return this.http.get<Actualite>(`${this.baseUrl}/${id}`);
  }

  create(actu: Actualite): Observable<Actualite> {
    return this.http.post<Actualite>(this.baseUrl, actu);
  }

  update(id: number, actu: Actualite): Observable<Actualite> {
    return this.http.put<Actualite>(`${this.baseUrl}/${id}`, actu);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
}
