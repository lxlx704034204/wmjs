import React from 'react';
import '../../assets/css/create.less';
import saveMemberMemorial from '../../store/saveMemberMemorial.js';
import userMe from '../../../../common/store/userMe';
import {SERVER_URL} from "../../../../common/constant/serverConfig";
import {
    Form, Select, InputNumber, Switch, Radio,
    Slider, Button, Upload,  Input, Icon, message, Row, Col,DatePicker,
} from 'antd';
const FormItem = Form.Item;
const Option = Select.Option;
/**
 * 新建纪念馆
 */
function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}
class Create extends React.Component {
    constructor(props) {
        super(props);
        let that= this;
        userMe().then(function (data) {
            let pkUser = data.pkUser;
            that.setState({
                pkUser:pkUser
            });
        });
    }
    state = {
        classFocus:0, //焦点背景
        pkUser:null
    };
    clickStyle =(value,idx)=>{
        this.setState({
            classFocus:idx
        });
    };
    getStyleList=()=>{
        let elements = [];
        let data = [1,2,3];
        if(data && data.length>0){
            for (let i = 0; i < data.length; i++) {
                if(i==this.state.classFocus){
                    elements.push(<Col className="gutter-row img-col" span={8}><div className="gutter-box li-active"><img onClick={this.clickStyle.bind(this,data[i],i)} src={require('../../assets/images/Scenes/'+data[i]+'.png')}/></div></Col>)
                }else{
                    elements.push(<Col className="gutter-row img-col" span={8}><div className="gutter-box"><img onClick={this.clickStyle.bind(this,data[i],i)} src={require('../../assets/images/Scenes/'+data[i]+'.png')}/></div></Col>)
                }
            }
        }
        return elements;
    };
    handleSubmit (e) {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                let birthday =  Date.parse(values.birthday);
                let deathDay =  Date.parse(values.deathDay);
                const pkUser = this.state.pkUser;
                const scenesPath =  this.state.classFocus + 1;
                let attachName = this.state.attachName;
                let idata = {
                    attachName:attachName,
                    scenesPath:scenesPath,
                    "creator.pkUser":pkUser,
                    memorialName:values.memorialName,
                    name:values.name,
                    birthday:birthday,
                    deathDay:deathDay,
                    relationship:values.relationship
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
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => this.setState({imageUrl}));
        }
    }
    normFile (e) {
        if (Array.isArray(e)) {
            return e;
        }
        return e && e.fileList;
    }
    upData =(date,file)=>{
        this.setState({
            attachName:'memorial/'+ date
        });

    };
    render() {
        const { getFieldDecorator } = this.props.form;
        const imageUrl = this.state.imageUrl;
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };
        const date=new Date().getTime();
        return (
          <div className='create ant-card ant-card-bordered ant-card-wider-padding' style={{minHeight: (window.innerHeight-150)}}>
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
                              action={SERVER_URL+'api/attachment/memorial/'+ date}
                              onChange={this.handleChange}
                              withCredentials={true}
                              data={this.upData.bind(this,date)}
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
                          rules: [
                              { required: true, message: '请输入逝者姓名！' },
                          ],
                      })(
                          <Input placeholder="逝者姓名" />
                      )}
                  </FormItem>
                  <FormItem
                      {...formItemLayout}
                      label="生辰"
                  >
                      {getFieldDecorator('birthday', {
                          rules: [{ type: 'object', required: true, message: '请选择逝者生辰!' }]
                      })(
                          <DatePicker />
                      )}
                  </FormItem>
                  <FormItem
                      {...formItemLayout}
                      label="忌日"
                  >
                      {getFieldDecorator('deathDay', {
                          rules: [{ type: 'object', required: true, message: '请选择逝者忌日!' }],
                      })(
                          <DatePicker />
                      )}
                  </FormItem>
                  <FormItem
                      {...formItemLayout}
                      label="与逝者关系"
                  >
                      {getFieldDecorator('relationship', {
                          rules: [{ required: true, message: '请选择与逝者关系!' }],
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
                      label="纪念馆名称"
                  >
                      {getFieldDecorator('memorialName', {
                          rules: [
                              { required: true, message: '请输入纪念馆名称！' },
                          ],
                      })(
                          <Input placeholder="纪念馆名称" />
                      )}
                  </FormItem>
                  <FormItem
                      {...formItemLayout}
                      label="模版风格"
                  >
                      <div className="moban-style">
                          <Row gutter={16}>
                              {this.getStyleList()}
                          </Row>
                      </div>

                  </FormItem>
                  <FormItem
                      wrapperCol={{ span: 12, offset: 6 }}
                  >
                      <Button type="primary" htmlType="submit">完成</Button>
                  </FormItem>
              </Form>
          </div>
        );
    }
};
const CreateForm = Form.create()(Create);
export default CreateForm;
