import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectorRef,
  signal,
  computed,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';


import { Actualite } from '../../interfaces/ActualiteInterface';
import { MediaInterface, MediaType } from '../../interfaces/Media-Interface/MediaInterface';
import { MediaFilterInterface } from '../../interfaces/Media-Interface/MediaFilterInterface';

import { ActualiteService } from '../../services/actualite-service';
import { MediaService } from '../../services/media-service';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home-component.html',
  styleUrls: ['./home-component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  // ✅ Actualités
  recentActualites: Actualite[] = [];
  actualitesSubscription?: Subscription;
  isLoadingNews = false;

  // ✅ Galerie
  private allMedias = signal<MediaInterface[]>([]);
  readonly currentFilter = signal<MediaFilterInterface>({ type: 'all', search: '' });
  readonly isLoading = signal(false);
  readonly selectedMedia = signal<MediaInterface | null>(null);
  readonly searchTerm = signal('');

  // ✅ Computed
  readonly filteredMedias = computed(() => {
    const medias = this.allMedias();
    const filter = this.currentFilter();
    return this.mediaService.filterMedias(medias, filter);
  });

  readonly totalMediasCount = computed(() => this.allMedias().length);
  readonly filteredMediasCount = computed(() => this.filteredMedias().length);
  readonly photosCount = computed(() =>
    this.allMedias().filter(m => m.type === MediaType.PHOTO).length
  );
  readonly videosCount = computed(() =>
    this.allMedias().filter(m => m.type === MediaType.VIDEO).length
  );

  private mediasSubscription?: Subscription;

  constructor(
    private actualiteService: ActualiteService,
    private mediaService: MediaService,
    private cdr: ChangeDetectorRef
  ) {}


  ngOnInit(): void {
    this.loadRecentActualites();
    this.loadMedias();
  }

  ngOnDestroy(): void {
    this.actualitesSubscription?.unsubscribe();
    this.mediasSubscription?.unsubscribe();
  }

  // ✅ Actualités
  loadRecentActualites(): void {
    this.isLoadingNews = true;
    this.actualitesSubscription = this.actualiteService.getAll().subscribe({
      next: (data) => {
        this.recentActualites = data
          .sort((a, b) => new Date(b.datePub).getTime() - new Date(a.datePub).getTime())
          .slice(0, 3);
        this.isLoadingNews = false;
      },
      error: (error) => {
        console.error('Erreur actualités:', error);
        this.isLoadingNews = false;
        this.cdr.detectChanges();
      }
    });
  }

  // ✅ Galerie
  loadMedias(): void {
    this.isLoading.set(true);
    this.mediasSubscription = this.mediaService.getAll().subscribe({
      next: (medias) => {
        const sorted = this.mediaService.sortByDateDesc(medias);
        this.allMedias.set(sorted);
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Erreur médias:', error);
        this.isLoading.set(false);
        this.cdr.detectChanges();
      }
    });
  }

  scrollToSection(sectionId: string): void {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
 /** Section galerie
  filterByType(type: 'all' | 'PHOTO' | 'VIDEO'): void {
    const current = this.currentFilter();
    this.currentFilter.set({ ...current, type });
  }

  updateSearch(term: string): void {
    this.searchTerm.set(term);
    const current = this.currentFilter();
    this.currentFilter.set({ ...current, search: term });
  }

  resetFilters(): void {
    this.filterByType('all');
    this.updateSearch('');
  }

  openMediaDetail(media: MediaInterface): void {
    this.selectedMedia.set(media);
    document.body.style.overflow = 'hidden';
  }

  closeMediaDetail(): void {
    this.selectedMedia.set(null);
    document.body.style.overflow = 'auto';
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = '/assets/images/placeholder.jpg';
  }

  isPhoto(media: MediaInterface): boolean {
    return this.mediaService.isPhoto(media);
  }

  isVideo(media: MediaInterface): boolean {
    return this.mediaService.isVideo(media);
  }

  getTypeLabel(type: 'PHOTO' | 'VIDEO'): string {
    return this.mediaService.getTypeLabel(type);
  }

  getTypeIcon(type: 'PHOTO' | 'VIDEO'): string {
    return this.mediaService.getTypeIcon(type);
  }

  formatDate(date?: Date | string): string {
    if (!date) return 'Date inconnue';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toLocaleDateString('fr-FR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.selectedMedia()) {
      this.closeMediaDetail();
    }
  }
*/
  
}
