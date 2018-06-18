import React from 'react';
import ReactDom from 'react-dom';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import LogButtonPanel from './LogButtonPanel';

describe('LogButtonPanel', () => {
    describe('render', () => {
        it('should render 3 buttons', () => {
            const wrapper = shallow(<LogButtonPanel />);
            expect(wrapper.find('button').length).to.be.equal(3);
        });
    });
});