/**
 * Created by renqiqi on 2017/06/08.
 */
import React from 'react';
import { Router, Route, Redirect, hashHistory } from 'react-router';
import Management from '../components/management/Management.jsx';
import Create from '../components/create/Create.jsx';
import Attention from '../components/attention/Attention.jsx';
import Jidian from '../components/jidian/Jidian.jsx';
import EditData from '../components/editdata/EditData.jsx';

const RouteConfig = (

    <Router history={hashHistory}>
        <Route path="/" component={Management}/>
        <Route path="/create" component={Create}/>
        <Route path="/attention" component={Attention}/>
        <Route path="/jidian" component={Jidian}/>
        <Route path="/editdata" component={EditData}/>
        <Redirect from='*' to='/'  />
    </Router>
);

export default RouteConfig;
