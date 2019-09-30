import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './argumts.reducer';
import { IArgumts } from 'app/shared/model/convertapp/argumts.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArgumtsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ArgumtsDetail extends React.Component<IArgumtsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { argumtsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Argumts [<b>{argumtsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">Nom</span>
            </dt>
            <dd>{argumtsEntity.nom}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{argumtsEntity.type}</dd>
            <dt>Fonctions</dt>
            <dd>{argumtsEntity.fonctionsId ? argumtsEntity.fonctionsId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/argumts" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/argumts/${argumtsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ argumts }: IRootState) => ({
  argumtsEntity: argumts.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ArgumtsDetail);
