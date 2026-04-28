import { defineStore } from 'pinia'

export const useMsfMenuStore = defineStore('msfMenu', {
  state: () => ({
    new: {
      cust: {},
      prod: {},
      agree: {},
    },
    menus: [
      {
        id: '00',
        name: '홈',
        url: '/',
        iconName: 'home',
      },
      {
        id: '01',
        name: '신규/변경',
        url: '/form/newchange',
        iconName: 'formNewChg',
      },
      {
        id: '02',
        name: '서비스 변경',
        url: '/form/servicechange',
        iconName: 'formSvcChg',
      },
      {
        id: '03',
        name: '명의변경',
        url: '/form/ownerchange',
        iconName: 'formOwnChg',
      },
      {
        id: '04',
        name: '서비스 해지',
        url: '/form/termination',
        iconName: 'formSvcCncl',
      },
      {
        id: '05',
        name: '부가기능',
        url: '/extra',
        iconName: 'ManagePage', //PNB 테스트용 추가
        children: [
          { id: '0501', name: '임시저장', url: '/extra/tempsave/TempSavePage' },
          { id: '0502', name: '접수완료 신청서', url: '/extra/receipt/ReceiptPage' },
          {
            id: '0503',
            name: '간편 신청서',
            url: '/extra/simplerequest/SimpleRequestPage', //PNB 테스트용 추가
          },
          ...(['loc'].includes(import.meta.env.MODE)
            ? [{ id: '0509', name: '신청서 관리(개발)', url: '/extra/receipt/ManageDevPage' }]
            : []),
          // { id: '0505', name: '로그인 설정', url: '/extra/mobileapp/MobileAppPage' },
        ],
      },
    ],
  }),
  getters: {},
  actions: {
    /**
     * 라우트 경로를 통한 상위 메뉴 조회
     *
     * @param {string} routePath
     * @returns
     */
    getParentMenu(routePath) {
      const parentMenu = this.menus.find((m) => m.url === routePath)
      if (parentMenu) {
        return parentMenu
      }
      const foundMenu = this.menus.find(
        (m) => m.children && m.children.some((sm) => sm.url === routePath),
      )
      if (foundMenu) {
        return foundMenu
      }
      return null
    },
    /**
     * 라우트 경로를 통한 하위 메뉴 조회
     *
     * @param {string} routePath
     * @returns
     */
    getSubMenu(routePath) {
      for (const menu of this.menus) {
        if (menu.children) {
          const subMenu = menu.children.find((sm) => sm.url === routePath)
          if (subMenu) {
            return subMenu
          }
        }
      }
      return null
    },
  },
})
