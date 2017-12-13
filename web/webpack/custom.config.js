module.exports = {
    proxy: {
        //服务将被发送到哪个服务器
        target: "http://localhost:8080/wmjs/"
    },
    server: {
        domain: "localhost",
        port: 3000
    }
};


