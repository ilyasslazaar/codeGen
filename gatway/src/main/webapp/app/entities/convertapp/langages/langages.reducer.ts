import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { ILangages, defaultValue } from 'app/shared/model/convertapp/langages.model';

export const ACTION_TYPES = {
  FETCH_LANGAGES_LIST: 'langages/FETCH_LANGAGES_LIST',
  FETCH_LANGAGES: 'langages/FETCH_LANGAGES',
  CREATE_LANGAGES: 'langages/CREATE_LANGAGES',
  UPDATE_LANGAGES: 'langages/UPDATE_LANGAGES',
  DELETE_LANGAGES: 'langages/DELETE_LANGAGES',
  RESET: 'langages/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILangages>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type LangagesState = Readonly<typeof initialState>;

// Reducer

export default (state: LangagesState = initialState, action): LangagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LANGAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LANGAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LANGAGES):
    case REQUEST(ACTION_TYPES.UPDATE_LANGAGES):
    case REQUEST(ACTION_TYPES.DELETE_LANGAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LANGAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LANGAGES):
    case FAILURE(ACTION_TYPES.CREATE_LANGAGES):
    case FAILURE(ACTION_TYPES.UPDATE_LANGAGES):
    case FAILURE(ACTION_TYPES.DELETE_LANGAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LANGAGES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LANGAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LANGAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_LANGAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LANGAGES):
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

const apiUrl = 'convertapp/api/langages';

// Actions

export const getEntities: ICrudGetAllAction<ILangages> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LANGAGES_LIST,
  payload: axios.get<ILangages>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ILangages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LANGAGES,
    payload: axios.get<ILangages>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILangages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LANGAGES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILangages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LANGAGES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILangages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LANGAGES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
