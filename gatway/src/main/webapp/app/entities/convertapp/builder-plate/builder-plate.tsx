import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './builder-plate.reducer';
import { IBuilderPlate } from 'app/shared/model/convertapp/builder-plate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuilderPlateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BuilderPlate extends React.Component<IBuilderPlateProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { builderPlateList, match } = this.props;
    return (
      <div>
        <h2 id="builder-plate-heading">
          Builder Plates
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Builder Plate
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Default Code</th>
                <th>Base Class</th>
                <th>Ref Code</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {builderPlateList.map((builderPlate, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${builderPlate.id}`} color="link" size="sm">
                      {builderPlate.id}
                    </Button>
                  </td>
                  <td>{builderPlate.defaultCode}</td>
                  <td>
                    {builderPlate.baseClassId ? <Link to={`base-class/${builderPlate.baseClassId}`}>{builderPlate.baseClassId}</Link> : ''}
                  </td>
                  <td>{builderPlate.refCodeId ? <Link to={`ref-code/${builderPlate.refCodeId}`}>{builderPlate.refCodeId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${builderPlate.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${builderPlate.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${builderPlate.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ builderPlate }: IRootState) => ({
  builderPlateList: builderPlate.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuilderPlate);
