import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SearchBar from './SearchBar';

describe('SearchBar', () => {
    describe('render', () => {
        it('should contain form', () => {
            const wrapper = shallow(<SearchBar />);
            expect(wrapper.find('form').length).to.equal(1);
        });
        it('should contain label batch Id', () => {
            const wrapper = shallow(<SearchBar />);
            const label = wrapper.find('b');
            expect(label.length).to.equal(1);
            expect(label.html()).to.equal('<b>Batch Id:</b>');
        });
        it('should contain input field', () => {
            const wrapper = shallow(<SearchBar />);
            const label = wrapper.find('input');
            expect(label.length).to.equal(1);
            expect(label.html()).to.equal('<input type="text" placeholder="Search Session ID..."/>');
        });
        it('should contain passed in search text', () => {
            const SEARCHTEXT = '123456';
            const wrapper = shallow(<SearchBar searchText={SEARCHTEXT} />);
            const inputField = wrapper.find('input');
            expect(inputField.length).to.equal(1);
            expect(inputField.html()).to.equal('<input type="text" placeholder="Search Session ID..." value="123456"/>');
        });
    });
});