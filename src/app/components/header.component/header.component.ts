import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth-service';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  menuOpen = false;
  unreadCount = 3;
  isLoggedIn = false;
  showHeader = true;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    // État initial
    this.isLoggedIn = this.authService.isAuthenticated();

    // Cacher le header sur certaines routes
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        const hiddenRoutes = ['/login', '/register'];
        this.showHeader = !hiddenRoutes.includes(event.urlAfterRedirects);
        this.isLoggedIn = this.authService.isAuthenticated();
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
      this.isLoggedIn = false;
      this.router.navigate(['/login']);
    }
  }
}
