import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActualiteComponent } from "../actualite.component/actualite.component";

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [CommonModule, ActualiteComponent],
  templateUrl: './home-component.html',
  styleUrls: ['./home-component.css']
})
export class HomeComponent {

}
