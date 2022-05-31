import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'

Vue.config.productionTip = false
// Vue.prototype.$host = 'http://localhost:8080/api'
Vue.prototype.$host = '/api'
Vue.prototype.$formatTime = function parseTime (time, pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/').replace('T', ' ').replace(new RegExp(/\.[\d]{3}/gm), '')
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const timeStr = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return timeStr
}

function Timer (time, type, callback) {
  this.init(time, type, callback)
}

Timer.prototype.init = function (time, type, callback) {
  this.callback = callback || function () {}
  this.time = time || parseInt(Math.random() * 3600)
  this.timerId = null

  if (type === undefined) {
    this.type = true
  } else {
    this.type = type
  }
}

Timer.prototype.start = function (callback) {
  this.callback = callback || function () {}
  !this.timerId && this.reTime()
}

/**
 * 暂停/停止
 */
Timer.prototype.pause = function () {
  if (this.timerId) {
    clearTimeout(this.timerId)
    this.timerId = null
  }
}

/**
 * 刷新时间
 * @return {string} 时间
 */
Timer.prototype.reTime = function () {
  if (this.type) {
    this.time++
  } else if (this.time > 0) {
    this.time--
  } else {
    // 等于零结束计时
    this.formatOutput(true)
    return false
  }

  // 输出时间
  this.formatOutput()
  // 递归
  this.timerId = setTimeout(this.reTime.bind(this), 1000)
}

/**
 * 输出时间
 */
Timer.prototype.formatOutput = function (end) {
  if (end) {
    this.callback('00:00:00')
    return '00:00:00'
  }
  // 获取时分秒
  let h = parseInt(this.time / 3600)
  let m = parseInt((this.time - h * 3600) / 60)
  let s = this.time - h * 3600 - m * 60

  // 格式化
  // eslint-disable-next-line no-unused-expressions
  h < 10 ? h = `0${h}` : ''
  // eslint-disable-next-line no-unused-expressions
  m < 10 ? m = `0${m}` : ''
  // eslint-disable-next-line no-unused-expressions
  s < 10 ? s = `0${s}` : ''

  this.callback(`${h}:${m}:${s}`)
  return `${h}:${m}:${s}`
}

Vue.prototype.$timer = Timer

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
