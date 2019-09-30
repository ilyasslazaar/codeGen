import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IBaseClass, defaultValue } from 'app/shared/model/convertapp/base-class.model';

export const ACTION_TYPES = {
  FETCH_BASECLASS_LIST: 'baseClass/FETCH_BASECLASS_LIST',
  FETCH_BASECLASS: 'baseClass/FETCH_BASECLASS',
  CREATE_BASECLASS: 'baseClass/CREATE_BASECLASS',
  UPDATE_BASECLASS: 'baseClass/UPDATE_BASECLASS',
  DELETE_BASECLASS: 'baseClass/DELETE_BASECLASS',
  RESET: 'baseClass/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBaseClass>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BaseClassState = Readonly<typeof initialState>;

// Reducer

export default (state: BaseClassState = initialState, action): BaseClassState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BASECLASS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BASECLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BASECLASS):
    case REQUEST(ACTION_TYPES.UPDATE_BASECLASS):
    case REQUEST(ACTION_TYPES.DELETE_BASECLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BASECLASS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BASECLASS):
    case FAILURE(ACTION_TYPES.CREATE_BASECLASS):
    case FAILURE(ACTION_TYPES.UPDATE_BASECLASS):
    case FAILURE(ACTION_TYPES.DELETE_BASECLASS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASECLASS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASECLASS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BASECLASS):
    case SUCCESS(ACTION_TYPES.UPDATE_BASECLASS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BASECLASS):
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

const apiUrl = 'convertapp/api/base-classes';

// Actions

export const getEntities: ICrudGetAllAction<IBaseClass> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BASECLASS_LIST,
  payload: axios.get<IBaseClass>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBaseClass> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BASECLASS,
    payload: axios.get<IBaseClass>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBaseClass> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BASECLASS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBaseClass> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BASECLASS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBaseClass> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BASECLASS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
