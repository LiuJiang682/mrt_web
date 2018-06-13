import React from 'react';
import ReactDom from 'react-dom';
import * as Enzyme from 'enzyme';
import ReactSixteenAdapter from 'enzyme-adapter-react-16';
import { mount, shallow } from 'enzyme';
import sinon from 'sinon';
// import fakeFatch from 'fake-fetch';

import SessionSummary from './SessionSummary';
import { Session } from 'inspector';

Enzyme.configure({ adapter: new ReactSixteenAdapter() });

describe('SessionSummary', () => {
    describe('render', () => {
        it('should render searchBar', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.find('SearchBar').length).to.equal(1);
        });
        it('should render the session table', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.find('SessionTable').length).to.equal(1);
        });
    });
    describe('state', () => {
        it('should update the search text field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.searchText).to.equal('');
            wrapper.instance().handleSearchTextChange('new text');
            expect(wrapper.instance().state.searchText).to.equal('new text');
        });
        it('should update the selectAll field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectAll).to.equal(false);
            wrapper.instance().handleSelectAllChange(true);
            expect(wrapper.instance().state.selectAll).to.equal(true);
            wrapper.instance().handleSelectAllChange(false);
            expect(wrapper.instance().state.selectAll).to.equal(false);
        });
        it('should update the selectedBatch field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectedBatch).to.be.an('array').that.is.empty;
            wrapper.instance().handleSelectedBatchChange('12345');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345']);
        });
        it('should remove batch Id from the selectedBatch field when the field already contains the id', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectedBatch).to.be.an('array').that.is.empty;
            wrapper.instance().handleSelectedBatchChange('12345');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345']);
            wrapper.instance().handleSelectedBatchChange('12345');
            expect(wrapper.instance().state.selectedBatch).to.be.an('array').that.is.empty;
        });
        it('should append batch Id from the selectedBatch field when the field is not contains the id', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectedBatch).to.be.an('array').that.is.empty;
            wrapper.instance().handleSelectedBatchChange('12345');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345']);
            wrapper.instance().handleSelectedBatchChange('67890');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345', '67890']);
        });
        it('should remove batch Id from the selectedBatch field when the field is contains the id but rest will not change', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.selectedBatch).to.be.an('array').that.is.empty;
            wrapper.instance().handleSelectedBatchChange('12345');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345']);
            wrapper.instance().handleSelectedBatchChange('67890');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345', '67890']);
            wrapper.instance().handleSelectedBatchChange('67890');
            expect(wrapper.instance().state.selectedBatch).to.deep.equal(['12345']);
        });
        it('should update the totalPageNo field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.totalItemsCount).to.equal(0);
            wrapper.instance().handleTotalPageNoChange(20);
            expect(wrapper.instance().state.totalItemsCount).to.equal(20);
        });
        it('should update the currentPage field', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().state.currentPage).to.equal(0);
            wrapper.instance().handleCurrentPageChange(3);
            expect(wrapper.instance().state.currentPage).to.equal(3);
        });
    });
    describe('extractBatchId', () => {
        // const promise = Promise.resolve(JSON_DATA);
        // before(() => {
        //     // sinon.stub(fetch).withArgs('http://localhost:8090/sessionHeader/search/display?page=0&size=20').return(promise);
        //     fakeFatch.install;
        // });
        it('should return batch id when url provided', () => {
            const wrapper = shallow(<SessionSummary />);
            const batchId = wrapper.instance().extractBatchId('http://localhost:8090/sessionHeader/1234567');
            expect(batchId).to.equal('1234567');
        });
        it('should return batch id when plain text provided', () => {
            const wrapper = shallow(<SessionSummary />);
            const batchId = wrapper.instance().extractBatchId('1234567');
            expect(batchId).to.equal('1234567');
        });
        // after(() => {
        //     fakeFatch.restore;
        // });
    });
    describe('extractTime', () => {
        it('should format the time to secon', () => {
            const wrapper = shallow(<SessionSummary />);
            const time = wrapper.instance().exttractTime('2018-06-06T02:30:01.000+0000');
            expect(time).to.equal('2018-06-06 02:30:01.000');
        });
    });
    describe('handleButtonClicked', () => {
        it('should alert user unknown command', () => {
            const wrapper = shallow(<SessionSummary />);
            const alertStub = sinon.stub(global, 'alert');
            alertStub.returns(true);
            wrapper.instance().handleButtonClicked('abc');
            expect(alertStub.calledOnce).to.equal(true);
            alertStub.restore();
        });
        it('should alert user no selected file with approve command', () => {
            const wrapper = shallow(<SessionSummary />);
            const alertStub = sinon.stub(global, 'alert');
            alertStub.returns(true);
            wrapper.instance().handleButtonClicked('approve');
            expect(alertStub.calledOnce).to.equal(true);
            alertStub.restore();
        });
        it('should alert user no selected file with approve command', () => {
            const wrapper = shallow(<SessionSummary />);
            const alertStub = sinon.stub(global, 'alert');
            alertStub.returns(true);
            wrapper.instance().handleButtonClicked('reject');
            expect(alertStub.calledOnce).to.equal(true);
            alertStub.restore();
        });
    });
    describe('isInteger', () => {
        it('should return false', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().isInteger(undefined)).to.equal(false);
            expect(wrapper.instance().isInteger(null)).to.equal(false);
            expect(wrapper.instance().isInteger('')).to.equal(false);
            expect(wrapper.instance().isInteger(' ')).to.equal(false);
            expect(wrapper.instance().isInteger(false)).to.equal(false);
            expect(wrapper.instance().isInteger('q345')).to.equal(false);
            expect(wrapper.instance().isInteger('123agb')).to.equal(false);
            expect(wrapper.instance().isInteger('123.456')).to.equal(false);
        });
        it('should return true', () => {
            const wrapper = shallow(<SessionSummary />);
            expect(wrapper.instance().isInteger('123456')).to.equal(true);
            expect(wrapper.instance().isInteger('0')).to.equal(true);
        });
    });
    describe('buildSessionDTO', () => {
        it('should return session DTO', () => {
            const wrapper = shallow(<SessionSummary />);
            const self = {href: 'http://localhost:8080/sessionHeader/123'};
            const link = {self: self};
            const session = {_links: link, fileName: 'mrt_el123.zip', status: 'FAILED', tenement: 'EL123', created: '2018-06-12T23:36:45.000+000'};
            var sessionDTO = wrapper.instance().buildSessionDTO(session);
            expect(sessionDTO).to.not.be.null;
            expect(sessionDTO.batchId).to.be.equal('123');
            expect(sessionDTO.fileName).to.be.equal('mrt_el123.zip');
            expect(session.status).to.be.equal('FAILED');
            expect(sessionDTO.tenement).to.be.equal('EL123');
            expect(sessionDTO.dateRun).to.be.equal('2018-06-12 23:36:45.000');
        });
    });
});

