import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IArgumts, defaultValue } from 'app/shared/model/convertapp/argumts.model';

export const ACTION_TYPES = {
  FETCH_ARGUMTS_LIST: 'argumts/FETCH_ARGUMTS_LIST',
  FETCH_ARGUMTS: 'argumts/FETCH_ARGUMTS',
  CREATE_ARGUMTS: 'argumts/CREATE_ARGUMTS',
  UPDATE_ARGUMTS: 'argumts/UPDATE_ARGUMTS',
  DELETE_ARGUMTS: 'argumts/DELETE_ARGUMTS',
  RESET: 'argumts/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArgumts>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ArgumtsState = Readonly<typeof initialState>;

// Reducer

export default (state: ArgumtsState = initialState, action): ArgumtsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARGUMTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARGUMTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARGUMTS):
    case REQUEST(ACTION_TYPES.UPDATE_ARGUMTS):
    case REQUEST(ACTION_TYPES.DELETE_ARGUMTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ARGUMTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARGUMTS):
    case FAILURE(ACTION_TYPES.CREATE_ARGUMTS):
    case FAILURE(ACTION_TYPES.UPDATE_ARGUMTS):
    case FAILURE(ACTION_TYPES.DELETE_ARGUMTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARGUMTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARGUMTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARGUMTS):
    case SUCCESS(ACTION_TYPES.UPDATE_ARGUMTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARGUMTS):
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

const apiUrl = 'convertapp/api/argumts';

// Actions

export const getEntities: ICrudGetAllAction<IArgumts> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ARGUMTS_LIST,
  payload: axios.get<IArgumts>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IArgumts> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARGUMTS,
    payload: axios.get<IArgumts>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IArgumts> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARGUMTS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArgumts> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARGUMTS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArgumts> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARGUMTS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
