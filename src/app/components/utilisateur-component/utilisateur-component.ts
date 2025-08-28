import { Component, OnInit } from '@angular/core';
import { UtilisateurInterface } from '../../interfaces/UtilisateurInterface';
import { UtilisateurService } from '../../services/UtilisateurService';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-utilisateur-component',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './utilisateur-component.html',
  styleUrl: './utilisateur-component.css'
})
export class UtilisateurComponent implements OnInit{

  utilisateurs: UtilisateurInterface[] = [];
  selectedUser?: UtilisateurInterface;

  constructor(private utilisateurService: UtilisateurService) {}

  ngOnInit(): void {
    this.loadUtilisateurs();
  }

  loadUtilisateurs(): void {
    this.utilisateurService.getAll().subscribe(data => {
      this.utilisateurs = data;
    });
  }

  selectUser(user: UtilisateurInterface): void {
    this.selectedUser = { ...user };
  }

  saveUser(): void {
    if (this.selectedUser?.id) {
      this.utilisateurService.update(this.selectedUser.id, this.selectedUser).subscribe(() => {
        this.loadUtilisateurs();
        this.selectedUser = undefined;
      });
    } else {
      this.utilisateurService.create(this.selectedUser!).subscribe(() => {
        this.loadUtilisateurs();
        this.selectedUser = undefined;
      });
    }
  }

  deleteUser(id: number): void {
    if (confirm('Supprimer cet utilisateur ?')) {
      this.utilisateurService.delete(id).subscribe(() => {
        this.loadUtilisateurs();
      });
    }
  }

}
