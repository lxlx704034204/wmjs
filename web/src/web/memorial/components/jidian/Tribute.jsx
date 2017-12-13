import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 贡品
 */
export default class Tribute extends React.Component {
    constructor(props) {
        super(props);
    }

    onTributeClick=(pk,e)=>{
        this.props.onClick("Tribute",pk);
    }

    render() {
        return (
            <div className='tribute'>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,1)} src={require('../../assets/images/Tribute/1.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,2)} src={require('../../assets/images/Tribute/2.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,3)} src={require('../../assets/images/Tribute/3.png')}/></Col>
                </Row>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,4)} src={require('../../assets/images/Tribute/4.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,5)} src={require('../../assets/images/Tribute/5.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,6)} src={require('../../assets/images/Tribute/6.png')}/></Col>
                </Row>
            </div>
        );
    }
};
