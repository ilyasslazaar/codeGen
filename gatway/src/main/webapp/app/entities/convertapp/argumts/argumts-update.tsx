import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFonctions } from 'app/shared/model/convertapp/fonctions.model';
import { getEntities as getFonctions } from 'app/entities/convertapp/fonctions/fonctions.reducer';
import { getEntity, updateEntity, createEntity, reset } from './argumts.reducer';
import { IArgumts } from 'app/shared/model/convertapp/argumts.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArgumtsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IArgumtsUpdateState {
  isNew: boolean;
  fonctionsId: string;
}

export class ArgumtsUpdate extends React.Component<IArgumtsUpdateProps, IArgumtsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      fonctionsId: '0',
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

    this.props.getFonctions();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { argumtsEntity } = this.props;
      const entity = {
        ...argumtsEntity,
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
    this.props.history.push('/entity/argumts');
  };

  render() {
    const { argumtsEntity, fonctions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="gatwayApp.convertappArgumts.home.createOrEditLabel">Create or edit a Argumts</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : argumtsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="argumts-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomLabel" for="nom">
                    Nom
                  </Label>
                  <AvField id="argumts-nom" type="text" name="nom" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="type">
                    Type
                  </Label>
                  <AvField id="argumts-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label for="fonctions.id">Fonctions</Label>
                  <AvInput id="argumts-fonctions" type="select" className="form-control" name="fonctionsId">
                    <option value="" key="0" />
                    {fonctions
                      ? fonctions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/argumts" replace color="info">
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
  fonctions: storeState.fonctions.entities,
  argumtsEntity: storeState.argumts.entity,
  loading: storeState.argumts.loading,
  updating: storeState.argumts.updating,
  updateSuccess: storeState.argumts.updateSuccess
});

const mapDispatchToProps = {
  getFonctions,
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
)(ArgumtsUpdate);
