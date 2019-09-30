import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IFonctions, defaultValue } from 'app/shared/model/convertapp/fonctions.model';

export const ACTION_TYPES = {
  FETCH_FONCTIONS_LIST: 'fonctions/FETCH_FONCTIONS_LIST',
  FETCH_FONCTIONS: 'fonctions/FETCH_FONCTIONS',
  CREATE_FONCTIONS: 'fonctions/CREATE_FONCTIONS',
  UPDATE_FONCTIONS: 'fonctions/UPDATE_FONCTIONS',
  DELETE_FONCTIONS: 'fonctions/DELETE_FONCTIONS',
  RESET: 'fonctions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFonctions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FonctionsState = Readonly<typeof initialState>;

// Reducer

export default (state: FonctionsState = initialState, action): FonctionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FONCTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FONCTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FONCTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_FONCTIONS):
    case REQUEST(ACTION_TYPES.DELETE_FONCTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FONCTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FONCTIONS):
    case FAILURE(ACTION_TYPES.CREATE_FONCTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_FONCTIONS):
    case FAILURE(ACTION_TYPES.DELETE_FONCTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FONCTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FONCTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FONCTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_FONCTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FONCTIONS):
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

const apiUrl = 'convertapp/api/fonctions';

// Actions

export const getEntities: ICrudGetAllAction<IFonctions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FONCTIONS_LIST,
  payload: axios.get<IFonctions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFonctions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FONCTIONS,
    payload: axios.get<IFonctions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFonctions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FONCTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFonctions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FONCTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFonctions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FONCTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
