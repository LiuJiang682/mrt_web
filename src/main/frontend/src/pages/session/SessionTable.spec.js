import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SessionTable from './SessionTable'

describe('SessionTable', () => {
    describe('render', () => {
        it('should contains table panel', () => {
            const wrapper = shallow(<SessionTable />);
            expect(wrapper.find('SessionTablePanel').length).to.equal(1);
        });
        it('should contains button panel', () => {
            const wrapper = shallow(<SessionTable />);
            expect(wrapper.find('SessionTableButtonPanel').length).to.equal(1);
        });
    });
});