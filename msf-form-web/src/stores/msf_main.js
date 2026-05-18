import { defineStore } from 'pinia'

export const useMsfMainStore = defineStore('msfMain', {
  state: () => ({
    hittedNotices: [],
    hittedQnas: [],
  }),
  getters: {},
  actions: {
    addHittedNotice(ids) {
      const hitted = ids.filter((v) => !this.hittedNotices.includes(v))
      if (hitted?.length > 0) {
        this.hittedNotices = this.hittedNotices.concat(hitted)
      }
    },
    addHittedQnas(ids) {
      const hitted = ids.filter((v) => !this.hittedQnas.includes(v))
      if (hitted?.length > 0) {
        this.hittedQnas = this.hittedQnas.concat(hitted)
      }
    },
  },
})
