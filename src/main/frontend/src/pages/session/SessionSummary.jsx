import React, { Component } from 'react'

// import Mrt from './Mrt'

// const Home = () => <div>Hello World! <Mrt /></div>

// export default Home
import SearchBar from '../common/SearchBar'
import SessionTable from './SessionTable'

export default class SessionSummary extends Component {
    render() {
        return (
            <div>
                <SearchBar />
                <SessionTable sessions={SESSIONS} />
            </div>
        )
    }
}

const SESSIONS = [
    {batchId: '123456', fileName: 'MRT_EL99', status: 'Success', dateRun: '06/05/2018'},
    {batchId: '789012', fileName: 'MRT_EL98', status: 'Success', dateRun: '06/05/2018'},
    {batchId: '345678', fileName: 'MRT_EL97', status: 'Fail', dateRun: '05/05/2018'},
    {batchId: '901234', fileName: 'MRT_EL96', status: 'Success', dateRun: '04/05/2018'},
    {batchId: '567890', fileName: 'MRT_EL95', status: 'Fail', dateRun: '03/05/2018'},
    {batchId: '112345', fileName: 'MRT_EL92', status: 'Success', dateRun: '02/05/2018'}
];