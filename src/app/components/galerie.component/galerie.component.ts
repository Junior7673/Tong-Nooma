import { ChangeDetectorRef, Component, computed, OnDestroy, OnInit, signal } from '@angular/core';
import { MediaInterface, MediaType } from '../../interfaces/Media-Interface/MediaInterface';
import { MediaFilterInterface } from '../../interfaces/Media-Interface/MediaFilterInterface';
import { Subscription } from 'rxjs/internal/Subscription';
import { MediaService } from '../../services/media-service';

@Component({
  selector: 'app-galerie.component',
  imports: [],
  templateUrl: './galerie.component.html',
  styleUrl: './galerie.component.css'
})
export class GalerieComponent implements OnInit, OnDestroy {
  // ✅ Signaux pour la gestion d'état réactive
  /** Liste complète des médias chargés depuis l'API */
  private allMedias = signal<MediaInterface[]>([]);
  
  /** Filtres appliqués actuellement */
readonly currentFilter = signal<MediaFilterInterface>({ type: 'all', search: '' });

  // ✅ Computed signals pour les données dérivées
  /** Médias filtrés selon les critères actuels */
  filteredMedias = computed(() => {
    const medias = this.allMedias();
    const filter = this.currentFilter();
    return this.mediaService.filterMedias(medias, filter);
  });

  /** Nombre total de médias disponibles */
  totalMediasCount = computed(() => this.allMedias().length);

  /** Nombre de médias après filtrage */
  filteredMediasCount = computed(() => this.filteredMedias().length);

  /** Nombre de photos */
  photosCount = computed(() => 
    this.allMedias().filter(media => media.type === MediaType.PHOTO).length
  );

  /** Nombre de vidéos */
  videosCount = computed(() => 
    this.allMedias().filter(media => media.type === MediaType.VIDEO).length
  );

  // ✅ États du composant
  /** Indicateur de chargement */
  isLoading = signal(false);
  
  /** Média actuellement sélectionné pour la vue détaillée */
  selectedMedia = signal<MediaInterface | null>(null);
  
  /** Terme de recherche saisi par l'utilisateur */
  searchTerm = signal('');

  // ✅ Gestion des abonnements
  private mediasSubscription?: Subscription;

  constructor(
    private mediaService: MediaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadMedias();
  }

  ngOnDestroy(): void {
    // ✅ Nettoyage des abonnements pour éviter les fuites mémoire
    this.mediasSubscription?.unsubscribe();
  }

  /**
   * Charge tous les médias depuis l'API
   * @private
   */
  private loadMedias(): void {
    this.isLoading.set(true);

    this.mediasSubscription = this.mediaService.getAll().subscribe({
      next: (medias) => {
        // ✅ Trier par date d'ajout décroissante (plus récent en premier)
        const sortedMedias = this.mediaService.sortByDateDesc(medias);

        this.allMedias.set(sortedMedias);
        this.isLoading.set(false);
        console.log('Médias chargés:', sortedMedias);
        console.log(`Photos: ${this.photosCount()}, Vidéos: ${this.videosCount()}`);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des médias:', error);
        this.isLoading.set(false);
        this.cdr.detectChanges();
      }
    });
  }

  /**
   * Met à jour le filtre de type de média
   * @param type Type de média à filtrer ('all', 'PHOTO', 'VIDEO')
   */
  filterByType(type: 'all' | 'PHOTO' | 'VIDEO'): void {
    const currentFilterValue = this.currentFilter();
    this.currentFilter.set({
      ...currentFilterValue,
      type: type
    });
  }

  /**
   * Met à jour la recherche textuelle
   * @param searchTerm Terme de recherche
   */
  updateSearch(searchTerm: string): void {
    this.searchTerm.set(searchTerm);
    const currentFilterValue = this.currentFilter();
    this.currentFilter.set({
      ...currentFilterValue,
      search: searchTerm
    });
  }

  /**
   * Réinitialise tous les filtres
   */
  resetFilters(): void {
    this.filterByType('all');
    this.updateSearch('');
  }

  /**
   * Ouvre la vue détaillée d'un média
   * @param media Média à afficher en détail
   */
  openMediaDetail(media: MediaInterface): void {
    this.selectedMedia.set(media);
    // ✅ Empêcher le scroll de la page en arrière-plan
    document.body.style.overflow = 'hidden';
  }

  /**
   * Ferme la vue détaillée
   */
  closeMediaDetail(): void {
    this.selectedMedia.set(null);
    // ✅ Restaurer le scroll de la page
    document.body.style.overflow = 'auto';
  }

  /**
   * Gère les erreurs de chargement d'images
   * @param event Événement d'erreur
   */
  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/images/placeholder-media.jpg'; // Image par défaut
  }

  /**
   * Détermine si un média est une photo
   * @param media Média à vérifier
   * @returns true si c'est une photo
   */
  isPhoto(media: MediaInterface): boolean {
    return this.mediaService.isPhoto(media);
  }

  /**
   * Détermine si un média est une vidéo
   * @param media Média à vérifier
   * @returns true si c'est une vidéo
   */
  isVideo(media: MediaInterface): boolean {
    return this.mediaService.isVideo(media);
  }

  /**
   * Obtient le label d'un type de média
   * @param type Type de média
   * @returns Label avec icône
   */
  getTypeLabel(type: 'PHOTO' | 'VIDEO'): string {
    return this.mediaService.getTypeLabel(type);
  }

  /**
   * Obtient l'icône d'un type de média
   * @param type Type de média
   * @returns Emoji d'icône
   */
  getTypeIcon(type: 'PHOTO' | 'VIDEO'): string {
    return this.mediaService.getTypeIcon(type);
  }

  /**
   * Gère l'événement de fermeture avec la touche Échap
   * @param event Événement clavier
   */
  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.selectedMedia()) {
      this.closeMediaDetail();
    }
  }

  /**
   * Formate une date pour l'affichage
   * @param date Date à formater
   * @returns Date formatée
   */
  formatDate(date?: Date | string): string {
  if (!date) return 'Date inconnue';
  const dateObj = typeof date === 'string' ? new Date(date) : date;
  return dateObj.toLocaleDateString('fr-FR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
}

}