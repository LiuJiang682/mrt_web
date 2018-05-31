import React from 'react';
import { mount } from 'enzyme';
import Banner from './Banner';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';


Enzyme.configure({ adapter: new ReactSixteenAdapter() });

describe('Banner', () => {
    it('should render banner', () => {
        const wrapper = mount(<Banner />);
        expect(wrapper.html()).to.equal('<div class="pos_center"><h1>MRT Loader Report</h1><br><br></div>');
        wrapper.unmount();
    });
});