export interface IBuilderPlate {
  id?: number;
  defaultCode?: string;
  baseClassId?: number;
  refCodeId?: number;
}

export const defaultValue: Readonly<IBuilderPlate> = {};
