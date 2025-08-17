export interface UtilisateurInterface {
    id?: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  password: string;
  role?: 'ADMIN' | 'UTILISATEUR';
  dateInscrip: Date;
  //enseignant?: Enseignant; // optionnel car peut être null
  //actualite?: Actualite[];
  //inscription?: InscriptionGrade[];
}
