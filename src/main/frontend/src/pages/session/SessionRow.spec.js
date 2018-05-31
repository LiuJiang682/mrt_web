import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import SessionRow from './SessionRow'

describe('SessionRow', () => {
    describe('render', () => {
        it('should contains a row', () => {
            const wrapper = shallow(<SessionRow session={SESSIONS} />);
            expect(wrapper.find('tr').length).to.equal(1);
        });
        it('should contains 5 columns', () => {
            const wrapper = shallow(<SessionRow session={SESSIONS} />);
            expect(wrapper.find('td').length).to.equal(5);
        });
    });
});

const SESSIONS = {batchId: '123456', fileName: 'MRT_EL99', status: 'Success', dateRun: '06/05/2018'};