import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
 
Vue.prototype.$apiBaseUrl = "http://springbootcontactservice-env.vhk784u9fe.us-east-2.elasticbeanstalk.com";

Vue.use(VueAxios, axios)
