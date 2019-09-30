import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RefCode from './ref-code';
import RefCodeDetail from './ref-code-detail';
import RefCodeUpdate from './ref-code-update';
import RefCodeDeleteDialog from './ref-code-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RefCodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RefCodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RefCodeDetail} />
      <ErrorBoundaryRoute path={match.url} component={RefCode} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RefCodeDeleteDialog} />
  </>
);

export default Routes;
