import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
import { MemoryRouter } from 'react-router-dom';
import sinon from 'sinon';
import jest from 'jest';

import Map from './Map'

describe('Map', () => {
    describe('extractTenement', () => {
        it('should alter user when undefined string provided', () => {
            // var spy = jest.spyOn(Map.prototype, 'componentDidMount').mockReturnThis();
            // const id = '123:el123';
            // const params = {id};
            // const match = {params: params};
            // // const spy = sinon.spy(Map.prototype, "componentDidMount");
            // const wrapper = shallow(<Map match={match} componentDidMount={spy}/>);
            // const alertStub = sinon.stub(global, 'alert');
            // alertStub.returns(true);
            // wrapper.extractTenement(undefined);
            // expect(alertStub.calledOnce).to.equal(true);
            // alertStub.restore();
        });
    });
    describe('extractAmgZone', () => {
        it('should return 54 when string 54 provided', () => {
            // const id = '123:el123';
            // const params = {id};
            // const match = {params: params};
            // // var spy = jest.fn();
            // const wrapper = mount(<MemoryRouter><Map match={match} /></MemoryRouter>);
            // const amgZone = wrapper.instance().extractAmgZone('54');
            // expect(amgZone).to.be.equal('EPSG:28354');
        });
    });
});