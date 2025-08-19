import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  menuOpen = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // ✅ Toggle du menu responsive
  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  // ✅ Déconnexion gouvernée
  logout(): void {
    this.authService.logout(); // méthode à implémenter dans AuthService
    this.router.navigate(['/login']); // redirection vers la route login
  }
}
