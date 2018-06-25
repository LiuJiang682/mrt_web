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
		console.log(this.state.headers.length);
		let records = new Array(this.state.headers.length).fill(' ');
		console.log(this.state.headers);
		console.log(this.state.recordList);
		// records.fill(' ', 0, this.state.recordList.length);
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
					} 
					else {
						pos = this.findHeaderPoleAliasPos(key, this.state.headers);
						if (-1 === pos) {
							pos = this.findHeaderMiddelAliasPos(key, this.state.headers);
						}
						if (-1 !== pos) {
							const recordValues = Object.values(this.state.recordList);
							value = recordValues[pos];
							index = pos;
						}
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
			const td = <td key={index} className="data_table_td">{record}</td>
			tds.push(td);
		});
		return (
			<tr key={this.state.currentIndex} className="tr_height">
				{tds}
			</tr>	
		);
	}

	findHeaderPoleAliasPos(currentHeader, headers) {
		console.log(arguments);
		console.log(currentHeader, headers);
		// const startAlias = '^' + key;
		// const endAlias = key + '$';
		headers.map((header, index) => {
			console.log('header: ' + header + ', index: ' + index + ', currentHeader: ' + currentHeader);
			const startAlias = '^' + header;
			const endAlias = header + '$';
			console.log(new RegExp(startAlias, 'gi').test(currentHeader));
			console.log(new RegExp(endAlias, 'gi').test(currentHeader));
			if (new RegExp(startAlias, 'gi').test(currentHeader) 
				|| (new RegExp(endAlias, 'gi').test(currentHeader))) {
					return index;
				}	
		});
		return -1;
	}

	findHeaderMiddelAliasPos(currentHeader, headers) {
		console.log('currentHeader', currentHeader);
		var headerIndex = -1;
		var spaceArray = currentHeader.split(' ');
		var underLineArray = currentHeader.split('_');
		var hypenArray = currentHeader.split('-');
		const lableArray = new Array();

		if (1 < spaceArray.length) {
			for (var index = 0; index < spaceArray.length; index++) {
				lableArray.push[spaceArray[index]];
			}
		}
		if (1 < underLineArray.length) {
			for (var index = 0; index < underLineArray.length; index++) {
				const str = underLineArray[index];
				console.log(str)
				lableArray[index] = str;
			}
		}
		if (1 < hypenArray.length) {
			for (var index = 0; index < hypenArray.length; index++) {
				lableArray.push[hypenArray[index]];
			}
		}
		if (0 < lableArray.length) {
			var regexString = this.buildRegexString(lableArray, ' ');
			regexString += "|";
			regexString += this.buildRegexString(lableArray, '_');
			regexString += "|";
			regexString += this.buildRegexString(lableArray, '_');

			var matcher = new RegExp(regexString, 'i');
			for (var index = 0; index < headers.length; index++) {
				const header = headers[index];
				if (matcher.test(header)) {
					console.log('returning: ' + index);
					headerIndex = index;
					break;
				}
			}
		}
		
		
		return headerIndex;
	}

	buildRegexString(array, delim) {
		var subRegexString = '';
		for (const str of array) {
			subRegexString += str;
			subRegexString += delim;
		}
		subRegexString = subRegexString.substring(0, subRegexString.length - 1);
		return subRegexString;
	}
}