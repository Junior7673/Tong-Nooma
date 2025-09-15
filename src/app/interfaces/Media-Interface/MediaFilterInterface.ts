export interface MediaFilterInterface {
  /** Filtrer par type de média */
  type?: 'PHOTO' | 'VIDEO' | 'all';
  
  /** Recherche par description */
  search?: string;
  
  /** Limite de résultats */
  limit?: number;
}