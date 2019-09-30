import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './builder-plate.reducer';
import { IBuilderPlate } from 'app/shared/model/convertapp/builder-plate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuilderPlateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BuilderPlateDetail extends React.Component<IBuilderPlateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { builderPlateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            BuilderPlate [<b>{builderPlateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="defaultCode">Default Code</span>
            </dt>
            <dd>{builderPlateEntity.defaultCode}</dd>
            <dt>Base Class</dt>
            <dd>{builderPlateEntity.baseClassId ? builderPlateEntity.baseClassId : ''}</dd>
            <dt>Ref Code</dt>
            <dd>{builderPlateEntity.refCodeId ? builderPlateEntity.refCodeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/builder-plate" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/builder-plate/${builderPlateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ builderPlate }: IRootState) => ({
  builderPlateEntity: builderPlate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuilderPlateDetail);
