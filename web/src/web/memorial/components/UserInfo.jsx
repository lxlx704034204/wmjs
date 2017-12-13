import React from 'react';
import { Modal, Form, Input, Radio, } from 'antd';
const FormItem = Form.Item;
const RadioGroup = Radio.Group;


const formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 },
    },
};

const CollectionCreateForm = Form.create()(
    (props) => {
        const { visible, onCancel, onCreate, form,generalUser } = props;
        const { getFieldDecorator } = form;

        return (
            <Modal
                visible={visible}
                title="用户信息"
                okText="保存"
                onCancel={onCancel}
                onOk={onCreate}
            >
                <Form layout="vertical">
                    <FormItem
                        label="姓名"
                        {...formItemLayout}
                    >
                        {getFieldDecorator('name', {
                            initialValue: generalUser.commonUser.name,
                            rules: [{ required: true, message: '姓名不能为空' }],
                        })(
                            <Input />
                        )}
                    </FormItem>

                    <FormItem
                        label="手机号"
                        {...formItemLayout}
                    >
                        {getFieldDecorator('phone1', {
                            initialValue: generalUser.commonUser.code,
                        })(
                            <Input disabled={true}/>
                        )}
                    </FormItem>

                    <FormItem
                        label="性别"
                        {...formItemLayout}
                    >
                        {getFieldDecorator('sex', {
                            initialValue: generalUser.personalInfo.sex?generalUser.personalInfo.sex.key:"Male",
                            rules: [
                                { required: true, message: '请选择性别!' },
                            ],
                        })(
                            <RadioGroup>
                                <Radio value="Male">男</Radio>
                                <Radio value="Female">女</Radio>
                            </RadioGroup>
                        )}
                    </FormItem>

                    <FormItem
                        label="家庭住址"
                        {...formItemLayout}
                    >
                        {getFieldDecorator('address', {
                            initialValue: generalUser.personalInfo.houseRegisterDetail,
                        })(
                            <Input />
                        )}
                    </FormItem>
                </Form>
            </Modal>
        );
    }
);

export default CollectionCreateForm;