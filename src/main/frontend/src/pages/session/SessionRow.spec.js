import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
import sinon from 'sinon';
import { MemoryRouter } from 'react-router-dom';

import SessionRow from './SessionRow'

describe('SessionRow', () => {
    describe('render', () => {
        it('should contains a row', () => {
            const wrapper = shallow(<SessionRow session={SESSIONS} />);
            expect(wrapper.find('tr').length).to.equal(1);
        });
        it('should contains 5 columns', () => {
            const wrapper = shallow(<SessionRow session={SESSIONS} />);
            expect(wrapper.find('td').length).to.equal(5);
        });
        it('should not render checked when batch ID not provided', () => {
            const wrapper = shallow(<MemoryRouter><SessionRow session={SESSIONS} /></MemoryRouter>);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456"/></td><td class="pos_left_middle"><a href="/map/123456">123456</a></td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
        it('should not render checked when batch ID is null', () => {
            const wrapper = shallow(<MemoryRouter><SessionRow session={SESSIONS} selectedBatch={null} /></MemoryRouter>);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456"/></td><td class="pos_left_middle"><a href="/map/123456">123456</a></td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
        it('should render checked when batch ID provided', () => {
            const wrapper = shallow(<MemoryRouter><SessionRow session={SESSIONS}  selectedBatch={SELECTEDBATCH} /></MemoryRouter>);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456" checked=""/></td><td class="pos_left_middle"><a href="/map/123456">123456</a></td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
        it('should not render checked when batch ID array is empty', () => {
            const emptyBatchIds = [];
            const wrapper = shallow(<MemoryRouter><SessionRow session={SESSIONS} selectedBatch={emptyBatchIds} /></MemoryRouter>);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456"/></td><td class="pos_left_middle"><a href="/map/123456">123456</a></td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
    });
    describe('selected batch', () => {
        it('should call selectedBatch of passing parameter', () => {
            const handleSelectBatchChange = sinon.fake();
            const wrapper =mount(<MemoryRouter><SessionRow session={SESSIONS} onSelectedBatchChange={handleSelectBatchChange}/></MemoryRouter>);
            expect(wrapper.find('input').length).to.equal(1);
            wrapper.find('input').simulate('change', {target: {name: '12345'}});
            wrapper.find('input').simulate('change', {target: {name: '67890'}});
            expect(handleSelectBatchChange.calledTwice).to.equal(true);
            expect(handleSelectBatchChange.calledWith('12345')).to.equal(true);
            expect(handleSelectBatchChange.calledWith('67890')).to.equal(true);
        });
    });
});

const SELECTEDBATCH = ['123456', '789012'];
const SESSIONS = {batchId: '123456', fileName: 'MRT_EL99', status: 'Success', dateRun: '06/05/2018'};