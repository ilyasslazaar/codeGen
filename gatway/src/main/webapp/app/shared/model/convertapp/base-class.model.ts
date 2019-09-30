import { IFonctions } from 'app/shared/model/convertapp/fonctions.model';
import { ILangages } from 'app/shared/model/convertapp/langages.model';
import { IProprietes } from 'app/shared/model/convertapp/proprietes.model';
import { IBuilderPlate } from 'app/shared/model/convertapp/builder-plate.model';

export interface IBaseClass {
  id?: number;
  nom?: string;
  imports?: string;
  fonctions?: IFonctions[];
  langages?: ILangages[];
  proprietes?: IProprietes[];
  builderPlates?: IBuilderPlate[];
}

export const defaultValue: Readonly<IBaseClass> = {};
