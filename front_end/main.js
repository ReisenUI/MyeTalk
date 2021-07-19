import Vue from 'vue'
import App from './App'
import request from './utils/request.js'
import http from './utils/http.js'
import mixin from 'common/mixin.js'

Vue.config.productionTip = false
Vue.prototype.request=request;
Vue.prototype.http=http;
Vue.mixin(mixin)

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
