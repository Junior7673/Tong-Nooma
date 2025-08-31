import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { HeaderComponent } from "./components/header.component/header.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule,HeaderComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'club';

  isLoginPage = false;

constructor(private router: Router) {
  this.router.events.subscribe(event => {
    if (event instanceof NavigationEnd) {
      const url = event.urlAfterRedirects || event.url;
      this.isLoginPage = url === '/login' || url === '/register';
    }
  });
}

}