const JSON_DATA = {
    "_embedded" : {
      "sessionHeader" : [ {
        "fileName" : "mrt_sl4.zip",
        "template" : "mrt",
        "processDate" : "2018-06-05T14:00:00.000+0000",
        "tenement" : "EL5478",
        "tenementHolder" : "Stavely Minerals Ltd",
        "reportingDate" : "2017-02-02T13:00:00.000+0000",
        "projectName" : "Toora West",
        "status" : "FAILED",
        "comments" : null,
        "emailSent" : "Y",
        "approved" : 0,
        "rejected" : 0,
        "created" : "2018-06-06T02:30:01.000+0000",
        "_links" : {
          "self" : {
            "href" : "http://localhost:8080/sessionHeader/180384216756204935"
          },
          "sessionHeader" : {
            "href" : "http://localhost:8080/sessionHeader/180384216756204935"
          }
        }
      } ]
    },
    "_links" : {
      "first" : {
        "href" : "http://localhost:8080/sessionHeader/search/display?page=0&size=1"
      },
      "self" : {
        "href" : "http://localhost:8080/sessionHeader/search/display?page=0&size=1"
      },
      "next" : {
        "href" : "http://localhost:8080/sessionHeader/search/display?page=1&size=1"
      },
      "last" : {
        "href" : "http://localhost:8080/sessionHeader/search/display?page=1&size=1"
      }
    },
    "page" : {
      "size" : 1,
      "totalElements" : 2,
      "totalPages" : 2,
      "number" : 0
    }
  };