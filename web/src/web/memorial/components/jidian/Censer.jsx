import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 香炉
 */
export default class Censer extends React.Component {
    constructor(props) {
        super(props);
    }

    onCenserClick=(pk,e)=>{
        this.props.onClick("Censer",pk);
    };

    render() {
        return (
            <div className='censer'>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,1)} src={require('../../assets/images/Censer/1.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,2)} src={require('../../assets/images/Censer/2.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,3)} src={require('../../assets/images/Censer/3.png')}/></Col>
                </Row>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,4)} src={require('../../assets/images/Censer/4.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,5)} src={require('../../assets/images/Censer/5.png')}/></Col>
                    <Col span={8}><img onClick={this.onCenserClick.bind(this,6)} src={require('../../assets/images/Censer/6.png')}/></Col>
                </Row>
            </div>
        );
    }
};
