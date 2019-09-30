import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BaseClass from './base-class';
import BaseClassDetail from './base-class-detail';
import BaseClassUpdate from './base-class-update';
import BaseClassDeleteDialog from './base-class-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BaseClassUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BaseClassUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BaseClassDetail} />
      <ErrorBoundaryRoute path={match.url} component={BaseClass} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BaseClassDeleteDialog} />
  </>
);

export default Routes;
