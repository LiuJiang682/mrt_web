import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { shallow } from 'enzyme';
import sinon from 'sinon';

import SessionTablePanel from './SessionTablePanel'

describe('SessionTablePanel', () => {
    describe('render', () => {
        it('should render session table header', () => {
            const wrapper = shallow(<SessionTablePanel sessions={SESSIONS} />);
            expect(wrapper.find('table').length).to.equal(1);
            const tableHeader = wrapper.find('thead');
            expect(tableHeader.html()).to.equal('<thead><tr class="tr_height"><th class="pos_center"><label><input type="checkbox" name="selectAll"/></label></th><th class="pos_left_middle">Batch Id</th><th class="pos_left_middle">File Name</th><th class="pos_left_middle">Status</th><th class="pos_left_middle">Date Run</th></tr></thead>');
        });
        it('should render session table body', () => {
            const wrapper = shallow(<SessionTablePanel sessions={SESSIONS} />);
            expect(wrapper.find('table').length).to.equal(1);
            expect(wrapper.find('tbody').length).to.equal(1);
        });
        it('should render pagination panel', () => {
            const wrapper = shallow(<SessionTablePanel sessions={SESSIONS} />);
            expect(wrapper.find('SessionPaginationPanel').length).to.equal(1);
        });
    });
});

const SESSIONS = [
    {batchId: '123456', fileName: 'MRT_EL99', status: 'Success', dateRun: '06/05/2018'}
];