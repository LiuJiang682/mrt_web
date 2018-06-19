import React from 'react';
import ReactDom from 'react-dom';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import ReportTemplate from './ReportTemplate';

describe('ReportTemplate', () => {
    describe('extractColumnHeaders', () => {
        it('should extract column headers', () => {
            const FIRST_ROW = [{1: 'holeId', 2: 'drillingCode', 3: 'easting', 4: 'northing'}];
            const wrapper = shallow(<ReportTemplate recordList={FIRST_ROW}/>);
            const headers = wrapper.instance().extractColumnHeaders(FIRST_ROW[0]);
            expect(headers.length).to.be.equal(4);
            expect(headers).to.deep.equal(['holeId', 'drillingCode', 'easting', 'northing']);
        });
    });
    describe('buildTableHeader', () => {
        it('should build coloumn header html', () => {
            const FIRST_ROW = [{1: 'holeId', 2: 'drillingCode', 3: 'easting', 4: 'northing'}];
            const wrapper = shallow(<ReportTemplate recordList={FIRST_ROW}/>);
            const headers = ['holeId', 'drillingCode', 'easting', 'northing'];
            const headerHtml = wrapper.instance().buildTableHeader(headers);
            // console.log('headerHtml.props', headerHtml.props);
            expect(headerHtml.type).to.be.equal('tr');
            expect(headerHtml.props.className).to.be.equal('tr_height');
            expect(headerHtml.props.children.length).to.be.equal(4);
            for (const child of headerHtml.props.children) {
                expect(child.type).to.be.equal('td');
            }
        });
    });
});

