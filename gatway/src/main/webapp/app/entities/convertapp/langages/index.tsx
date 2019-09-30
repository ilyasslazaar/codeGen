import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Langages from './langages';
import LangagesDetail from './langages-detail';
import LangagesUpdate from './langages-update';
import LangagesDeleteDialog from './langages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LangagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LangagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LangagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Langages} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LangagesDeleteDialog} />
  </>
);

export default Routes;
