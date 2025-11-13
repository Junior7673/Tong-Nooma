import { Component, HostListener, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

/**
 * Composant de navigation avec indicateur de progression
 * Affiche quelle section est visible et permet la navigation fluide
 */
@Component({
  selector: 'app-scroll-progress',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <!-- Barre de progression en haut -->
    <div class="scroll-progress">
      <div class="scroll-progress__bar" [style.width.%]="scrollProgress()"></div>
    </div>

    <!-- Indicateur de section -->
    <div class="section-indicator" *ngIf="currentSection()">
      <div class="section-indicator__content">
        <span class="section-indicator__icon">{{ getSectionIcon(currentSection()) }}</span>
        <span class="section-indicator__name">{{ currentSection() }}</span>
        <span class="section-indicator__progress">{{ sectionProgress() }}%</span>
      </div>
    </div>

    <!-- Navigation rapide (points) -->
    <nav class="quick-nav">
      @for (section of sections; track section.id) {
        <button 
          class="quick-nav__dot"
          [class.active]="currentSection() === section.name"
          (click)="scrollToSection(section.id)"
          [attr.aria-label]="'Aller à ' + section.name"
          [title]="section.name">
          <span class="quick-nav__tooltip">{{ section.name }}</span>
        </button>
      }
    </nav>

    <!-- Bouton retour en haut -->
    <button 
      class="scroll-top"
      *ngIf="showScrollTop()"
      (click)="scrollToTop()"
      aria-label="Retour en haut">
      ↑
    </button>
  `,
  styles: [`
    /* ===== BARRE DE PROGRESSION ===== */
    .scroll-progress {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 4px;
      background: rgba(255, 255, 255, 0.1);
      z-index: 9999;
    }

    .scroll-progress__bar {
      height: 100%;
      background: linear-gradient(90deg, #00b4d8, #142850);
      transition: width 0.1s ease-out;
      box-shadow: 0 0 10px rgba(0, 180, 216, 0.5);
    }

    /* ===== INDICATEUR DE SECTION ===== */
    .section-indicator {
      position: fixed;
      top: 80px;
      right: 30px;
      background: rgba(255, 255, 255, 0.95);
      backdrop-filter: blur(10px);
      padding: 15px 25px;
      border-radius: 30px;
      box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
      z-index: 9998;
      animation: slideIn 0.4s ease;
    }

    @keyframes slideIn {
      from { opacity: 0; transform: translateX(100px); }
      to { opacity: 1; transform: translateX(0); }
    }

    .section-indicator__content {
      display: flex;
      align-items: center;
      gap: 12px;
      font-weight: 600;
      color: #142850;
    }

    .section-indicator__icon {
      font-size: 1.5rem;
    }

    .section-indicator__name {
      font-size: 1rem;
    }

    .section-indicator__progress {
      background: #142850;
      color: white;
      padding: 4px 12px;
      border-radius: 15px;
      font-size: 0.85rem;
    }

    /* ===== NAVIGATION RAPIDE ===== */
    .quick-nav {
      position: fixed;
      right: 30px;
      top: 50%;
      transform: translateY(-50%);
      display: flex;
      flex-direction: column;
      gap: 15px;
      z-index: 9997;
    }

    .quick-nav__dot {
      width: 12px;
      height: 12px;
      border-radius: 50%;
      border: 2px solid #142850;
      background: white;
      cursor: pointer;
      transition: all 0.3s ease;
      position: relative;
      padding: 0;
    }

    .quick-nav__dot:hover {
      transform: scale(1.5);
      background: #00b4d8;
      border-color: #00b4d8;
    }

    .quick-nav__dot.active {
      background: #142850;
      transform: scale(1.3);
    }

    .quick-nav__tooltip {
      position: absolute;
      right: 30px;
      top: 50%;
      transform: translateY(-50%);
      background: #142850;
      color: white;
      padding: 6px 12px;
      border-radius: 6px;
      font-size: 0.85rem;
      white-space: nowrap;
      opacity: 0;
      pointer-events: none;
      transition: opacity 0.3s ease;
    }

    .quick-nav__dot:hover .quick-nav__tooltip {
      opacity: 1;
    }

    /* ===== BOUTON RETOUR EN HAUT ===== */
    .scroll-top {
      position: fixed;
      bottom: 30px;
      right: 30px;
      width: 55px;
      height: 55px;
      background: #142850;
      color: white;
      border: none;
      border-radius: 50%;
      font-size: 1.8rem;
      cursor: pointer;
      box-shadow: 0 8px 25px rgba(20, 40, 80, 0.3);
      transition: all 0.3s ease;
      z-index: 9996;
      animation: fadeInUp 0.4s ease;
    }

    .scroll-top:hover {
      background: #00b4d8;
      transform: translateY(-5px);
      box-shadow: 0 12px 35px rgba(0, 180, 216, 0.4);
    }

    @keyframes fadeInUp {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    /* ===== RESPONSIVE ===== */
    @media (max-width: 768px) {
      .section-indicator {
        top: 70px;
        right: 15px;
        padding: 10px 15px;
      }

      .section-indicator__content {
        gap: 8px;
      }

      .section-indicator__icon {
        font-size: 1.2rem;
      }

      .section-indicator__name {
        font-size: 0.85rem;
      }

      .quick-nav {
        display: none;
      }

      .scroll-top {
        width: 45px;
        height: 45px;
        bottom: 20px;
        right: 20px;
        font-size: 1.4rem;
      }
    }
  `]
})
export class ScrollProgressComponent implements OnInit {
  // ✅ Signaux pour la réactivité
  scrollProgress = signal(0);
  currentSection = signal('');
  sectionProgress = signal(0);
  showScrollTop = signal(false);

  // ✅ Sections de la page (à adapter selon vos besoins)
  sections = [
    { id: 'hero', name: 'Accueil' },
    { id: 'about', name: 'À propos' },
    { id: 'news', name: 'Actualités' },
    { id: 'programs', name: 'Programmes' },
    { id: 'gallery', name: 'Galerie' },
    { id: 'contact', name: 'Contact' }
  ];

  ngOnInit(): void {
    // Calculer au chargement
    this.updateProgress();
  }

  /**
   * Écoute le scroll de la fenêtre
   */
  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    this.updateProgress();
  }

  /**
   * Met à jour la progression et détecte la section courante
   */
  private updateProgress(): void {
    const windowHeight = window.innerHeight;
    const documentHeight = document.documentElement.scrollHeight;
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

    // ✅ Progression globale
    const totalScroll = documentHeight - windowHeight;
    const progress = totalScroll > 0 ? (scrollTop / totalScroll) * 100 : 0;
    this.scrollProgress.set(Math.min(Math.round(progress), 100));

    // ✅ Afficher le bouton "retour en haut"
    this.showScrollTop.set(scrollTop > 400);

    // ✅ Détecter la section visible
    this.detectCurrentSection(scrollTop, windowHeight);
  }

  /**
   * Détecte quelle section est actuellement visible
   */
  private detectCurrentSection(scrollTop: number, windowHeight: number): void {
    const offset = 150; // Décalage pour le header

    for (const section of this.sections) {
      const element = document.getElementById(section.id);
      if (!element) continue;

      const rect = element.getBoundingClientRect();
      const elementTop = rect.top + scrollTop;
      const elementBottom = elementTop + element.offsetHeight;

      // ✅ La section est visible
      if (scrollTop + offset >= elementTop && scrollTop + offset < elementBottom) {
        this.currentSection.set(section.name);

        // ✅ Calculer la progression dans cette section
        const sectionScrolled = (scrollTop + offset - elementTop);
        const sectionHeight = element.offsetHeight;
        const sectionProg = (sectionScrolled / sectionHeight) * 100;
        this.sectionProgress.set(Math.min(Math.round(sectionProg), 100));
        break;
      }
    }
  }

  /**
   * Fait défiler vers une section spécifique
   */
  scrollToSection(sectionId: string): void {
    const element = document.getElementById(sectionId);
    if (element) {
      const offsetTop = element.offsetTop - 100; // Décalage pour le header
      window.scrollTo({
        top: offsetTop,
        behavior: 'smooth'
      });
    }
  }

  /**
   * Retour en haut de page
   */
  scrollToTop(): void {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }

  /**
   * Obtient l'icône correspondant à une section
   */
  getSectionIcon(sectionName: string): string {
    const icons: Record<string, string> = {
      'Accueil': '🏠',
      'À propos': 'ℹ️',
      'Actualités': '📰',
      'Programmes': '📅',
      'Galerie': '🖼️',
      'Contact': '📞'
    };
    return icons[sectionName] || '📍';
  }
}