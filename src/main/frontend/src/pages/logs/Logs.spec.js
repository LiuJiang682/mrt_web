import React from 'react';
import ReactDom from 'react-dom';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
import { MemoryRouter } from 'react-router-dom';
import sinon from 'sinon';
import 'whatwg-fetch';

import Logs from './Logs';

describe('Logs', () => {
    describe('updateSession success', () => {
        const response = new Response();
        const promise = Promise.resolve(response);
        
        before(() => {
            sinon.stub(window, 'fetch').withArgs("http://localhost:8090/sessionHeader/search/approve?sessionId=123").returns(promise);
        });
        after(() => {
            window.fetch.restore();
        });

        it('should update the history', () => {
            const wrapper = mount(<MemoryRouter><Logs /></MemoryRouter>);
            wrapper.find('Logs').instance().updateSession("http://localhost:8090/sessionHeader/search/approve?sessionId=123");
            // console.log('pathname', wrapper.find('Logs').props().history.entries[0].pathname);
            expect(wrapper.find('Logs').props().history.entries[0].pathname).to.be.equal("/");
        });
    });

    describe('updateSession failed', () => {
        const response = new Response();
        response.status = 404;
        const promise = Promise.resolve(response);
        
        before(() => {
            sinon.stub(window, 'fetch').withArgs("http://localhost:8090/sessionHeader/search/approve?sessionId=123").returns(promise);
        });
        after(() => {
            window.fetch.restore();
        });

        it('should failed to update the history', () => {
            // TODO: fix me!
            // const wrapper = mount(<MemoryRouter><Logs /></MemoryRouter>);
            // wrapper.find('Logs').instance().updateSession("http://localhost:8090/sessionHeader/search/approve?sessionId=123");
            // // console.log('pathname', wrapper.find('Logs').props().history.entries[0].pathname);
            // expect(wrapper.find('Logs').props().history.entries[0].pathname).to.be.equal("/");
        });
    });
});