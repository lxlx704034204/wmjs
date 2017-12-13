import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 场景
 */
export default class Scenes extends React.Component {
    constructor(props) {
        super(props);
    }

    onTributeClick=(pk,e)=>{
        this.props.onClick("Scenes",pk);
    }

    render() {
        return (
            <div className='scenes'>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,1)} src={require('../../assets/images/Scenes/1.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,2)} src={require('../../assets/images/Scenes/2.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,3)} src={require('../../assets/images/Scenes/3.png')}/></Col>
                </Row>
            </div>
        );
    }
};
