import React from 'react';
import { Form,Col, Button, Modal,Input } from 'antd';
import { Link } from 'react-router';
import {SERVER_URL} from "../../../../common/constant/serverConfig";
const { TextArea } = Input;
const FormItem = Form.Item;
/**
 * 管理的馆
 */
class Share extends React.Component {
    constructor(props) {
        super(props);
    }
    state = {
        visible: false
    };
    showModal(){
        this.setState({
            visible: true,
        });
    };
    hideModal(){
        this.setState({
            visible: false,
        });
    };
    handleSubmit (e) {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let text=document.getElementById("text");
                text.select();
                document.execCommand("Copy");
                this.setState({
                    visible: false,
                });
            }
        });
    }
    render() {
        const { getFieldDecorator } = this.props.form;
        let memorialName = this.props.data?this.props.data.personalInfo.name:"";
        let text = "你好，这是我在逝者网上祭奠平台上为【"+memorialName+"】创建的网上纪念馆，希望你能前来祭拜，用我们的爱温暖整个天堂！谢谢！纪念馆网址："+window.location.href;
        return (
            <div className="shareBox">
                <b onClick={this.showModal.bind(this)}>邀请亲友来祭拜</b>
                <Modal
                    title="邀请亲友来祭拜"
                    wrapClassName="vertical-center-modal"
                    visible={this.state.visible}
                    onOk={this.handleSubmit.bind(this)}
                    onCancel={this.hideModal.bind(this)}
                    okText="复制"
                    cancelText="取消"
                >
                    <p>复制以下文字并通过E-mail或QQ、MSN等方式发送给亲朋好友，<br/>邀请他们一起来祭拜（您可以自行修改以下文字）：</p>
                    <Form>
                        <FormItem>
                            {getFieldDecorator('text',{
                                initialValue:text,
                            })(
                                <TextArea style={{minHeight:"90px"}}/>
                            )}
                        </FormItem>
                    </Form>
                </Modal>
            </div>
        );
    }
};
const ShareForm = Form.create()(Share);
export default ShareForm;