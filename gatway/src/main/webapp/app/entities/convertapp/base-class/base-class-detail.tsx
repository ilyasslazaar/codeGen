import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './base-class.reducer';
import { IBaseClass } from 'app/shared/model/convertapp/base-class.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaseClassDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BaseClassDetail extends React.Component<IBaseClassDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { baseClassEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            BaseClass [<b>{baseClassEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">Nom</span>
            </dt>
            <dd>{baseClassEntity.nom}</dd>
            <dt>
              <span id="imports">Imports</span>
            </dt>
            <dd>{baseClassEntity.imports}</dd>
          </dl>
          <Button tag={Link} to="/entity/base-class" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/base-class/${baseClassEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ baseClass }: IRootState) => ({
  baseClassEntity: baseClass.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BaseClassDetail);
