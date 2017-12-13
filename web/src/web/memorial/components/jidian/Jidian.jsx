import React from 'react';
import '../../assets/css/jidian.less';
import { Card,Icon } from 'antd';
import Mubei from './Mubei.jsx';
import Menu from './Menu.jsx';
import GoodsLayer from './GoodsLayer.jsx';
import userMe from '../../../../common/store/userMe';

import {memorialQuery,query,saveMemorial,saveGoods,clearGoods} from '../../store/MemorialDetail';
import {getUrlParame} from '../../../../common/tools/Tools';

import img1 from '../../assets/images/Scenes/1.png';
import img2 from '../../assets/images/Scenes/2.png';
import img3 from '../../assets/images/Scenes/3.png';

import Attention from '../../store/Attention';
import Share from './Share.jsx';
import Daowen from './Daowen.jsx';
import { Link } from 'react-router';
const jidianStyles = [img1,img2,img3];


/**
 * 网络祭奠
 */
export default class Jidian extends React.Component {
    constructor(props) {
        super(props);

        this.state={
            userMe:null,
            goods:[],
            newGoods:null,
            backgroundImage: null,
            memorial: null,
            tombstoneImage: null,
            attention:null
        };

        let that = this;

        let pkMemberMemorial = getUrlParame('pk');

        let queryParams = {
            'pkMemberMemorial': pkMemberMemorial
        };

        let userMeParams = {};
        userMeParams.fetchProperties = "*";

        let tmpState = {};

        userMe(userMeParams).then((data)=>{
            tmpState.userMe = data;
            return memorialQuery(queryParams);
        }).then((data)=>{
            let scenes = 1;
            if(data[0].scenesPath && data[0].scenesPath != "0"){
                scenes = Number(data[0].scenesPath);
            }
            tmpState.memorial = data[0];
            tmpState.backgroundImage = jidianStyles[scenes-1];
            tmpState.tombstoneImage = data[0].tombstone?data[0].tombstone:1;
            return query({'memberMemorial.pkMemberMemorial': pkMemberMemorial});
        }).then((data)=>{

            that.setState({
                userMe: tmpState.userMe,
                memorial: tmpState.memorial,
                backgroundImage: tmpState.backgroundImage,
                tombstoneImage: tmpState.tombstoneImage,
                goods: data
            });

            const queryParams = {
                careUser: tmpState.userMe.pkUser,
                memberMemorial: tmpState.memorial.pkMemberMemorial
            };
            return Attention.query(queryParams);
        }).then((data)=>{
            if(data.length>0){
                that.setState({
                    attention: data[0]
                });
            }
        });

    }

    onMenuClick=(type,index,e)=>{

        if(type == "Scenes"){

            this.setState({
                backgroundImage: jidianStyles[index-1]
            });

            let params = this.state.memorial;

            if(params.memberMemorialType && typeof params.memberMemorialType == "object"){
                params.memberMemorialType = params.memberMemorialType.key;
            }

            let that = this;
            params.scenesPath = index;
            saveMemorial(params).then((data)=>{
                if(!data.exMessage){
                    that.setState({
                        memorial:data
                    });
                }else{
                    let memorial = that.state.memorial;
                    memorial.scenesPath = index;
                    that.setState({
                        memorial:memorial
                    });
                }
            });

        }else if(type == "Censer"){
            this.setState({
                newGoods:{
                    coordinate: "",
                    imagePath: type+"/"+index+".png",
                    memorialType: type,
                    index: index
                }
            });
        }else if(type == "Candle"){

            this.setState({
                newGoods:{
                    coordinate: "",
                    imagePath: type+"/"+index+".png",
                    memorialType: type,
                    index: index
                }
            });

        }else if(type == "Tombstone"){

            this.setState({
                tombstoneImage: index
            });

            let params = this.state.memorial;

            if(params.memberMemorialType && typeof params.memberMemorialType == "object"){
                params.memberMemorialType = params.memberMemorialType.key;
            }


            if(params.creator && typeof params.creator == "object"){
                params.creator = params.creator.pkUser;
            }


            let that = this;
            params.tombstone = index;
            saveMemorial(params).then((data)=>{
                if(!data.exMessage){
                    that.setState({
                        memorial:data
                    });
                }else{
                    let memorial = that.state.memorial;
                    memorial.scenesPath = index;
                    that.setState({
                        memorial:memorial
                    });
                }
            });


        } else {
            this.setState({
                newGoods:{
                    coordinate: "",
                    imagePath: type+"/"+index+".png",
                    memorialType: type,
                    index: index
                }
            });
        }
    };

