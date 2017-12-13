import React from 'react';
import '../../assets/css/jidian.less';
import {SERVER_URL} from "../../../../common/constant/serverConfig";

/**
 * 照片
 */
export default class Mubei extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        console.log(this.props.tombstoneImage);

        let attachName = require("../../assets/images/toxiang.jpg");
        if(this.props.data && this.props.data.attachName){
            attachName = SERVER_URL + 'api/attachment/' + this.props.data.attachName;
        }

        let mubeiClass = "mubei mubei"+this.props.tombstoneImage;

        return (
            <div className={mubeiClass}>

                <div>
                    <div className="photo">
                        <img style={{width:"100%",height:"100%"}} src={attachName}/>
                    </div>
                    <div className="title">
                        {this.props.data?this.props.data.personalInfo.name:""}
                    </div>
                </div>
            </div>
        );
    }
};
