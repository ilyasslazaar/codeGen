import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBaseClass } from 'app/shared/model/convertapp/base-class.model';
import { getEntities as getBaseClasses } from 'app/entities/convertapp/base-class/base-class.reducer';
import { IRefCode } from 'app/shared/model/convertapp/ref-code.model';
import { getEntities as getRefCodes } from 'app/entities/convertapp/ref-code/ref-code.reducer';
import { getEntity, updateEntity, createEntity, reset } from './builder-plate.reducer';
import { IBuilderPlate } from 'app/shared/model/convertapp/builder-plate.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBuilderPlateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBuilderPlateUpdateState {
  isNew: boolean;
  baseClassId: string;
  refCodeId: string;
}

export class BuilderPlateUpdate extends React.Component<IBuilderPlateUpdateProps, IBuilderPlateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      baseClassId: '0',
      refCodeId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getBaseClasses();
    this.props.getRefCodes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { builderPlateEntity } = this.props;
      const entity = {
        ...builderPlateEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/builder-plate');
  };

  render() {
    const { builderPlateEntity, baseClasses, refCodes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatwayApp.convertappBuilderPlate.home.createOrEditLabel">Create or edit a BuilderPlate</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : builderPlateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="builder-plate-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="defaultCodeLabel" for="defaultCode">
                    Default Code
                  </Label>
                  <AvField id="builder-plate-defaultCode" type="text" name="defaultCode" />
                </AvGroup>
                <AvGroup>
                  <Label for="baseClass.id">Base Class</Label>
                  <AvInput id="builder-plate-baseClass" type="select" className="form-control" name="baseClassId">
                    <option value="" key="0" />
                    {baseClasses
                      ? baseClasses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="refCode.id">Ref Code</Label>
                  <AvInput id="builder-plate-refCode" type="select" className="form-control" name="refCodeId">
                    <option value="" key="0" />
                    {refCodes
                      ? refCodes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/builder-plate" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  baseClasses: storeState.baseClass.entities,
  refCodes: storeState.refCode.entities,
  builderPlateEntity: storeState.builderPlate.entity,
  loading: storeState.builderPlate.loading,
  updating: storeState.builderPlate.updating,
  updateSuccess: storeState.builderPlate.updateSuccess
});

const mapDispatchToProps = {
  getBaseClasses,
  getRefCodes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuilderPlateUpdate);
