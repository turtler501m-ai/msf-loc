<template>
  <div class="header-wrap">
    <header class="header-inner">
      <h1 class="logo"><img src="@/assets/images/logo.svg" alt="kt mobile" /></h1>
      <div class="side-wrap">
        <div class="user-info">
          <p class="name"><span class="avatar"></span>홍길동</p>
          <ul class="infos">
            <li>SPT8050</li>
            <li>IT전략팀</li>
          </ul>
        </div>
      </div>
    </header>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.header-wrap {
  width: 100%;
  height: var(--layout-header-height);
  background-color: var(--color-background);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  flex-shrink: 0;
  padding-inline: var(--spacing-x6);
  .header-inner {
    position: relative;
    max-width: var(--layout-max-width);
    width: 100%;
    height: 100%;
    margin: 0 auto;
    @include flex($v: center, $h: space-between);
    .title {
      @include position($p: absolute, $l: 50%, $t: 50%);
      transform: translate(-50%, -50%);
      font-size: var(--font-size-20);
      font-weight: var(--font-weight-bold);
      text-align: center;
    }
    h1.logo {
      flex-shrink: 0;
      flex-grow: 0;
      margin: 0;
      padding: 0;
      height: auto;
      font-size: inherit;
    }
    .side-wrap {
      @include flex($v: center) {
        gap: var(--spacing-x6);
      }
      .user-info {
        @include flex($v: center) {
          gap: var(--spacing-x4);
        }
        .name {
          @include flex($v: center) {
            gap: rem(4px);
          }
          font-size: var(--font-size-14);
          font-weight: var(--font-weight-bold);
          .avatar {
            display: inline-block;
            width: rem(24px);
            height: rem(24px);
            background-image: url('@/assets/images/userAvatar.svg');
            background-position: left center;
            background-size: 100%;
          }
        }
        ul.infos {
          padding-inline: var(--spacing-indent);
          @include flex($v: center);
          column-gap: var(--spacing-x2);
          font-size: var(--font-size-14);
          color: var(--color-gray-500);
          & > :not(:last-child) {
            @include separator;
          }
        }
      }
      .side-btns {
        @include flex($v: center) {
          gap: var(--spacing-x2);
        }
        button {
          width: rem(40px);
          height: rem(40px);
          border-radius: rem(8px);
          background: var(--color-gray-100);
          border-color: var(--color-gray-100);
          :deep(.msf-icon) {
            --icon-size: #{rem(24px)};
            color: var(--color-gray-500);
          }
        }
      }
    }
  }
}
</style>
