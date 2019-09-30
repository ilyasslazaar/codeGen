import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Fonctions from './fonctions';
import FonctionsDetail from './fonctions-detail';
import FonctionsUpdate from './fonctions-update';
import FonctionsDeleteDialog from './fonctions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FonctionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FonctionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FonctionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Fonctions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FonctionsDeleteDialog} />
  </>
);

export default Routes;
