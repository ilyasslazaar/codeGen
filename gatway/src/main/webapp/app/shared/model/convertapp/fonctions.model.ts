import { IArgumts } from 'app/shared/model/convertapp/argumts.model';

export interface IFonctions {
  id?: number;
  nom?: string;
  type?: string;
  argumts?: IArgumts[];
  baseClassId?: number;
}

export const defaultValue: Readonly<IFonctions> = {};
