import React from 'react';
import ReactDom from 'react-dom';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
import { MemoryRouter } from 'react-router-dom';
import sinon from 'sinon';
import fetchMock from 'fetch-mock';

import Logs from './Logs';

describe('Logs', () => {
    describe('updateSession success', () => {
        // const response = new Response();
        // const promise = Promise.resolve(response);
        
        // before(() => {
        //     sinon.stub(window, 'fetch').withArgs("http://localhost:8090/sessionHeader/search/approve?sessionId=123").returns(promise);
        // });
        // after(() => {
        //     window.fetch.restore();
        // });

        // it('should update the history', () => {
        //     const wrapper = mount(<MemoryRouter><Logs /></MemoryRouter>);
        //     wrapper.find('Logs').instance().updateSession("http://localhost:8090/sessionHeader/search/approve?sessionId=123");
        //     // console.log('pathname', wrapper.find('Logs').props().history.entries[0].pathname);
        //     expect(wrapper.find('Logs').props().history.entries[0].pathname).to.be.equal("/");
        // });
        it('should update the history', () => {
            fetchMock.get('*', {hello: 'world'});
            const wrapper = mount(<MemoryRouter><Logs /></MemoryRouter>);
            wrapper.find('Logs').instance().updateSession("http://localhost:8090/sessionHeader/search/approve?sessionId=123");
            expect(wrapper.find('Logs').props().history.entries[0].pathname).to.be.equal("/");
            fetchMock.restore();
        });
    });

    describe('updateSession failed', () => {
        const settings = {};
        const response = new Response();
        response.status = 400;
        response.statusText = 'Bad Request';
        response.ok = false;
        response.body = JSON.stringify(settings);
        const promise = Promise.resolve(response);
        
        // before(() => {
        //     sinon.stub(window, 'fetch').withArgs("http://localhost:8090/sessionHeader/search/approve?sessionId=123").returns(promise);
        // });
        // after(() => {
        //     window.fetch.restore();
        // });

        it('should failed to update the history', () => {
            // TODO: fix me!
            fetchMock.mock('*', new Response(JSON.stringify(settings)));
            // const wrapper = mount(shallow(<MemoryRouter><Logs /></MemoryRouter>).get(0));
            const wrapper = mount(<MemoryRouter><Logs /></MemoryRouter>);
            fetchMock.restore();
            fetchMock.mock('*', promise);
            // const alertStub = sinon.stub(global, 'alert');
            // alertStub.returns(true);
            wrapper.find('Logs').instance().updateSession("http://localhost:8090/sessionHeader/search/approve?sessionId=123");
            // console.log('just completed the call');
            // expect(alertStub.calledOnce).to.equal(true);
            // alertStub.restore();
            fetchMock.restore();
        });
    });
});