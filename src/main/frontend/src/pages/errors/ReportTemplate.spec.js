import React from 'react';
import ReactDom from 'react-dom';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import ReportTemplate from './ReportTemplate';

describe('ReportTemplate', () => {
    describe('extractColumnHeaders', () => {
        it('should extract column headers', () => {
            // const FIRST_ROW = [{1: 'holeId', 2: 'drillingCode', 3: 'easting', 4: 'northing'}];
            const wrapper = shallow(<ReportTemplate recordList={ROWS}/>);
            const headers = wrapper.instance().extractColumnHeaders(ROWS[0]);
            expect(headers.length).to.be.equal(6);
            expect(headers).to.deep.equal(['holeId', 'drillingCode', 'easting', 'northing', 'Depth From', 'Depth To']);
        });
    });
    describe('buildTableHeader', () => {
        it('should build coloumn header html', () => {
            // const FIRST_ROW = [{1: 'holeId', 2: 'drillingCode', 3: 'easting', 4: 'northing'}];
            const wrapper = shallow(<ReportTemplate recordList={ROWS}/>);
            const headers = ['holeId', 'drillingCode', 'easting', 'northing', 'Depth From', 'Depth To'];
            const dataHeaders = ['holeId', 'drillingCode', 'easting', 'northing', 'Depth_From', 'Depth_To'];
            const headerHtml = wrapper.instance().buildTableHeader(headers, dataHeaders);
            // console.log('headerHtml.props', headerHtml.props);
            expect(headerHtml.type).to.be.equal('tr');
            expect(headerHtml.props.className).to.be.equal('tr_height');
            expect(headerHtml.props.children.length).to.be.equal(6);
            for (const child of headerHtml.props.children) {
                console.log(child);
                expect(child.type).to.be.equal('td');
                if ('Depth From' === child.props.children) {
                    expect(child.props.className).to.be.equal('data_table_td_warn');
                } else if ('Depth To' === child.props.children) {
                    expect(child.props.className).to.be.equal('data_table_td_warn');
                } else {
                    expect(child.props.className).to.be.equal('data_table_td');
                }
            }
        });
    });
    describe('buildUCHeaders', () => {
        it('should build upper case header', () => {
            // const FIRST_ROW = [{1: 'holeId', 2: 'drillingCode', 3: 'easting', 4: 'northing'}];
            const wrapper = shallow(<ReportTemplate recordList={ROWS}/>);
            const headers = ['holeId', 'drillingCode', 'easting', 'northing'];
            const ucHeaders = wrapper.instance().buildUCHeaders(headers);
            for (const ucHeader of ucHeaders) {
                expect(/[A-Z]/.test(ucHeader)).to.be.equal(true);
            }
        });
    });
    describe('extractDataHeader', () => {
        it('should extract data header', () => {
            // const DATA_ROW = [{holeId: 'KDPP001', easting: '1234567', northing: '89012345', Depth_From: '1', Depth_To: '2'}];
            const wrapper = shallow(<ReportTemplate recordList={ROWS}/>);
            const dataHeaders = wrapper.instance().extractDataHeaders(ROWS[1]);
            expect(dataHeaders).to.deep.equal(['holeId', 'easting', 'northing', 'Depth_From', 'Depth_To']);
        });
    });
});

const ROWS = [
    {1: 'holeId', 2: 'drillingCode', 3: 'easting', 4: 'northing', 5: 'Depth From', 6: 'Depth To'},
    {holeId: 'KDPP001', easting: '1234567', northing: '89012345', Depth_From: '1', Depth_To: '2'}
];