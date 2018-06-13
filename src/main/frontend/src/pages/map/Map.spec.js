import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
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
});