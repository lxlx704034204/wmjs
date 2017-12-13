/**
 * Created by renqiqi on 2017/03/29.
 */
import React from 'react';
import {render} from "react-dom";
import ELayout from './components/ELayout.jsx';
import injectTapEventPlugin from "react-tap-event-plugin";
import userMe from '../../common/store/userMe';

//点击事件的支持
injectTapEventPlugin();

userMe({fetchProperties:"*"}).then((data)=>{
    render(<div>
            <ELayout userMe={data} />
        </div>,
        document.getElementById("app")
    );

}).catch((e)=>{
    window.location.href = "../../channels/introduce.html";
});

