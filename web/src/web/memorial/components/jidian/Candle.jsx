import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 香炉
 */
export default class Candle extends React.Component {
    constructor(props) {
        super(props);
    }

    onCenserClick=(pk,e)=>{
        this.props.onClick("Candle",pk);
    };

    render() {
        return (
            <div className='candle'>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,1)} src={require('../../assets/images/Candle/1.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,2)} src={require('../../assets/images/Candle/2.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,3)} src={require('../../assets/images/Candle/3.png')}/></Col>
                </Row>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,4)} src={require('../../assets/images/Candle/4.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,5)} src={require('../../assets/images/Candle/5.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,6)} src={require('../../assets/images/Candle/6.png')}/></Col>
                </Row>
            </div>
        );
    }
};
