import React from 'react'

export default class Mrt extends React.Component {
	constructor() {
		super();
		this.state = {
				records: [],
		}
	}
	
	componentDidMount() {
		let headers = new Headers({
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
			'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
		    'Content-Type': 'multipart/form-data'
		});
		fetch("http://localhost:8080/template/123", {
			headers: headers
		})
		.then(results => {
			return results.json();
		}).then(data => {
			console.log('Data: ' + data)
			Object.entries(data).forEach(
				([key, value]) => console.log(key, value)
			);
			let records = Object.keys(data).map(function(keyName, keyIndex) {
				console.log('records: ' + keyName, data[keyName]);
				return (
						<div key={keyIndex}>
							<h3>{keyName}</h3>
							<TemplateList templateList={data[keyName]} />
						</div>
				);
			});
			console.log(records);
			this.setState({records: records}, (prevState, records) => ({
				records: records
			}));
			console.log('after', this.state.records);
		})
	}
	
	render() {
		return (
				<div>
					<h2>Mrt Report</h2>
					<div>
						{this.state.records}
					</div>
				</div>
		);
	}
}

class TemplateList extends React.Component {
	render() {
		console.log(this.props.templateList);
		return (
				<div>
					{this.props.templateList.map((record, index) => (
//							console.log('record ' + index + ': ' +record);
							<p key={index}>
								<RecordList recordList={record} />
							</p>
					))}
				</div>
		);
	}
}

class RecordList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
				records: [],
		}
	}
	componentDidMount() {
		Object.entries(this.props.recordList).forEach(
				([key, value]) => console.log('this.props', key, value)
			);
		let records = Object.keys(this.props.recordList).map(
				(key) => {
					return this.props.recordList[key]
					});
		console.log('new records: ', records);
		this.setState({records: records}, (prevState, records) => ({
			records: records
		}));
		console.log('state.record:', this.state.records);
	}
	render() {
		console.log('attr: ', this.state.records);
		return (
				<div>
					{this.state.records.map((record, index) => (
						<p key={index}>
							{record}
						</p>
				))}
				</div>
		);
	}
}
