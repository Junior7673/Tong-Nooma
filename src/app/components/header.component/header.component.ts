import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth-service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  menuOpen = false;
  unreadCount = 3; // Exemple
  isLoggedIn = false;

  constructor(private authService: AuthService,
      private router: Router

  ) {}

  ngOnInit() {
    this.isLoggedIn = this.authService.isAuthenticated(); // ou observable si tu veux le rendre réactif
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  openNotifications() {
    alert('Ouverture des notifications');
    this.unreadCount = 0;
  }

  logout() {
    if (confirm('Voulez-vous vraiment vous déconnecter ?')) {
      this.authService.logout();
      this.isLoggedIn = false;
     this.router.navigate(['/login']); // ✅ redirection forcée
    }
  }
}
