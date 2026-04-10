import { defineStore } from 'pinia'

export const useMsfMenuStore = defineStore('msfMenu', {
  state: () => ({
    menus: [
      {
        id: '01',
        title: '신규/변경',
        url: '/mobile/regist/RegistType',
        stepSubMenu: true,
        children: [
          {
            id: '0100',
            title: '가입유형',
            url: '/mobile/regist/RegistType',
            step: 0,
            hidden: true,
          },
          { id: '0101', title: '고객', url: '/mobile/regist/RegistCust', step: 1 },
          { id: '0102', title: '상품', url: '/mobile/regist/RegistProd', step: 2 },
          { id: '0103', title: '동의', url: '/mobile/regist/RegistAgree', step: 3 },
        ],
      },
      {
        id: '02',
        title: '서비스\n변경',
        url: '/mobile/change/ChangeTypeCust',
        stepSubMenu: true,
        children: [
          { id: '0201', title: '고객', url: '/mobile/change/ChangeTypeCust', step: 1 },
          { id: '0202', title: '상품', url: '/mobile/change/ChangeProd', step: 2 },
          { id: '0203', title: '동의', url: '/mobile/change/ChangeAgree', step: 3 },
        ],
      },
      {
        id: '03',
        title: '명의변경',
        url: '/mobile/ident/IdentTypeCust',
        stepSubMenu: true,
        children: [
          { id: '0301', title: '고객', url: '/mobile/ident/IdentTypeCust', step: 0 },
          { id: '0302', title: '상품', url: '/mobile/ident/IdentProd', step: 1 },
          { id: '0303', title: '동의', url: '/mobile/ident/IdentAgree', step: 2 },
        ],
      },
      {
        id: '04',
        title: '서비스\n해지',
        url: '/mobile/cancel/SvcCnclCust',
        stepSubMenu: true,
        children: [
          { id: '0401', title: '고객', url: '/mobile/cancel/SvcCnclCust', step: 0 },
          { id: '0402', title: '상품', url: '/mobile/cancel/SvcCnclProd', step: 1 },
          { id: '0403', title: '동의', url: '/mobile/cancel/SvcCnclAgree', step: 2 },
        ],
      },
      {
        id: '05',
        title: '부가기능',
        url: '/mobile/addition/TempStorage',
        children: [
          { id: '0501', title: '임시저장', url: '/mobile/addition/TempStorage' },
          { id: '0502', title: '접수완료\n신청서', url: '/mobile/addition/ReceiptComplete' },
          { id: '0503', title: '간편\n신청서', url: '/mobile/addition/SimpleForm' },
          { id: '0504', title: '로그인\n설정', url: '/mobile/addition/LoginSetting' },
        ],
      },
    ],
    stepSubMenuUrls: [],
  }),
  getters: {},
  actions: {
    getCurrentParentMenu(routePath) {
      const parentMenu = this.menus.find((menu) => menu.url === routePath)
      if (parentMenu) {
        return parentMenu
      }
      const etcMenus = this.menus.filter((menu) => !menu.stepSubMenu)
      const foundMenu = etcMenus.find(
        (menu) => menu.children && menu.children.some((subMenu) => subMenu.url === routePath),
      )
      if (foundMenu) {
        return foundMenu
      }
      const stepMenus = this.menus.filter((menu) => menu.stepSubMenu)
      const foundStepMenu = stepMenus.find(
        (menu) =>
          menu.children &&
          menu.children.some(
            (subMenu) => menu.stepSubMenu && this.stepSubMenuUrls.includes(subMenu.url),
          ),
      )
      return foundStepMenu || null
    },
    getCurrentAllStepSubMenu(routePath) {
      const parentMenu = this.getCurrentParentMenu(routePath)
      if (parentMenu?.stepSubMenu && parentMenu?.children) {
        return parentMenu.children
      }
      return null
    },
    getCurrentStepSubMenu(routePath) {
      const parentMenu = this.getCurrentParentMenu(routePath)
      if (parentMenu?.stepSubMenu && parentMenu?.children) {
        const subMenu = parentMenu?.children.find((sub) => this.stepSubMenuUrls.includes(sub.url))
        if (subMenu) {
          return subMenu
        }
      }
      return null
    },
    getCurrentSubMenu(routePath) {
      for (const menu of this.menus) {
        if (menu.children) {
          const subMenu = menu.children.find((sub) => sub.url === routePath)
          if (subMenu) {
            return subMenu
          }
        }
      }
      return null
    },
    clearStepSubMenuUrls() {
      this.stepSubMenuUrls.splice(0, this.stepSubMenuUrls.length)
    },
    setStepSubMenuUrls(subMenuUrl) {
      this.stepSubMenuUrls = [subMenuUrl]
    },
    addStepSubMenuUrl(subMenuUrl) {
      if (!this.stepSubMenuUrls.includes(subMenuUrl)) {
        this.stepSubMenuUrls.push(subMenuUrl)
        return true
      }
      return false
    },
  },
})
