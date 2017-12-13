import React from 'react';
import '../../assets/css/jidian.less';
import { Row, Col } from 'antd';

/**
 * 标题
 */
export default class Header extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className='eheader'>
                <Row>
                    <Col span={6}></Col>
                    <Col span={12}>
                        <div className="title">
                            {this.props.data?this.props.data.memorialName:""}
                        </div>
                    </Col>
                    <Col span={6}></Col>
                </Row>
            </div>
        );
    }
};
