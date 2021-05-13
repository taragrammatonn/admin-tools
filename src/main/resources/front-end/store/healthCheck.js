const baseUrl = 'http://localhost'
const healthCheckUrl = '/actuator/health'

const axios = require('axios')

export const state = () => ({
  microservices: {
    serviceRegistry: null,
    dbService: null,
    parsingService: null,
    telegramUserService: null,
    logisticService: null
  }
})

export const actions = {
  async servicesHealthCheck({commit}) {
    axios.get(baseUrl+':8761'+healthCheckUrl).then(res => {commit('setServiceRegistryStatus', res)})
      .catch(err => console.log(err))

    axios.get(baseUrl+':8180'+healthCheckUrl).then(res => {commit('setDbServiceStatus', res)})
      .catch(err => console.log(err))

    axios.get(baseUrl+':8081'+healthCheckUrl).then(res => {commit('setParsingServiceStatus', res)})
      .catch(err => console.log(err))

    axios.get(baseUrl+':8082'+healthCheckUrl).then(res => {commit('setTelegramUserServiceStatus', res)})
      .catch(err => console.log(err))

    axios.get(baseUrl+':8083'+healthCheckUrl).then(res => {commit('setLogisticServiceStatus', res)})
      .catch(err => console.log(err))
  }
}

export const mutations = {
  setServiceRegistryStatus(state, data) { state.microservices.serviceRegistry = data.data.status === 'UP' },
  setDbServiceStatus(state, data) { state.microservices.dbService = data.data.status === 'UP' },
  setParsingServiceStatus(state, data) { state.microservices.parsingService = data.data.status === 'UP' },
  setTelegramUserServiceStatus(state, data) { state.microservices.telegramUserService = data.data.status === 'UP' },
  setLogisticServiceStatus(state, data) { state.microservices.logisticService = data.data.status === 'UP' }
}

export const getters = {
  getServiceRegistryStatus: s => s.microservices.serviceRegistry,
  getDbServiceStatus: s => s.microservices.dbService,
  getParsingServiceStatus: s => s.microservices.parsingService,
  getTelegramUserServiceStatus: s => s.microservices.telegramUserService,
  getLogisticServiceStatus: s => s.microservices.logisticService
}


