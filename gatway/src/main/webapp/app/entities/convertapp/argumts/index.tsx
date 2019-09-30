import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Argumts from './argumts';
import ArgumtsDetail from './argumts-detail';
import ArgumtsUpdate from './argumts-update';
import ArgumtsDeleteDialog from './argumts-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArgumtsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArgumtsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArgumtsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Argumts} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ArgumtsDeleteDialog} />
  </>
);

export default Routes;
