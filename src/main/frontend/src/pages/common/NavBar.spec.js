import React from 'react';
import { mount } from 'enzyme';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { MemoryRouter } from 'react-router-dom';
import NavBar from './NavBar';

Enzyme.configure({ adapter: new ReactSixteenAdapter() });

const expectedHtml = '<div><nav class="navbar navbar-default"><div class="container-fluid"><div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1"><ul class="nav navbar-nav"><li><a activeclassname="active" href="/">Session Summary</a></li><li><a activeclassname="active" href="errors">Errors</a></li></ul></div></div></nav></div>';

describe('NavBar', () => {
    it('should render NavBar', () => {
        const wrapper = mount(
            <MemoryRouter>
                <NavBar />
            </MemoryRouter>
        );
        expect(wrapper.html()).to.equal(expectedHtml);
        wrapper.unmount();
    });
});
