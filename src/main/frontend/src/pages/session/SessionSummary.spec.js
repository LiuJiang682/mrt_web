import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SessionSummary from './SessionSummary';

Enzyme.configure({ adapter: new ReactSixteenAdapter() });

describe('SessionSummary', () => {
    describe('render', () => {
        it('should render searchBar', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.find('SearchBar').length).to.equal(1);
        });
        it('should render the session table', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.find('SessionTable').length).to.equal(1);
        });
    });
});