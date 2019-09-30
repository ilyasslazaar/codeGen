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
import { getEntity, updateEntity, createEntity, reset } from './langages.reducer';
import { ILangages } from 'app/shared/model/convertapp/langages.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILangagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILangagesUpdateState {
  isNew: boolean;
  baseClassId: string;
}

export class LangagesUpdate extends React.Component<ILangagesUpdateProps, ILangagesUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      baseClassId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { langagesEntity } = this.props;
      const entity = {
        ...langagesEntity,
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
    this.props.history.push('/entity/langages');
  };

  render() {
    const { langagesEntity, baseClasses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatwayApp.convertappLangages.home.createOrEditLabel">Create or edit a Langages</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : langagesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="langages-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomLabel" for="nom">
                    Nom
                  </Label>
                  <AvField id="langages-nom" type="text" name="nom" />
                </AvGroup>
                <AvGroup>
                  <Label id="codeLabel" for="code">
                    Code
                  </Label>
                  <AvField id="langages-code" type="text" name="code" />
                </AvGroup>
                <AvGroup>
                  <Label for="baseClass.id">Base Class</Label>
                  <AvInput id="langages-baseClass" type="select" className="form-control" name="baseClassId">
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
                <Button tag={Link} id="cancel-save" to="/entity/langages" replace color="info">
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
  langagesEntity: storeState.langages.entity,
  loading: storeState.langages.loading,
  updating: storeState.langages.updating,
  updateSuccess: storeState.langages.updateSuccess
});

const mapDispatchToProps = {
  getBaseClasses,
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
)(LangagesUpdate);
