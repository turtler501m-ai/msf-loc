import '@/assets/styles/global.scss'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community' // ag-grid

import App from './App.vue'
import router from './router'

ModuleRegistry.registerModules([AllCommunityModule]) // ag-grid

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
