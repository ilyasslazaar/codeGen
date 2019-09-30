import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BuilderPlate from './builder-plate';
import BuilderPlateDetail from './builder-plate-detail';
import BuilderPlateUpdate from './builder-plate-update';
import BuilderPlateDeleteDialog from './builder-plate-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BuilderPlateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BuilderPlateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BuilderPlateDetail} />
      <ErrorBoundaryRoute path={match.url} component={BuilderPlate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BuilderPlateDeleteDialog} />
  </>
);

export default Routes;
