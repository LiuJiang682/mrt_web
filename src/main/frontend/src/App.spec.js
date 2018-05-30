import React from 'react';
import ReactDom from 'react-dom';
//import * as Adapter from 'enzyme-adapter-react-16';
import * as Enzyme from 'enzyme';
import App from './App';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
import ReactTestUtils from 'react-dom/test-utils'; 
import Banner from './pages/common/Banner';

Enzyme.configure({ adapter: new ReactSixteenAdapter() });

describe('App', function() {
	describe('#load()', function() {
		it('renders without crashing', () => {
			const div = document.createElement('div');
			ReactDom.render(<App />, div);
		});
		it('contains banner', () => {
			const wrapper = shallow(<App />);
			expect(wrapper.find('Banner').length).to.equal(1);
		});
	});
});
