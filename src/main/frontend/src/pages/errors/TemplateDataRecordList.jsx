import React, { Component } from "react";

export default class TemplateDataRecordList extends Component {
	constructor(props) {
		super(props);
		this.state = {
			currentIndex: this.props.currentIndex,
			records: [],
			recordList: this.props.recordList,
			headers: this.props.headers,
		}
	}
	componentWillMount() {
		let records = [];
		console.log(this.state.headers);
		console.log(this.state.recordList);
		records.fill('', 0, this.state.recordList.length);
		Object.keys(this.state.recordList).map(
			(key) => {
				let value;
				var index = this.state.headers.indexOf(key.toUpperCase());
				if (-1 === index) {
					let title;
					let pos;
					if (/^[a-zA-Z]{1,2}[0-9]{1,}$/.test(key)) {
						//This is duplicated periodic table header
						title = key.substring(0, 2);
						if (2 < key.length) {
							pos = parseInt(key.substring(2));
						}
						if (/\d/.test(title)) {
							title = title.substring(0, 1);
							pos = parseInt(key.substring(1));
						}
					} else {
						pos = this.findHeaderAliasPos(key, this.state.headers);
					}
					if ((undefined !== title)
						&& (null !== title)) {
						title = title.toUpperCase();
						const existKey = this.state.headers[pos];
						if (existKey === title) {
							const recordValues = Object.values(this.state.recordList);
							value = recordValues[pos];
							index = pos;
						}
					}
					
				} else {
					value = this.state.recordList[key];	
				}
				
				if ((undefined === value)
					||(null === value)) {
						value = '    ';
					}
				records[index] = value;
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
			<tr key={this.state.currentIndex} className="tr_height">
				{tds}
			</tr>	
		);
	}

	findHeaderAliasPos(currentHeader, headers) {
		console.log(arguments);
		console.log(currentHeader, headers);
		// const startAlias = '^' + key;
		// const endAlias = key + '$';
		headers.map((header, index) => {
			console.log('header: ' + header + ', index: ' + index + ', currentHeader: ' + currentHeader);
			const startAlias = '^' + header;
			const endAlias = header + '$';
			console(new RegExp(startAlias, 'gi').test(currentHeader));
			console(new RegExp(endAlias, 'gi').test(currentHeader));
			if (new RegExp(startAlias, 'gi').test(currentHeader) 
				|| (new RegExp(endAlias, 'gi').test(currentHeader))) {
					return index;
				}
		});
	}
}