import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, catchError, throwError } from 'rxjs';
import { MediaInterface, MediaType } from '../interfaces/Media-Interface/MediaInterface';
import { MediaFilterInterface } from '../interfaces/Media-Interface/MediaFilterInterface';

/**
 * Service pour gérer les interactions avec l'API des médias
 * Correspond au MediaController du backend Spring Boot
 */
@Injectable({
  providedIn: 'root'
})
export class MediaService {
  /** URL de base de l'API des médias */
  private readonly baseUrl = 'http://localhost:8080/media';

  constructor(private http: HttpClient) {}

  /**
   * Récupère tous les médias depuis l'API
   * @returns Observable contenant la liste des médias
   */
  getAll(): Observable<MediaInterface[]> {
    return this.http.get<MediaInterface[]>(this.baseUrl).pipe(
      map((medias: MediaInterface[]) => {
        // ✅ Transformation des dates et validation des données
        return medias.map(media => ({
          ...media,
          dateAjout: media.dateAjout ? new Date(media.dateAjout) : new Date(),
          // ✅ S'assurer que le type correspond à l'enum backend
          type: media.type === 'PHOTO' ? MediaType.PHOTO : MediaType.VIDEO
        }));
      }),
      catchError(error => {
        console.error('Erreur lors du chargement des médias:', error);
        return throwError(() => error);
      })
    );
  }

  /**
   * Récupère un média par son ID
   * @param id Identifiant du média
   * @returns Observable contenant le média
   */
  getById(id: number): Observable<MediaInterface> {
    return this.http.get<MediaInterface>(`${this.baseUrl}/${id}`).pipe(
      map((media: MediaInterface) => ({
        ...media,
        dateAjout: media.dateAjout ? new Date(media.dateAjout) : new Date(),
        type: media.type === 'PHOTO' ? MediaType.PHOTO : MediaType.VIDEO
      })),
      catchError(error => {
        console.error(`Erreur lors du chargement du média ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  /**
   * Filtre les médias selon les critères spécifiés
   * @param medias Liste des médias à filtrer
   * @param filter Critères de filtrage
   * @returns Liste filtrée des médias
   */
  filterMedias(medias: MediaInterface[], filter: MediaFilterInterface): MediaInterface[] {
    let filteredMedias = [...medias];

    // ✅ Filtrer par type (PHOTO, VIDEO ou all)
    if (filter.type && filter.type !== 'all') {
      filteredMedias = filteredMedias.filter(media => media.type === filter.type);
    }

    // ✅ Recherche textuelle dans la description
    if (filter.search && filter.search.trim()) {
      const searchTerm = filter.search.toLowerCase().trim();
      filteredMedias = filteredMedias.filter(media => 
        media.description?.toLowerCase().includes(searchTerm)
      );
    }

    // ✅ Limiter le nombre de résultats
    if (filter.limit && filter.limit > 0) {
      filteredMedias = filteredMedias.slice(0, filter.limit);
    }

    return filteredMedias;
  }

  /**
   * Détermine si un média est une photo
   * @param media Média à vérifier
   * @returns true si c'est une photo
   */
  isPhoto(media: MediaInterface): boolean {
    return media.type === MediaType.PHOTO;
  }

  /**
   * Détermine si un média est une vidéo
   * @param media Média à vérifier
   * @returns true si c'est une vidéo
   */
  isVideo(media: MediaInterface): boolean {
    return media.type === MediaType.VIDEO;
  }

  /**
   * Détermine si une URL correspond à une image (méthode alternative)
   */
  isImageUrl(url: string): boolean {
    const imageExtensions = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'svg', 'bmp'];
    const extension = url.split('.').pop()?.toLowerCase();
    return extension ? imageExtensions.includes(extension) : false;
  }

  /**
   * Détermine si une URL correspond à une vidéo (méthode alternative)
   */
  isVideoUrl(url: string): boolean {
    const videoExtensions = ['mp4', 'avi', 'mkv', 'webm', 'mov', 'wmv', 'flv', 'ogg'];
    const extension = url.split('.').pop()?.toLowerCase();
    return extension ? videoExtensions.includes(extension) : false;
  }

  /**
   * Obtient le label d'affichage pour un type de média
   */
  getTypeLabel(type: 'PHOTO' | 'VIDEO'): string {
    switch (type) {
      case MediaType.PHOTO:
        return '📸 Photo';
      case MediaType.VIDEO:
        return '🎬 Vidéo';
      default:
        return '📁 Média';
    }
  }

  /**
   * Obtient l'icône pour un type de média
 
   */
  getTypeIcon(type: 'PHOTO' | 'VIDEO'): string {
    switch (type) {
      case MediaType.PHOTO:
        return '🖼️';
      case MediaType.VIDEO:
        return '▶️';
      default:
        return '📁';
    }
  }

  /**
   * Trie les médias par date d'ajout (plus récent en premier)
   */
  sortByDateDesc(medias: MediaInterface[]): MediaInterface[] {
    return medias.sort((a, b) => {
      const dateA = new Date(a.dateAjout).getTime();
      const dateB = new Date(b.dateAjout).getTime();
      return dateB - dateA; // Plus récent en premier
    });
  }
}