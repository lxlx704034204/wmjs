import React from 'react';
import '../../assets/css/jidian.less';
import { Popover } from 'antd';
import {getElementPos,formatDate} from '../../../../common/tools/Tools';

/**
 * 浮层
 */
export default class GoodsLayer extends React.Component {
    constructor(props) {
        super(props);
        this.isMove = false;
        this.isNew = false;
        this.selectEle = null;
        this.startPosition = {x:0,y:0};
        this.elementPosition = {top:0,left:0};
    }

    componentWillReceiveProps(nextProps){

    }

    mousePosition(ev){
        let xd = getElementPos("goods-layer");
        if(ev.pageX || ev.pageY){
            return {x:ev.pageX-xd.x+10, y:ev.pageY-xd.y+10};
        }
        return {
            x:ev.clientX + document.body.scrollLeft - document.body.clientLeft-xd.x + 10,
            y:ev.clientY + document.body.scrollTop - document.body.clientTop-xd.y + 10
        };
    }

    getGoodsDetail=(data)=>{
        let times = formatDate(data.memorialDate,"yyyy年MM月dd日");
        return (
            <div>
                <p>祭奠人：{data.commonUser?data.commonUser.name:""}</p>
                <p>时间：{times}</p>
            </div>
        );
    };

    getGoodsDom=()=>{
        let goods = this.props.goods;
        let that = this;
        if(goods.length>0){
            return goods.map(function(n,i){
                let top = "0px";
                let left = "0px";
                if(n.coordinate && typeof n.coordinate == "string"){
                    n.coordinate = JSON.parse(n.coordinate);
                    if(typeof n.coordinate == "string"){
                        n.coordinate = JSON.parse(n.coordinate);
                    }
                    top = n.coordinate.y+"px";
                    left = n.coordinate.x+"px";
                }else if(n.coordinate && typeof n.coordinate == "object"){
                    top = n.coordinate.y+"px";
                    left = n.coordinate.x+"px";
                }

                if(n.imagePath != "Candle/4.png" && n.imagePath != "Candle/5.png" && n.imagePath != "Candle/6.png"){

                    let tmpMemorialType = null;

                    if(typeof n.memorialType == "object"){
                        tmpMemorialType = n.memorialType.key;
                    }else{
                        tmpMemorialType = n.memorialType;
                    }

                    if(tmpMemorialType == "Censer" && n.validPeriod && n.validPeriod>new Date().getTime()){

                        let imagePath = n.imagePath.replace("png","gif");

                        return (
                            <div key={i} className={"goods-ele goods-"+n.memorialType.key}
                                 data-type={n.memorialType.key}
                                 data-pk={n.pkMemorialDetail}
                                 style={{top: top,left: left}}
                            >
                                <Popover content={that.getGoodsDetail(n)} >
                                    <img draggable="false"
                                         width={100} src={require("../../assets/images/" + imagePath)}/>
                                </Popover>
                            </div>
                        );
                    }else{
                        return (
                            <div key={i} className={"goods-ele goods-"+n.memorialType.key}
                                 data-type={n.memorialType.key}
                                 data-pk={n.pkMemorialDetail}
                                 style={{top: top,left: left}}
                            >
                                <Popover content={that.getGoodsDetail(n)} >
                                    <img draggable="false"
                                         width={70} src={require("../../assets/images/" + n.imagePath)}/>
                                </Popover>
                            </div>
                        );
                    }
                }

            });
        }else{
            return null;
        }
    };

    getNewGoodsDom=()=>{
        let newGoods = this.props.newGoods;
        if(newGoods){
            this.isNew = true;
            return (
                <div id="move-ele" style={{top: -999,left: -999}} className={"goods-ele goods-"+newGoods.memorialType}>
                    <img width={70} src={require("../../assets/images/" + newGoods.imagePath)}/>
                </div>
            );
        }else{
            this.isNew = false;
            return null;
        }
    };

    onContextMenu=(e)=>{
        this.props.onContextMenu();
        e.preventDefault();
    };

    onGoodsClick=(e)=>{

        let tmpGoods = this.props.newGoods;

        if(tmpGoods && e.target.parentNode.getAttribute("data-type") == "Censer"
            && tmpGoods.memorialType == "Candle" && tmpGoods.index > 3){

            let pk = e.target.parentNode.getAttribute("data-pk");
            let idata = null;
            for(let i=0;i<this.props.goods.length;i++){
                if(pk == this.props.goods[i].pkMemorialDetail){
                    idata = this.props.goods[i];
                    break;
                }
            }


            if(idata){
                if(!idata.validPeriod || idata.validPeriod < new Date().getTime()){
                    idata.validPeriod = new Date().getTime() + 5*1000*60;
                }else if(idata.validPeriod >= new Date().getTime()){
                    idata.validPeriod = idata.validPeriod + 5*1000*60;
                }
            }
            this.props.saveGoodsDetail(idata);
            this.props.onContextMenu();
        }else{
            if(tmpGoods){
                tmpGoods.coordinate = JSON.stringify(this.mousePosition(e));
                this.props.onClick(tmpGoods);
            }
        }

        e.preventDefault();
    };


    onGoodsMouseMove = (e) => {
        if(this.isMove){
            let ele = this.selectEle;
            if(ele.style.left && ele.style.top){
                ele.style.left = (Number(this.elementPosition.left) + (e.clientX-this.startPosition.x))+"px";
                ele.style.top = (Number(this.elementPosition.top) + (e.clientY-this.startPosition.y))+"px";
            }
        }else{
            let position = this.mousePosition(e);
            let moveEle = document.getElementById("move-ele");
            if(moveEle){
                document.getElementById("move-ele").style.top = position.y+"px";
                document.getElementById("move-ele").style.left = position.x+"px";
            }
        }
    };

    onGoodsMouseDown = (e) => {
        this.isMove = true;
        this.startPosition = {x:e.clientX,y:e.clientY};
        this.selectEle = e.target.parentNode;

        let left = this.selectEle.style.left.replace("px","");
        let top = this.selectEle.style.top.replace("px","");

        this.elementPosition = {
            top: top,
            left: left
        };
    };

    onGoodsMouseUp = (e) => {
        this.isMove = false;
        if(!this.isNew){
            let left = this.selectEle.style.left.replace("px","");
            let top = this.selectEle.style.top.replace("px","");
            let pk = this.selectEle.getAttribute("data-pk");
            let idata = null;

            for(let i=0;i<this.props.goods.length;i++){
                if(pk == this.props.goods[i].pkMemorialDetail){
                    idata = this.props.goods[i];
                    break;
                }
            }

            if(idata){
                idata.coordinate = {
                    x: left,
                    y: top
                };
                idata.coordinate = JSON.stringify(idata.coordinate);
                this.props.saveGoodsDetail(idata);
            }
        }
    };

    render() {

        return (
            <div id='goods-layer'
                 onMouseDown={this.onGoodsMouseDown}
                 onMouseUp={this.onGoodsMouseUp}
                 onMouseMove={this.onGoodsMouseMove}
                 onClick={this.onGoodsClick}
                 onContextMenu={this.onContextMenu}
            >
                {this.getGoodsDom()}
                {this.getNewGoodsDom()}
            </div>
        );
    }
};
