import React, { Component } from 'react'

export default class SessionTableHeader extends Component {
    render() {
        let checkBox;
        if ((undefined !== this.props.selectAll) 
            && (this.props.selectAll)) {
                checkBox = <input name="selectAll" type="checkbox"  checked />;
            } else {
                checkBox = <input name="selectAll" type="checkbox" />;
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