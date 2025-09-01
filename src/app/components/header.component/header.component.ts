import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { filter, map } from 'rxjs/operators';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  menuOpen = false;
  unreadCount = 3;

  // 🔥 Ici on ne garde que NavigationEnd et on map vers son urlAfterRedirects (string)
  private navigationEnd$ = this.router.events.pipe(
    filter((event): event is NavigationEnd => event instanceof NavigationEnd),
    map(event => event.urlAfterRedirects)
  );
  private currentUrl = toSignal(this.navigationEnd$, { initialValue: this.router.url });

  showHeader = computed(() => {
    const url = this.currentUrl() ?? '';
    return !['/login', '/register'].some(path => url.startsWith(path));
  });

  isLoggedIn = computed(() => this.authService.isAuthenticated());

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
