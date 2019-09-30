import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IBuilderPlate, defaultValue } from 'app/shared/model/convertapp/builder-plate.model';

export const ACTION_TYPES = {
  FETCH_BUILDERPLATE_LIST: 'builderPlate/FETCH_BUILDERPLATE_LIST',
  FETCH_BUILDERPLATE: 'builderPlate/FETCH_BUILDERPLATE',
  CREATE_BUILDERPLATE: 'builderPlate/CREATE_BUILDERPLATE',
  UPDATE_BUILDERPLATE: 'builderPlate/UPDATE_BUILDERPLATE',
  DELETE_BUILDERPLATE: 'builderPlate/DELETE_BUILDERPLATE',
  RESET: 'builderPlate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBuilderPlate>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BuilderPlateState = Readonly<typeof initialState>;

// Reducer

export default (state: BuilderPlateState = initialState, action): BuilderPlateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BUILDERPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BUILDERPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BUILDERPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_BUILDERPLATE):
    case REQUEST(ACTION_TYPES.DELETE_BUILDERPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BUILDERPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BUILDERPLATE):
    case FAILURE(ACTION_TYPES.CREATE_BUILDERPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_BUILDERPLATE):
    case FAILURE(ACTION_TYPES.DELETE_BUILDERPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUILDERPLATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUILDERPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BUILDERPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_BUILDERPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BUILDERPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'convertapp/api/builder-plates';

// Actions

export const getEntities: ICrudGetAllAction<IBuilderPlate> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BUILDERPLATE_LIST,
  payload: axios.get<IBuilderPlate>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBuilderPlate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BUILDERPLATE,
    payload: axios.get<IBuilderPlate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBuilderPlate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BUILDERPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBuilderPlate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BUILDERPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBuilderPlate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BUILDERPLATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
