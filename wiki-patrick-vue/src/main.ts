import Vue, { VueConstructor } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import axios from "axios";
// @ts-ignore
axios.defaults.baseURL = process.env.VUE_APP_SERVER
Vue.config.productionTip = false
Vue.use(Antd)

/**
 * axios拦截器
 */
axios.interceptors.request.use(config=>{
  console.log('请求参数：',config)
  return config
},error => {
  return Promise.reject(error)
})
axios.interceptors.response.use(response=>{
  console.log('返回结果：',response)
  return response
},error => {
  return Promise.reject(error)
})




new Vue({
  router,
  store,
  render: (h: (arg0: VueConstructor<Vue>) => any) => h(App)
}).$mount('#app')
