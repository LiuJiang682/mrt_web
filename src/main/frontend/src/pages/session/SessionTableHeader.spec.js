import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { shallow } from 'enzyme';
import sinon from 'sinon';

import SessionTableHeader from './SessionTableHeader'

describe('SessionTableHeader', () => {
    describe('render', () => {
        it('should render the session table header', () => {
            const wrapper = shallow(<SessionTableHeader />);
            expect(wrapper.find('tr').length).to.equal(1);
            expect(wrapper.find('th').length).to.equal(5);
            expect(wrapper.find('input').length).to.equal(1);
        });
        it('should render the selectAll checkbox', () => {
            const SELECTALL = true;
            const wrapper = shallow(<SessionTableHeader selectAll={SELECTALL}/>);
            expect(wrapper.html()).to.equal('<tr class="tr_height"><th class="pos_center"><label><input type="checkbox" name="selectAll" checked=""/></label></th><th class="pos_left_middle">Batch Id</th><th class="pos_left_middle">File Name</th><th class="pos_left_middle">Status</th><th class="pos_left_middle">Date Run</th></tr>');
        });
    });
    describe('selectAll check box', () => {
        it('should call handleSelectAllChange of the passing parameter', () => {
            const handleSelectAllChange = sinon.fake();
            const wrapper = shallow(<SessionTableHeader onSelectAllChange={handleSelectAllChange}/>);
            wrapper.find('input').simulate('change', {target: {checked: true}});
            wrapper.find('input').simulate('change', {target: {checked: false}});
            expect(handleSelectAllChange.calledTwice).to.equal(true);
            expect(handleSelectAllChange.calledWith(true)).to.equal(true);
            expect(handleSelectAllChange.calledWith(false)).to.equal(true);
        });
    });
});