    onGoodsContextMenu=()=>{
        this.setState({
            newGoods:null
        });
    };


    onGoodsClick=(data)=>{
        let goods = this.state.goods;
        let that = this;
        data["memberMemorial"] = getUrlParame('pk');

        goods.push(data);
        that.setState({
            goods:goods,
            //newGoods:null
        });

        data["counts"] = 1;
        data.coordinate = JSON.stringify(data.coordinate);
        saveGoods(data).then((data)=>{
            goods = that.state.goods;
            goods.pop();

            goods.push(data);
            that.setState({
                goods:goods,
                //newGoods:null
            });
        });
    };


    saveGoodsDetail=(iData)=>{

        let goods = this.state.goods;
        let that = this;


        if(typeof iData.memberMemorial == "object"){
            iData.memberMemorial = iData.memberMemorial.pkMemberMemorial;
        }

        if(typeof iData.commonUser == "object"){
            iData.commonUser = iData.commonUser.pkUser;
        }

        if(typeof iData.memorialType == "object"){
            iData.memorialType = iData.memorialType.key;
        }

        saveGoods(iData).then((data)=>{

            for(let i=0;i<goods.length;i++){
                if(data.pkMemorialDetail == goods[i].pkMemorialDetail){
                    goods[i] = data;
                    break;
                }
            }
            that.setState({
                goods:goods
            });
        });
    };


    onCareClick=()=>{
        let params = {
            memberMemorial: this.state.memorial.pkMemberMemorial,
            careUser: this.state.userMe.pkUser,
            version: 0,
            careTime: new Date().getTime()
        };
        let that = this;
        Attention.save(params).then((data)=>{
            that.setState({
                attention: data
            });
        });
    };

    onUnCareClick=()=>{
        let that = this;
        Attention.cancel(this.state.attention.pkCareMemorial).then((data)=>{
            that.setState({
                attention: null
            });
        });
    };


    onClearGoods=()=>{
        let params = {
            memberMemorial: this.state.memorial.pkMemberMemorial
        };

        let that = this;
        clearGoods(params).then((data)=>{
            if(data.msg == "删除成功"){
                that.setState({
                    goods: []
                });
            }
        });
    };


    render() {
        let tmpStyle = null;
        if(this.state.backgroundImage){
            tmpStyle = {
                background:'url('+this.state.backgroundImage+') 100% 100% no-repeat'
            };
        }

        let guanzhu = null;

        if(!this.state.userMe || !this.state.memorial || (this.state.userMe.pkUser != this.state.memorial.creator.pkUser)){
            if(!this.state.attention){
                guanzhu = (
                    <div className="guanzhu" onClick={this.onCareClick}>
                        <Icon type="star-o" />关注
                    </div>
                );
            }else{
                guanzhu = (
                    <Link to={"/attention"} activeClassName="jidian">
                        <div className="guanzhu" onClick={this.onUnCareClick}>
                            <Icon type="star" />取消关注
                        </div>
                    </Link>
                );
            }
        }

        return (
            <div className='jidian' style={{height: (window.innerHeight-150)}}>
                <Card bordered={true} style={{ width: "100%",height:"100%" }}>
                    {guanzhu}<Share data = {this.state.memorial}/>
                    <div id="daowenBox">
                        {this.state.userMe?<Daowen data={this.state.memorial} userMe={this.state.userMe} />:null}
                    </div>
                    <div id="jidianBox" className="jidian-body" style={tmpStyle} >
                        <Menu onClick={this.onMenuClick} onClearGoods={this.onClearGoods} />
                        <Mubei data={this.state.memorial} tombstoneImage={this.state.tombstoneImage} />
                        <GoodsLayer saveGoodsDetail={this.saveGoodsDetail} onClick={this.onGoodsClick} onContextMenu={this.onGoodsContextMenu} newGoods={this.state.newGoods} goods={this.state.goods} />
                    </div>
                </Card>
            </div>
        );
    }
};
