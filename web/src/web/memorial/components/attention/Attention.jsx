import React from 'react';
import '../../assets/css/attention.less';
import { Row } from 'antd';
import AttentionCard from "./AttentionCard.jsx";
import userMe from '../../../../common/store/userMe';
import {query} from '../../store/Attention';

/**
 * 管理的馆
 */
export default class Attention extends React.Component {
    constructor(props) {
        super(props);
        this.getData();
    }
    state={
        data:[],
        userMe:null
    };
    getData = ()=>{
        let that= this;
        userMe().then(function (data) {

            that.setState({
                userMe:data
            });

            let pkUser = data.pkUser;
            that.getMemorialData(pkUser);
        });
    };
    getMemorialData=(pkUser)=>{
        let that = this;
        const queryParams = {
            careUser: pkUser
        };
        query(queryParams).then((data) => {
            let tmp = [];
            for(let i=0;i<data.length;i++){

                data[i].memberMemorial.pkCareMemorial=data[i].pkCareMemorial;

                tmp.push(data[i].memberMemorial);
            }
            that.setState({
                data: tmp
            })
        });
    };
    getMemorialItems=(data)=>{
        let elements = [];
        if(data && data.length>0){
            for (let i = 0; i < data.length; i++) {
                elements.push(<AttentionCard content = {data[i]} getData={this.getData} userMe={this.state.userMe}/>)
            }
        }
        return elements;
    }
    render() {
        return (
            <div className='attention ant-card ant-card-bordered ant-card-wider-padding' style={{minHeight: (window.innerHeight-150)}}>
                <Row gutter={16}>
                    {this.state.data.length>0?this.getMemorialItems(this.state.data):"暂无数据"}
                </Row>
            </div>
        );
    }
};
