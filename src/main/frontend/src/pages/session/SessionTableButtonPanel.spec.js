import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SessionTableButtonPanel from './SessionTableButtonPanel'

describe('SessionTableButtonPanel', () => {
    describe('render', () => {
        it('should render 2 buttons', () => {
            const wrapper = mount(<SessionTableButtonPanel />);
            expect(wrapper.find('button').length).to.equal(2);
        });
    });
});