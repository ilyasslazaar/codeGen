import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fonctions.reducer';
import { IFonctions } from 'app/shared/model/convertapp/fonctions.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFonctionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FonctionsDetail extends React.Component<IFonctionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fonctionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Fonctions [<b>{fonctionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">Nom</span>
            </dt>
            <dd>{fonctionsEntity.nom}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{fonctionsEntity.type}</dd>
            <dt>Base Class</dt>
            <dd>{fonctionsEntity.baseClassId ? fonctionsEntity.baseClassId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/fonctions" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/fonctions/${fonctionsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ fonctions }: IRootState) => ({
  fonctionsEntity: fonctions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FonctionsDetail);
