// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth-service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // Vérification authentification
    if (!this.authService.isAuthenticated()) {
      // Redirection vers login avec retour vers la page demandée
      this.router.navigate(['/nolay/login'], { queryParams: { returnUrl: state.url } });
      return false;
    }

    // Vérification des rôles si précisés dans les routes
    const expectedRoles: string[] = route.data['roles'];
    const userRole = this.authService.getUserRole();

    if (expectedRoles && !expectedRoles.includes(userRole)) {
      this.router.navigate(['/forbidden']);
      return false;
    }

    return true;
  }
}
