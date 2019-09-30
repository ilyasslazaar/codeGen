import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './proprietes.reducer';
import { IProprietes } from 'app/shared/model/convertapp/proprietes.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProprietesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProprietesDetail extends React.Component<IProprietesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { proprietesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Proprietes [<b>{proprietesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">Nom</span>
            </dt>
            <dd>{proprietesEntity.nom}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{proprietesEntity.type}</dd>
            <dt>Base Class</dt>
            <dd>{proprietesEntity.baseClassId ? proprietesEntity.baseClassId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/proprietes" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/proprietes/${proprietesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ proprietes }: IRootState) => ({
  proprietesEntity: proprietes.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProprietesDetail);
