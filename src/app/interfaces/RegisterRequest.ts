export interface RegisterRequest {
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  password: string;
  role?: 'ADMIN' | 'UTILISATEUR';
  dateInscrip: Date;
}
