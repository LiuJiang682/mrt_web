import React from 'react';
import ReactDom from 'react-dom';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';

import TemplateDataRecordList from './TemplateDataRecordList';

describe('TemplateDataRecordList', () => {
    describe('regex for periodic table header', () => {
        it('should match periodic table', () => {
            expect(/^[a-zA-Z]{1,2}[0-9]{1,}$/.test('Au5')).to.be.equal(true);
            expect(/^[a-zA-Z]{1,2}[0-9]{1,}$/.test('Au51')).to.be.equal(true);
        });
        it('should match the middel variaty header', () => {
            expect(/^SURVERYED DEPTH$|^SURVERYED_DEPTH$|^SURVERYED-DEPTH$/.test('SURVERYED DEPTH')).to.be.equal(true);
            expect(/^SURVERYED DEPTH$|^SURVERYED_DEPTH$|^SURVERYED-DEPTH$/.test('SURVERYED_DEPTH')).to.be.equal(true);
            expect(/^SURVERYED DEPTH$|^SURVERYED_DEPTH$|^SURVERYED-DEPTH$/.test('SURVERYED-DEPTH')).to.be.equal(true);
            const matcher = new RegExp('^SURVERYED DEPTH$|^SURVERYED_DEPTH$|^SURVERYED-DEPTH$', 'i');
            expect(matcher.test('surveryed depth')).to.be.equal(true);
            expect(matcher.test('Surveryed depth')).to.be.equal(true);
            expect(/^SURVERYED DEPTH$|^SURVERYED_DEPTH$|^SURVERYED_DEPTH$/.test('HOLE_ID')).to.be.equal(false);
            // expect(/^\wSURVERYED\w |_|-\wDEPTH\w$/.test("HOLE_ID")).to.be.equal(false);
        });
    });
    describe('findHeaderAliasPos', () => {
        it('should match the alias', () => {
            const key = 'To'
            const startAlias = '^' + key;
            const endAlias =  key + '$';
            // console.log(endAlias);
            // console.log(/_To$/.test('Sample_To'));
            // console.log(/endAlias/.test('Sample_To'));
            // console.log(new RegExp(endAlias).test('Sample_To'));
            expect(new RegExp(endAlias, 'gi').test('Sample_To')).to.be.equal(true);
            expect(new RegExp(endAlias, 'gi').test('Sample_to')).to.be.equal(true);
            expect(new RegExp(startAlias, 'gi').test('To_sample')).to.be.equal(true);
            expect(new RegExp(startAlias, 'gi').test('to_sample')).to.be.equal(true);
        });
        it('should return position of header alias', () => {
            const headers = ['hole_id', 'From', 'To', 'sample_id', 'easting', 'northing'];
            const wrapper = shallow(<TemplateDataRecordList headers={headers} recordList={headers}/>);
            const current = 'Sample_From';
            const pos = wrapper.instance().findHeaderPoleAliasPos(current, headers);
            expect(pos).to.be.equal(1);
        });
    });
    describe('findHeaderMiddelAliasPos', () => {
        it('should match the title with middle variation', () => {
            const headers = ['hole_id', 'Sample From', 'To', 'sample_id', 'easting', 'northing'];
            const wrapper = shallow(<TemplateDataRecordList headers={headers} recordList={headers}/>);
            const current = 'Sample_From';
            const pos = wrapper.instance().findHeaderMiddelAliasPos(current, headers);
            expect(pos).to.be.equal(1);
        });
    });
    describe('buildRegexString', () => {
        it('should build regex string', () => {
            const array = ['Sample', 'depth'];
            const delim = '_';
            const wrapper = shallow(<TemplateDataRecordList headers={array} recordList={array} />);
            const regexString = wrapper.instance().buildRegexString(array, delim);
            expect(regexString).to.be.equal('Sample_depth');
        });
    });
});