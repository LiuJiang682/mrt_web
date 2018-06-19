import React, { Component } from "react";

export default class TemplateDataRecordList extends Component {
	constructor(props) {
		super(props);
		this.state = {
			records: [],
			recordList: this.props.recordList,
			headers: this.props.headers,
		}
	}
	componentWillMount() {
		let records = [];
		records.fill('', 0, this.state.recordList.length);
		Object.keys(this.state.recordList).map(
			(key) => {
				const index = this.state.headers.indexOf(key);
				records[index] = this.state.recordList[key];		
			});
		this.setState({
			records: records,
		});
	}
	
	render() {
		// console.log('records', this.state.records);
		const tds = [];
		this.state.records.map((record, index) => {
			const td = <td key={index}>{record}</td>
			tds.push(td);
		});
		return (
			<tr className="tr_height">
				{tds}
			</tr>	
		);
	}
}