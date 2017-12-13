import React from 'react';
import { Layout, Menu, Breadcrumb, Modal, Button } from 'antd';
const { Header, Content } = Layout;
import { hashHistory } from 'react-router';
import Routers from '../router/Router.jsx';
import '../assets/css/memorialmanagement.less';
import logout from '../../../common/store/logout';
import UserInfo from './UserInfo.jsx';
import updateUser from '../store/updateUser';
import queryGeneraluser from '../store/queryGeneraluser';

/**
 * 框架
 */
export default class ELayout extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            tmpName:null,
            loading: false,
            userMe: props.userMe,
            generalUser:null
        };

        let that = this;

        let queryParams = {
            'commonUser.pkUser': props.userMe.pkUser
        };

        queryGeneraluser(queryParams).then((data)=>{
            that.setState({
                generalUser:data[0]
            });
        })

    }

    handleClick(e){
        if(e.key == "1"){
            window.location.href = "../../index.html";
        } else if(e.key == "2"){
            hashHistory.push("/");
            this.setState({
                tmpName:"我管理的馆"
            })
        } else if(e.key == "3"){
            hashHistory.push("/attention");
            this.setState({
                tmpName:"我关注的馆"
            })
        } else if(e.key == "4"){
            hashHistory.push("/create");
            this.setState({
                tmpName:"我创建的馆"
            })
        }

    }


    onLogout=()=>{
        logout().then((data)=>{
            if(data.url.indexOf("sign_in")>=0){
                window.location.href = "../../channels/introduce.html";
            }
        });
    };

    showModal = () => {
        this.setState({
            visible: true,
        });
    };
    handleCreate = () => {

        const form = this.form;

        let that = this;

        form.validateFields((err, values) => {
            if (err) {
                return;
            }

            let param = {
                pkGeneralUser: this.state.generalUser.pkGeneralUser,
                version: this.state.generalUser.version,
                commonUser:{
                    pkUser: this.state.generalUser.commonUser.pkUser,
                    name: values.name,
                    phone1: values.phone1,
                    version: this.state.generalUser.commonUser.version
                },
                personalInfo:{
                    pkPersonalInfo: this.state.generalUser.personalInfo.pkPersonalInfo,
                    version: this.state.generalUser.personalInfo.version,
                    name: values.name,
                    sex: values.sex,
                    houseRegisterDetail: values.address,
                },
                fetchProperties: "*,commonUser.*,personalInfo.*"
            };

            updateUser(param).then((data)=>{

                form.resetFields();
                this.setState({
                    generalUser:data,
                    userMe:data.commonUser,
                    visible: false
                });
            });
        });

    };
    handleCancel = () => {
        this.setState({ visible: false });
    };

    saveFormRef = (form) => {
        this.form = form;
    };

    render() {

        let tmpName = "";
        let defaultSelectedKeys = "2";
        let obj = hashHistory.getCurrentLocation();

        if(obj.pathname == "/"){
            tmpName = "我管理的馆";
            defaultSelectedKeys = "2";
        }else if(obj.pathname == "/attention"){
            tmpName = "我关注的馆";
            defaultSelectedKeys = "3";
        }else if(obj.pathname == "/create"){
            tmpName = "创建新馆";
            defaultSelectedKeys = "4";
        }else if(obj.pathname == "/jidian"){
            tmpName = "网上祭奠";
            defaultSelectedKeys = "";
        }

        if(this.state && this.state.tmpName){
            tmpName = this.state.tmpName;
        }

        return (
          <div className='memorial'>
              <Layout>
                  <Header className="header">
                    <div className="logo" >用户中心</div>
                    <Menu
                      theme="dark"
                      mode="horizontal"
                      onClick={this.handleClick.bind(this)}
                      defaultSelectedKeys={defaultSelectedKeys}
                      style={{ lineHeight: '64px' }}
                    >
                      <Menu.Item key="1">首页</Menu.Item>
                      <Menu.Item key="2">我管理的馆</Menu.Item>
                      <Menu.Item key="3">我关注的馆</Menu.Item>
                      <Menu.Item key="4">创建新馆</Menu.Item>
                    </Menu>
                      <div className="user-center">
                          <div className="user-center-info" onClick={this.showModal}>{this.state.userMe.name}</div>
                          <div className="user-center-out" onClick={this.onLogout}>退出</div>
                      </div>

                      {this.state.generalUser?
                          <UserInfo
                              ref={this.saveFormRef}
                              visible={this.state.visible}
                              onCancel={this.handleCancel}
                              onCreate={this.handleCreate}
                              generalUser={this.state.generalUser}
                          />:null}
                  </Header>
                  <Layout>
                    <Layout style={{ padding: '0 24px 24px' }}>
                      <Breadcrumb style={{ margin: '12px 0' }}>
                        <Breadcrumb.Item>用户中心</Breadcrumb.Item>
                        <Breadcrumb.Item>{tmpName}</Breadcrumb.Item>
                      </Breadcrumb>
                      <Content style={{ padding: '0 20', margin: 0, minHeight: window.innerHeight-150 }}>
                          {Routers}
                      </Content>
                    </Layout>
                  </Layout>
                </Layout>
            </div>
        );
    }
};
