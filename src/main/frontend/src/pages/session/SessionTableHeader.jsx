import React, { Component } from 'react'

export default class SessionTableHeader extends Component {
    constructor(props) {
        super(props);
        this.handleSelectAllChange = this.handleSelectAllChange.bind(this);
    }

    handleSelectAllChange(e) {
        console.log(e.target.checked);
        this.props.onSelectAllChange(e.target.checked);
    }

    render() {
        let checkBox;
        if ((undefined !== this.props.selectAll) 
            && (this.props.selectAll)) {
                checkBox = <input name="selectAll" type="checkbox"  checked onChange={this.handleSelectAllChange} />;
            } else {
                checkBox = <input name="selectAll" type="checkbox" onChange={this.handleSelectAllChange}/>;
            }
        
        return (
            <tr className="tr_height">
                <th className="pos_center">
                    <label>
                        {checkBox}
                    </label>
                </th>
                <th className="pos_left_middle">Batch Id</th>
                <th className="pos_left_middle">File Name</th>
                <th className="pos_left_middle">Status</th>
                <th className="pos_left_middle">Date Run</th>
            </tr>
        );
    }
}