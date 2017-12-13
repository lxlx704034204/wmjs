import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 鲜花
 */
export default class Flower extends React.Component {
    constructor(props) {
        super(props);
    }

    onFlowerClick=(pk,e)=>{
        this.props.onClick("Flower",pk);
    };

    render() {
        return (
            <div className='flower'>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,1)} src={require('../../assets/images/Flower/1.png')}/></Col>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,2)} src={require('../../assets/images/Flower/2.png')}/></Col>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,3)} src={require('../../assets/images/Flower/3.png')}/></Col>
                </Row>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,4)} src={require('../../assets/images/Flower/4.png')}/></Col>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,5)} src={require('../../assets/images/Flower/5.png')}/></Col>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,6)} src={require('../../assets/images/Flower/6.png')}/></Col>
                </Row>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,7)} src={require('../../assets/images/Flower/7.png')}/></Col>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,8)} src={require('../../assets/images/Flower/8.png')}/></Col>
                    <Col span={8}><img onClick={this.onFlowerClick.bind(this,9)} src={require('../../assets/images/Flower/9.png')}/></Col>
                </Row>
            </div>
        );
    }
};
