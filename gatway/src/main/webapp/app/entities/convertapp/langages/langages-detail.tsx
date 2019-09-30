import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './langages.reducer';
import { ILangages } from 'app/shared/model/convertapp/langages.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILangagesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LangagesDetail extends React.Component<ILangagesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { langagesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Langages [<b>{langagesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">Nom</span>
            </dt>
            <dd>{langagesEntity.nom}</dd>
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{langagesEntity.code}</dd>
            <dt>Base Class</dt>
            <dd>{langagesEntity.baseClassId ? langagesEntity.baseClassId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/langages" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/langages/${langagesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ langages }: IRootState) => ({
  langagesEntity: langages.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LangagesDetail);
