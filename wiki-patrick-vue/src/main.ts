import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import axios from 'axios'
import * as Icons from '@ant-design/icons-vue';
axios.defaults.baseURL = process.env.VUE_APP_SERVER
/**
 * 拦截器
 */
axios.interceptors.request.use(config => {
    console.log('请求参数：', config)
    return config
}, error => {
    return Promise.reject(error)
})
axios.interceptors.response.use(response => {
    console.log('返回结果：', response)
    return response
}, error => {
    return Promise.reject(error)
})


const app = createApp(App)
app.use(store)
app.use(router)
app.use(Antd)
app.mount('#app')

// 全局使用图标
const icons: any = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}