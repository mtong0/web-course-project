module.exports={
    devServer: {
        host: 'localhost',
        port: 8081,
        proxy: {
            '/api':{
                target:'http://localhost:8080',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    },

    assetsDir: 'static'
}