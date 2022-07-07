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

new Vue({
  router,
  store,
  render: (h: (arg0: VueConstructor<Vue>) => any) => h(App)
}).$mount('#app')
