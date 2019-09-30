import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/argumts">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Argumts
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/base-class">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Base Class
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/builder-plate">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Builder Plate
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/fonctions">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Fonctions
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/langages">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Langages
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/proprietes">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Proprietes
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ref-code">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Ref Code
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
