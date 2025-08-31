export interface LoginResponse {
  token: string;
  role: 'ADMIN' | 'UTILISATEUR';
  expiresAt: number;
}