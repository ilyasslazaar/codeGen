import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IProprietes, defaultValue } from 'app/shared/model/convertapp/proprietes.model';

export const ACTION_TYPES = {
  FETCH_PROPRIETES_LIST: 'proprietes/FETCH_PROPRIETES_LIST',
  FETCH_PROPRIETES: 'proprietes/FETCH_PROPRIETES',
  CREATE_PROPRIETES: 'proprietes/CREATE_PROPRIETES',
  UPDATE_PROPRIETES: 'proprietes/UPDATE_PROPRIETES',
  DELETE_PROPRIETES: 'proprietes/DELETE_PROPRIETES',
  RESET: 'proprietes/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProprietes>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProprietesState = Readonly<typeof initialState>;

// Reducer

export default (state: ProprietesState = initialState, action): ProprietesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROPRIETES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROPRIETES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROPRIETES):
    case REQUEST(ACTION_TYPES.UPDATE_PROPRIETES):
    case REQUEST(ACTION_TYPES.DELETE_PROPRIETES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROPRIETES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROPRIETES):
    case FAILURE(ACTION_TYPES.CREATE_PROPRIETES):
    case FAILURE(ACTION_TYPES.UPDATE_PROPRIETES):
    case FAILURE(ACTION_TYPES.DELETE_PROPRIETES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPRIETES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPRIETES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROPRIETES):
    case SUCCESS(ACTION_TYPES.UPDATE_PROPRIETES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROPRIETES):
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

const apiUrl = 'convertapp/api/proprietes';

// Actions

export const getEntities: ICrudGetAllAction<IProprietes> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROPRIETES_LIST,
  payload: axios.get<IProprietes>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProprietes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROPRIETES,
    payload: axios.get<IProprietes>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProprietes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROPRIETES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProprietes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROPRIETES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProprietes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROPRIETES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
