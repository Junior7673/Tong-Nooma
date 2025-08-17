export interface LoginInterface{
      id?: number;
      email: string;
      password: string;

      role?: 'ADMIN' | 'UTILISATEUR'; // facultatif pour l'inscription

}