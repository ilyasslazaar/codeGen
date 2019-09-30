import { IBuilderPlate } from 'app/shared/model/convertapp/builder-plate.model';

export interface IRefCode {
  id?: number;
  langage?: string;
  builderPlates?: IBuilderPlate[];
}

export const defaultValue: Readonly<IRefCode> = {};
