import React from 'react';
import moment from "moment";
import '../../assets/css/editdata.less';
import {
    Form, Select, InputNumber, Switch, Radio,
    Slider, Button, Upload,  Input, Icon, message,DatePicker,
} from 'antd';
import { Link } from 'react-router';
const FormItem = Form.Item;
const Option = Select.Option;
const RadioGroup = Radio.Group;
const { TextArea } = Input;
import userMe from '../../../../common/store/userMe';
import {getUrlParame} from '../../../../common/tools/Tools';
import {SERVER_URL} from "../../../../common/constant/serverConfig";
import saveMemberMemorial from '../../store/saveMemberMemorial.js';
import queryMemberMemorial from '../../store/queryMemberMemorial.js';
/**
 * 编辑资料
 */
function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}
class EditData extends React.Component {
    constructor(props) {
        super(props);
        let that = this;
        const pkPersonalInfo = getUrlParame('pkPersonalInfo');
        const pkMemberMemorial = getUrlParame('pkMemberMemorial');
        const version = getUrlParame('version');
        userMe().then(function (data) {
            let pkUser = data.pkUser;
            const queryParams = {
                pkMemberMemorial: pkMemberMemorial,
                fetchProperties:"*,personalInfo.*,creator.*"
            };
            queryMemberMemorial(queryParams).then((ret) => {
                let data= ret[0];
                that.setState({
                    pkUser:pkUser,
                    name:data.personalInfo.name,
                    sex:data.personalInfo.sex?data.personalInfo.sex.key:"",
                    memorialName:data.memorialName,
                    profession:data.profession,
                    relationship:data.relationship,
                    introduction:data.introduction,
					birthday:data.personalInfo.birthday,
                    deathDay:data.deathDay,
 					attachName: data.attachName,
					imageUrl:SERVER_URL+'api/attachment/'+(data.attachName?data.attachName:new Date().getTime())
                });
            });
        });
    }
    state = {};
    handleSubmit (e) {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {

            if (!err) {
                const pkUser = this.state.pkUser;
                const pkPersonalInfo = getUrlParame('pkPersonalInfo');
                const pkMemberMemorial = getUrlParame('pkMemberMemorial');
                const version = getUrlParame('version');
                let birthday =  Date.parse(values.birthday);
                let deathDay =  Date.parse(values.deathDay);
                let idata = {
                    "creator.pkUser":pkUser,
                    pkPersonalInfo:pkPersonalInfo,
                    pkMemberMemorial:pkMemberMemorial,
                    version:version,
                    memorialName:values.memorialName,
                    name:values.name,
                    sex:values.sex,
                    birthday:birthday,
                    deathDay:deathDay,
                    profession:values.profession,
                    relationship:values.relationship,
                    introduction:values.introduction,
                    attachName: this.state.attachName
                };
                saveMemberMemorial.save(idata).then((data)=> {
                    if(data.pkMemberMemorial){
                        location.href = "#/";
                    }
                });
            }
        });
    }
    handleChange = (info) => {
        const pkPersonalInfo = getUrlParame('pkPersonalInfo');
        if (info.file.status === 'done') {

            this.setState({
                attachName: 'memorial/'+ pkPersonalInfo
            });

            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => this.setState({imageUrl}))
        };
    }
    normFile (e) {
        if (Array.isArray(e)) {
            return e;
        }
        return e && e.fileList;
    }
    render() {
        const { getFieldDecorator } = this.props.form;
        const imageUrl = this.state.imageUrl;
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };
        return (
            <div className='edit-data ant-card ant-card-bordered ant-card-wider-padding' style={{minHeight: (window.innerHeight-150)}}>
                <Form onSubmit={this.handleSubmit.bind(this)}>
                    <FormItem
                        {...formItemLayout}
                        label="头像"
                    >
                        {getFieldDecorator('dragger', {
                            valuePropName: 'fileList',
                            getValueFromEvent: this.normFile,
                        })(
                            <Upload
                                className="avatar-uploader"
                                name="file"
                                showUploadList={false}
                                action={SERVER_URL+'api/attachment/memorial/'+ getUrlParame('pkPersonalInfo')}
                                onChange={this.handleChange}
                                withCredentials={true}
                            >
                                {
                                    imageUrl ?
                                        <img src={imageUrl} alt="" className="avatar" /> :
                                        <Icon type="plus" className="avatar-uploader-trigger" />
                                }
                            </Upload>
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="逝者姓名"
                    >
                        {getFieldDecorator('name', {
                            initialValue:this.state.name,
                            rules: [
                                { required: true, message: '请输入逝者姓名！' },
                            ],
                        })(
                            <Input placeholder="逝者姓名" />
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="逝者性别"
                    >
                        {getFieldDecorator('sex', {
                            initialValue:this.state.sex?this.state.sex:"Male",
                            rules: [
                                { required: true, message: '请选择逝者性别!' },
                            ],
                        })(
                            <RadioGroup>
                                <Radio value="Male">男</Radio>
                                <Radio value="Female">女</Radio>
                            </RadioGroup>
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="生辰"
                    >
                        {getFieldDecorator('birthday', {
                            initialValue:this.state.birthday?moment(this.state.birthday):null,
                            rules: [{ type: 'object', required: true, message: '请选择逝者生辰!' }]
                        })(
                            <DatePicker/>
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="忌日"
                    >
                        {getFieldDecorator('deathDay', {
                            initialValue:this.state.deathDay?moment(this.state.deathDay):null,
                            rules: [{ type: 'object', required: true, message: '请选择逝者忌日!' }],
                        })(
                            <DatePicker/>
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="纪念馆名称"
                    >
                        {getFieldDecorator('memorialName', {
                            initialValue:this.state.memorialName,
                            rules: [
                                { required: true, message: '请输入逝者纪念馆名称！' },
                            ],
                        })(
                            <Input placeholder="纪念馆名称" />
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="与逝者关系"
                    >
                        {getFieldDecorator('relationship', {
                            initialValue:this.state.relationship,
                            rules: [{ required: true, message: '请选择与逝者关系!' },
                            ],
                        })(
                            <Select placeholder="与逝者关系">
                                <Option value="祖先">祖先</Option>
                                <Option value="祖父">祖父</Option>
                                <Option value="祖母">祖母</Option>
                                <Option value="外公">外公</Option>
                                <Option value="外婆">外婆</Option>
                                <Option value="父亲">父亲</Option>
                                <Option value="岳父">岳父</Option>
                                <Option value="岳母">岳母</Option>
                                <Option value="公公">公公</Option>
                                <Option value="婆婆">婆婆</Option>
                                <Option value="丈夫">丈夫</Option>
                                <Option value="妻子">妻子</Option>
                                <Option value="儿子">儿子</Option>
                                <Option value="女儿">女儿</Option>
                                <Option value="哥哥">哥哥</Option>
                                <Option value="姐姐">姐姐</Option>
                                <Option value="弟弟">弟弟</Option>
                                <Option value="妹妹">妹妹</Option>
                                <Option value="亲属">亲属</Option>
                                <Option value="老师">老师</Option>
                                <Option value="朋友">朋友</Option>
                                <Option value="同事">同事</Option>
                                <Option value="其他">其他</Option>
                            </Select>
                        )}
                    </FormItem>
                    <FormItem
                        {...formItemLayout}
                        label="逝者职业"
                    >
                        {getFieldDecorator('profession',{
                            initialValue:this.state.profession,
                        })(
                            <Input placeholder="职业" />
                        )}
                    </FormItem>

                    <FormItem
                        {...formItemLayout}
                        label="逝者简介"
                    >
                        {getFieldDecorator('introduction',{
                            initialValue:this.state.introduction,
                            rules: [{ max: 254, message: '最多不能超过250个字!' }],
                        })(
                            <TextArea rows={4} placeholder="逝者简介"/>
                        )}
                    </FormItem>
                    <FormItem
                        wrapperCol={{ span: 12, offset: 6 }}
                    >
                        <Button type="primary" htmlType="submit">提交</Button>
                        <Link to="#/">
                            <Button style={{ marginLeft: 8 }} onClick={this.handleReset}>取消</Button>
                        </Link>
                    </FormItem>
                </Form>
            </div>
        );
    }
};
const EditDataForm = Form.create()(EditData);
export default EditDataForm;
