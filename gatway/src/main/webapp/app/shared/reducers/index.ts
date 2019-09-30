import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import argumts, {
  ArgumtsState
} from 'app/entities/convertapp/argumts/argumts.reducer';
// prettier-ignore
import baseClass, {
  BaseClassState
} from 'app/entities/convertapp/base-class/base-class.reducer';
// prettier-ignore
import builderPlate, {
  BuilderPlateState
} from 'app/entities/convertapp/builder-plate/builder-plate.reducer';
// prettier-ignore
import fonctions, {
  FonctionsState
} from 'app/entities/convertapp/fonctions/fonctions.reducer';
// prettier-ignore
import langages, {
  LangagesState
} from 'app/entities/convertapp/langages/langages.reducer';
// prettier-ignore
import proprietes, {
  ProprietesState
} from 'app/entities/convertapp/proprietes/proprietes.reducer';
// prettier-ignore
import refCode, {
  RefCodeState
} from 'app/entities/convertapp/ref-code/ref-code.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly argumts: ArgumtsState;
  readonly baseClass: BaseClassState;
  readonly builderPlate: BuilderPlateState;
  readonly fonctions: FonctionsState;
  readonly langages: LangagesState;
  readonly proprietes: ProprietesState;
  readonly refCode: RefCodeState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  argumts,
  baseClass,
  builderPlate,
  fonctions,
  langages,
  proprietes,
  refCode,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
