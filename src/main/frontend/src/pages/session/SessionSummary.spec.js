import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SessionSummary from './SessionSummary';
import { Session } from 'inspector';

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
    describe('state', () => {
        it('should update the search text field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.searchText).to.equal('');
            wrapper.instance().handleSearchTextChange('new text');
            expect(wrapper.instance().state.searchText).to.equal('new text');
        });
        it('should update the selectAll field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectAll).to.equal(false);
            wrapper.instance().handleSelectAllChange(true);
            expect(wrapper.instance().state.selectAll).to.equal(true);
            wrapper.instance().handleSelectAllChange(false);
            expect(wrapper.instance().state.selectAll).to.equal(false);
        });
        it('should update the selectedBatch field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectedBatch).to.be.an('array').that.is.empty;
            wrapper.instance().handleSelectedBatchChange(['12345']);
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345']);
        });
        it('should update the totalPageNo field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.totalPageNo).to.equal(null);
            wrapper.instance().handleTotalPageNoChange(20);
            expect(wrapper.instance().state.totalPageNo).to.equal(20);
        });
        it('should update the currentPage field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.currentPage).to.equal(null);
            wrapper.instance().handleCurrentPageChange(3);
            expect(wrapper.instance().state.currentPage).to.equal(3);
        });
    });
});