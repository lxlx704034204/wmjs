import React from "react";

import eCharts from 'echarts/lib/echarts';
import 'echarts/lib/chart/bar';
let ECharts = React.createClass({

    getDefaultProps(){
        return {
            width: document.documentElement.clientWidth,
            option: {}
        }
    },
    setOption() {
        if(this.chart){
            this.chart.setOption(this.props.option, false, false);
        }
    },
    getInstance() {
        return this.chart;
    },
    componentDidMount() {
        this.init();
    },
    init() {
        this.chart = eCharts.init(this.refs.container);
        this.setOption();
    },
    render(){
        this.setOption();
        return (
            <div ref = "container" style={{height:this.props.oHeight,width:this.props.width}}></div>
        );
    }

});

export default ECharts;