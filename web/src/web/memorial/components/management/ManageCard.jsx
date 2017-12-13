import React from 'react';
import { Col, Button, Modal } from 'antd';
import { Link } from 'react-router';
import deleteMemorial from "../../store/deleteMemorial.js";
import {SERVER_URL} from "../../../../common/constant/serverConfig";

/**
 * 管理的馆
 */
export default class ManageCard extends React.Component {
    constructor(props) {
        super(props);
    }
    state = {
        visible: false
    };
    showModal(){
        this.setState({
            visible: true,
        });
    };
    hideModal(){
        this.setState({
            visible: false,
        });
    };
    deleteItem(pk,version){
        this.setState({
            visible: false,
        });
        deleteMemorial(pk).then((data)=> {
            this.props.getData();
        });
    };
    render() {
        let data=this.props.content;
        let pkPersonalInfo = data.personalInfo.pkPersonalInfo;
        let pkMemberMemorial = data.pkMemberMemorial;
        let version = data.version;
        let params = "/editdata?pkPersonalInfo="+pkPersonalInfo+"&version="+version+"&pkMemberMemorial="+pkMemberMemorial;
        let link = "/jidian?pk="+pkMemberMemorial;

        let attachName = require("../../assets/images/toxiang.jpg");
        if(data && data.attachName){
            attachName = SERVER_URL + 'api/attachment/' + data.attachName;
        }


        return (
            <Col className="gutter-row" span={3}>
                <div className="gutter-box thumbnail">
                    <div>
                        <Link to={link} activeClassName="jidian">
                            <img src={attachName} />
                        </Link>
                    </div>
                    <div>
                        <p>{data.memorialName}</p>
                    </div>
                    <div>
                        <Button size="small">
                            <Link to={params} activeClassName="editdata">管理</Link>
                        </Button>
                        <Button type="primary" onClick={this.showModal.bind(this)} size="small">删除</Button>
                        <Modal
                            title="确定删除吗？"
                            wrapClassName="vertical-center-modal"
                            visible={this.state.visible}
                            onOk={this.deleteItem.bind(this,data.pkMemberMemorial,data.version)}
                            onCancel={this.hideModal.bind(this)}
                            okText="确认"
                            cancelText="取消"
                        >
                            <p>纪念馆一经删除，将不可恢复，您确定删除吗？</p>
                        </Modal>
                    </div>
                </div>
            </Col>
        );
    }
};
