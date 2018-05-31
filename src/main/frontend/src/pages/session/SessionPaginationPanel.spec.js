import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SessionPaginationPanel from './SessionPaginationPanel'

describe('SessionPaginationPanel', () => {
    describe('render', () => {
        it('should render pagination panel', () => {
            const wrapper = shallow(<SessionPaginationPanel />);
            expect(wrapper.find('div').length).to.equal(1);
        });
    });
});