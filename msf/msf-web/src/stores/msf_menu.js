import { defineStore } from 'pinia'

export const useMsfMenuStore = defineStore('msfMenu', {
  state: () => ({
    menus: [
      {
        id: '02',
        name: '서비스 변경',
        url: '/step/formSvcChg',
      },
      {
        id: '03',
        name: '명의변경',
        url: '/step/formOwnChg',
      },
      {
        id: '04',
        name: '서비스 해지',
        url: '/step/formSvcCncl',
      },
      {
        id: '05',
        name: '부가기능',
        url: '/detail/addFunction/TempStorage',
        children: [
          { id: '0501', name: '임시저장', url: '/detail/addFunction/TempStorage' },
          { id: '0502', name: '접수완료 신청서', url: '/detail/addFunction/ReceiptComplete' },
          { id: '0503', name: '간편 신청서', url: '/detail/addFunction/FormSimple' },
          { id: '0504', name: '로그인 설정', url: '/detail/addFunction/LoginSetting' },
        ],
      },
    ],
  }),
  getters: {},
  actions: {
    getCurrentParentMenu(routePath) {
      const parentMenu = this.menus.find((menu) => menu.url === routePath)
      if (parentMenu) return parentMenu
      const foundMenu = this.menus.find(
        (menu) => menu.children && menu.children.some((sub) => sub.url === routePath),
      )
      return foundMenu || null
    },
    getCurrentSubMenu(routePath) {
      for (const menu of this.menus) {
        if (menu.children) {
          const subMenu = menu.children.find((sub) => sub.url === routePath)
          if (subMenu) return subMenu
        }
      }
      return null
    },
  },
})
