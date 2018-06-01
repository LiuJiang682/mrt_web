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
        it('should render 9 pages when total page number is 8', () => {
            const wrapper = shallow(<SessionPaginationPanel totalPageNo={8} currentPage={1} />);
            expect(wrapper.find('li').length).to.equal(9);
        });
        it('should render 11 pages when total page number is 20', () => {
            const wrapper = shallow(<SessionPaginationPanel totalPageNo={20} currentPage={1} />);
            expect(wrapper.find('li').length).to.equal(11);
        });
        it('should render 0 pages when total page number is underfined', () => {
            const wrapper = shallow(<SessionPaginationPanel totalPageNo={undefined} currentPage={1} />);
            expect(wrapper.find('li').length).to.equal(0);
        });
        it('should render 0 pages when total page number is null', () => {
            const wrapper = shallow(<SessionPaginationPanel totalPageNo={null} currentPage={1} />);
            expect(wrapper.find('li').length).to.equal(0);
        });
    });
});