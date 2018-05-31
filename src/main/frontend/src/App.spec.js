import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import App from './App';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

Enzyme.configure({ adapter: new ReactSixteenAdapter() });

describe('App', function() {
	describe('#load()', function() {
		it('should renders without crashing', () => {
			const div = document.createElement('div');
			ReactDom.render(<App />, div);
		});
		it('should contains banner', () => {
			const wrapper = shallow(<App />);
			expect(wrapper.find('Banner').length).to.equal(1);
		});
		it('should contains navbar', () => {
			const wrapper = shallow(<App />);
			expect(wrapper.find('NavBar').length).to.equal(1);
		});
		// it('should contains router', () => {
		// 	const wrapper = shallow(<App />);
		// 	expect(wrapper.find('Router').length).to.equal(1);
		// });
		it('should contains 4 routes', () => {
			const wrapper = shallow(<App />);
			expect(wrapper.find('Route').length).to.equal(4);
		});
	});
});
