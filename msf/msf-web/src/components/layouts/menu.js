// 왼쪽메뉴 - 관리자 목록 샘플
// (MsfSideNav, MsfContainer, MsfBreadcrumb 에서 공유중)
export const menuData = [
  {
    label: '메뉴1',
    path: '/',
  },
  {
    label: '메뉴2',
    children: [
      // {
      //   label: '메뉴2-1',
      //   children: [
      //     { label: '메뉴2-1-1', path: '/home' },
      //     { label: '메뉴2-1-2', path: '/guide/test' },
      //   ],
      // },
      { label: '메뉴2-1', path: '/home' },
      { label: '메뉴2-2', path: '/guide' },
      { label: '메뉴2-3', path: '/guide' },
    ],
  },
  {
    label: '메뉴3',
    children: [
      { label: '메뉴3-1', path: '/guide' },
      { label: '메뉴3-2', path: '/guide/icons' },
    ],
  },
]
