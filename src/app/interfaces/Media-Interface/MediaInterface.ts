export interface MediaInterface {
    id?: number;
    type: 'PHOTO' | 'VIDEO';
    url: string;
    description?: string;
    dateAjout: Date;

}

export enum MediaType {
  PHOTO = 'PHOTO',
  VIDEO = 'VIDEO'
}