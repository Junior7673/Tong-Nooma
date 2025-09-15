import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);
 
  menuOpen = false;
  unreadCount = 3;

  // ✅ Utiliser un signal manuel pour contrôler l'affichage
  private currentUrl = signal(this.router.url);

  showHeader = computed(() => {
    const url = this.currentUrl();
    return !['/login', '/register'].some(path => url.startsWith(path));
  });

  isLoggedIn = computed(() => this.authService.isAuthenticated());

  ngOnInit() {
    // ✅ Écouter les changements de navigation et mettre à jour le signal
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe(event => {
      this.currentUrl.set(event.urlAfterRedirects);
    });
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu() {
    this.menuOpen = false;
  }

  openNotifications() {
    alert('Ouverture des notifications');
    this.unreadCount = 0;
  }

  logout() {
    if (confirm('Voulez-vous vraiment vous déconnecter ?')) {
      this.authService.logout();
      this.router.navigate(['/login']);
    }
  }
}