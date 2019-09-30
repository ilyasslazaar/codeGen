import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Proprietes from './proprietes';
import ProprietesDetail from './proprietes-detail';
import ProprietesUpdate from './proprietes-update';
import ProprietesDeleteDialog from './proprietes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProprietesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProprietesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProprietesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Proprietes} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProprietesDeleteDialog} />
  </>
);

export default Routes;
