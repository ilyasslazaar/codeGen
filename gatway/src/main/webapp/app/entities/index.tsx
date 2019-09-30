import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Argumts from './convertapp/argumts';
import BaseClass from './convertapp/base-class';
import BuilderPlate from './convertapp/builder-plate';
import Fonctions from './convertapp/fonctions';
import Langages from './convertapp/langages';
import Proprietes from './convertapp/proprietes';
import RefCode from './convertapp/ref-code';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/argumts`} component={Argumts} />
      <ErrorBoundaryRoute path={`${match.url}/base-class`} component={BaseClass} />
      <ErrorBoundaryRoute path={`${match.url}/builder-plate`} component={BuilderPlate} />
      <ErrorBoundaryRoute path={`${match.url}/fonctions`} component={Fonctions} />
      <ErrorBoundaryRoute path={`${match.url}/langages`} component={Langages} />
      <ErrorBoundaryRoute path={`${match.url}/proprietes`} component={Proprietes} />
      <ErrorBoundaryRoute path={`${match.url}/ref-code`} component={RefCode} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
