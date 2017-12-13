import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 墓碑
 */
export default class Tombstone extends React.Component {
    constructor(props) {
        super(props);
    }

    onTributeClick=(pk,e)=>{
        this.props.onClick("Tombstone",pk);
    }

    render() {
        return (
            <div className='scenes'>
                <Row style={{height:100}}>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,1)} src={require('../../assets/images/Tombstone/1.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,2)} src={require('../../assets/images/Tombstone/2.png')}/></Col>
                    <Col span={8}><img onClick={this.onTributeClick.bind(this,3)} src={require('../../assets/images/Tombstone/3.png')}/></Col>
                </Row>
            </div>
        );
    }
};
