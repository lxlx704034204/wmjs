import React from 'react';
import '../../assets/css/jidian.less';
import { Input, Button, Timeline } from 'antd';
const { TextArea } = Input;
import {query,save,deleteItem} from '../../store/memorialobituary';
import {getUrlParame,formatDate} from '../../../../common/tools/Tools';

/**
 * 悼文
 */
export default class Daowen extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            timeData:[]
        };

        if(props.userMe){
            let that = this;
            let params = {
                memberMemorial: getUrlParame('pk')
            };
            query(params).then((data)=>{

                data = data.sort(function(a,b){
                    return b.memorialDate - a.memorialDate;
                });

                that.setState({
                    timeData: data
                });
            });
        }
    }

    onButtonClick=()=>{
        let content = this.refs.daowenText.textAreaRef.value;

        if(content){
            let params = {
                commonUser: this.props.userMe.pkUser,
                memberMemorial: getUrlParame('pk'),
                memorialDate: new Date().getTime(),
                content: content
            };
            let that = this;
            save(params).then(()=>{
                that.refs.daowenText.textAreaRef.value = "";
                let tParams = {
                    memberMemorial: getUrlParame('pk')
                };
                return query(tParams);
            }).then((data)=>{
                data = data.sort(function(a,b){
                    return b.memorialDate - a.memorialDate;
                });
                that.setState({
                    timeData: data
                });

            });
        }

    };
    clickDelete =(value,idx)=>{
        let pk = value.pkMemorialObituary;
        let that=this;
        deleteItem(pk).then((data)=> {
            if(data.msg == "删除成功"){
                let params = {
                    memberMemorial: getUrlParame('pk')
                };
                return query(params);
            }
        }).then((data)=>{
            data = data.sort(function(a,b){
                return b.memorialDate - a.memorialDate;
            });
            that.setState({
                timeData: data
            });
        });
    };
    getTimeLine=(data)=>{
        let that = this;
        return data.map(function(n,i){
            let idata = formatDate(n.memorialDate,"yyyy-MM-dd");
            if(that.props.userMe.pkUser == n.memberMemorial.creator.pkUser){
                return (<Timeline.Item key={i}>
                    <div style={{paddingRight:"20px"}}>{n.content} {idata}</div>
                    <div style={{textAlign:"right"}}><Button type="primary" size="small" onClick={that.clickDelete.bind(this,n,i)}>删除</Button></div>
                </Timeline.Item>);
            }else{
                return (<Timeline.Item key={i}>
                    <div style={{paddingRight:"20px"}}>{n.content} {idata}</div>
                </Timeline.Item>);
            }
        });
    };

    render() {
        let timeData = this.state.timeData;

        let introduction = this.props.data.introduction;

        return (
            <div className='daowen'>
                <div className="daowen-jianjie">
                    <p>
                    <span>生平简介：</span><br/>{introduction}
                    </p>
                </div>
                <div>
                    <TextArea ref="daowenText" placeholder="请输入纪念悼文" style={{width:"100%"}} autosize={{ minRows: 2, maxRows: 2 }} />
                    <Button onClick={this.onButtonClick} type="primary" style={{float: 'right', marginTop: '10px'}}>发表</Button>
                </div>
                <div className="daowen-list">
                    <Timeline>
                        {this.getTimeLine(timeData)}
                    </Timeline>
                </div>

            </div>
        );
    }
};
