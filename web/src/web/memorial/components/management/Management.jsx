import React from 'react';
import '../../assets/css/management.less';
import { Row } from 'antd';
import ManageCard from "./ManageCard.jsx";
import userMe from '../../../../common/store/userMe';
import queryMemberMemorial from '../../store/queryMemberMemorial.js';
/**
 * 管理的馆
 */
export default class MemorialManagement extends React.Component {
    constructor(props) {
        super(props);
        this.getData();
    }
    state={
        data:[]
    };
    getData = ()=>{
        let that= this;
        userMe().then(function (data) {
            let pkUser = data.pkUser;
            that.getMemorialData(pkUser);
        });
    };
    getMemorialData=(pkUser)=>{
        let that = this;
        const queryParams = {
            creator: pkUser,
            fetchProperties:"*,personalInfo.*,creator.*"
        };
        queryMemberMemorial(queryParams).then((data) => {
            that.setState({
                data: data
            })
        });
    };
    getMemorialItems=(data)=>{
        let elements = [];
        if(data && data.length>0){
            for (let i = 0; i < data.length; i++) {
                elements.push(<ManageCard content = {data[i]} getData={this.getData}/>)
            }
        }
        return elements;
    }
    render() {
        return (
            <div className='memorial-management ant-card ant-card-bordered ant-card-wider-padding'style={{minHeight: (window.innerHeight-150)}}>
                <Row gutter={16}>
                    {this.state.data.length>0?this.getMemorialItems(this.state.data):"暂无数据"}
                </Row>
            </div>
        );
    }
};
