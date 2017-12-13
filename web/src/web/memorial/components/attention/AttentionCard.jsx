import React from 'react';
import { Col, Button, Modal } from 'antd';
import { Link } from 'react-router';
import {cancel} from '../../store/Attention';
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
    deleteItem(data){
        cancel(data.pkCareMemorial).then((data)=>{
            this.setState({
                visible: false,
            });
            this.props.getData();
        });

    };
    render() {
        let data=this.props.content;
        let pkMemberMemorial = data.pkMemberMemorial;
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
                        <Button type="primary" onClick={this.showModal.bind(this)}>取消关注</Button>
                        <Modal
                            title="确定取消关注吗？"
                            wrapClassName="vertical-center-modal"
                            visible={this.state.visible}
                            onOk={this.deleteItem.bind(this,data)}
                            onCancel={this.hideModal.bind(this)}
                            okText="确认"
                            cancelText="取消"
                        >
                        </Modal>
                    </div>
                </div>
            </Col>
        );
    }
};
