import React from 'react';
import '../../assets/css/jidian.less';
import { Popover, Popconfirm } from 'antd';
import Flower from './Flower.jsx';
import Censer from './Censer.jsx';
import Candle from './Candle.jsx';
import Tribute from './Tribute.jsx';
import Scenes from './Scenes.jsx';
import Tombstone from './Tombstone.jsx';

/**
 * 菜单
 */
export default class Menu extends React.Component {
    constructor(props) {
        super(props);
    }

    onMenuClick=(type,index)=>{
        this.props.onClick(type,index);
    }


    /**
     * 鲜花
     * @returns {XML}
     */
    getFlowerContent(){
        return (
            <div style={{width:300,height:300}}>
                <Flower onClick={this.onMenuClick} />
            </div>
        );
    }

    /**
     * 贡品
     * @returns {XML}
     */
    getTributeContent(){
        return (
            <div style={{width:300,height:200}}>
                <Tribute onClick={this.onMenuClick} />
            </div>
        );
    }

    /**
     * 香炉
     * @returns {XML}
     */
    getCenserContent(){
        return (
            <div style={{width:300,height:200}}>
                <Censer onClick={this.onMenuClick} />
            </div>
        );
    }

    /**
     * 香烛
     * @returns {XML}
     */
    getCandleContent(){
        return (
            <div style={{width:300,height:200}}>
                <Candle onClick={this.onMenuClick} />
            </div>
        );
    }

    /**
     * 场景
     * @returns {XML}
     */
    getScenesContent(){
        return (
            <div style={{width:300,height:100}}>
                <Scenes onClick={this.onMenuClick} />
            </div>
        );
    }

    /**
     * 墓碑
     * @returns {XML}
     */
    getMuBeiContent(){
        return (
            <div style={{width:300,height:100}}>
                <Tombstone onClick={this.onMenuClick} />
            </div>
        );
    }

    /**
     * 一键清扫
     * @returns {XML}
     */
    getClearGoods(){
        this.props.onClearGoods();
    }

    render() {
        return (
            <div className='menu'>
                <ul>
                    <Popover placement="leftTop"  content={this.getFlowerContent()} title="鲜花" trigger="click">
                        <li>
                            <div className="xianhua">
                                献花
                            </div>
                        </li>
                    </Popover>

                    <Popover placement="leftTop"  content={this.getTributeContent()} title="贡品" trigger="click">
                        <li>
                            <div className="gongping">
                                贡品
                            </div>
                        </li>
                    </Popover>

                    <Popover placement="left"  content={this.getCandleContent()} title="香烛" trigger="click">
                        <li>
                            <div className="xiangzhu">
                                香烛
                            </div>
                        </li>
                    </Popover>


                    <Popover placement="left"  content={this.getCenserContent()} title="香炉" trigger="click">
                        <li>
                            <div className="xianglu">
                                香炉
                            </div>
                        </li>
                    </Popover>


                    <Popover placement="leftBottom"  content={this.getScenesContent()} title="场景" trigger="click">
                        <li>
                            <div className="changjing">
                                场景
                            </div>
                        </li>
                    </Popover>

                    <Popover placement="left"  content={this.getMuBeiContent()} title="墓碑" trigger="click">
                        <li>
                            <div className="mubeibut">
                                墓碑
                            </div>
                        </li>
                    </Popover>

                    <Popconfirm placement="left" title={"是否清扫所有贡品"} onConfirm={this.getClearGoods.bind(this)} okText="立即清扫" cancelText="我再想想">
                        <li>
                            <div className="qingjie">
                                清扫
                            </div>
                        </li>
                    </Popconfirm>
                </ul>
            </div>
        );
    }
};
