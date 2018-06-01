import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

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
            const wrapper = shallow(<SessionRow session={SESSIONS} />);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456"/></td><td class="pos_left_middle">123456</td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
        it('should not render checked when batch ID is null', () => {
            const wrapper = shallow(<SessionRow session={SESSIONS} selectedBatch={null} />);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456"/></td><td class="pos_left_middle">123456</td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
        it('should render checked when batch ID provided', () => {
            const wrapper = shallow(<SessionRow session={SESSIONS}  selectedBatch={SELECTEDBATCH} />);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><td class="pos_center"><input type="checkbox" name="123456" checked=""/></td><td class="pos_left_middle">123456</td><td class="pos_left_middle">MRT_EL99</td><td class="pos_left_middle">Success</td><td class="pos_left_middle">06/05/2018</td></tr>');
        });
    });
});

const SELECTEDBATCH = ['123456', '789012'];
const SESSIONS = {batchId: '123456', fileName: 'MRT_EL99', status: 'Success', dateRun: '06/05/2018'};